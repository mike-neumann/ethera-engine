package me.etheraengine.engine

import me.etheraengine.Window
import me.etheraengine.config.EtheraConfig
import me.etheraengine.system.RenderingSystem
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

/**
 * Engine responsible for calling all registered rendering related systems
 */
@Component
class RenderingEngine(
    etheraConfig: EtheraConfig,
    private val window: Window
) : Thread("RenderingEngine"), CommandLineRunner {
    private val log = me.etheraengine.logger<RenderingSystem>()
    private val frameDuration = 1_000 / etheraConfig.maxFps

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