package me.animaengine.engine

import me.animaengine.config.AnimaConfig
import me.animaengine.logger
import me.animaengine.service.SceneService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

/**
 * Engine responsible for calling all registered logic related systems
 */
@Component
open class LogicEngine(
    animaConfig: AnimaConfig,
    private val sceneService: SceneService
) : Thread("LogicEngine"), CommandLineRunner {
    private val log = logger<LogicEngine>()
    private val tickDuration = 1_000 / animaConfig.tps

    override fun run() {
        var lastTickTime = System.currentTimeMillis()

        while (true) {
            val now = System.currentTimeMillis()
            val deltaTime = now - lastTickTime
            val elapsedTime = measureTimeMillis {
                sceneService.update(deltaTime)
            }

            if (elapsedTime < tickDuration) {
                sleep(tickDuration - elapsedTime)
            }

            lastTickTime = now
        }
    }

    override fun run(vararg args: String) {
        start()
        log.info("Logic engine started")
    }
}