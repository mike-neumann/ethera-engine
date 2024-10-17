package me.animaengine.engine

import me.animaengine.Window
import me.animaengine.config.AnimaConfig
import me.animaengine.logger
import me.animaengine.system.RenderingSystem
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

/**
 * Engine responsible for calling all registered rendering related systems
 */
@Component
class RenderingEngine(
    animaConfig: AnimaConfig,
    private val window: Window
) : Thread("RenderingEngine"), CommandLineRunner {
    private val log = logger<RenderingSystem>()
    private val frameDuration = 1_000 / animaConfig.maxFps

    override fun run() {
        while (true) {
            val elapsedTime = measureTimeMillis {
                window.repaint()
            }

            if (elapsedTime < frameDuration) {
                sleep(frameDuration - elapsedTime)
            }
        }
    }

    override fun run(vararg args: String) {
        start()
        log.info("Rendering engine started")
    }
}