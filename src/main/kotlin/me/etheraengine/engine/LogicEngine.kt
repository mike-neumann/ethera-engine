package me.etheraengine.engine

import me.etheraengine.config.EtheraConfig
import me.etheraengine.service.SceneService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

/**
 * Engine responsible for calling all registered logic related systems
 */
@Component
open class LogicEngine(
    etheraConfig: EtheraConfig,
    private val sceneService: SceneService
) : Thread("LogicEngine"), CommandLineRunner {
    private val log = me.etheraengine.logger<LogicEngine>()
    private val tickDuration = 1_000 / etheraConfig.tps

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