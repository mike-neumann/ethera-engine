package me.etheraengine.engine

import me.etheraengine.logger
import org.springframework.boot.CommandLineRunner
import kotlin.system.measureTimeMillis

/**
 * An engine defines a continuous running task within the game engine's lifecycle.
 */
abstract class Engine(
    name: String,
    val tickDuration: Int,
) : Thread(name), CommandLineRunner {
    val log = logger<LogicEngine>()

    final override fun run() {
        var lastTickTime = System.currentTimeMillis()

        while (true) {
            val now = System.currentTimeMillis()
            val deltaTime = now - lastTickTime
            val elapsedTime = measureTimeMillis {
                onTick(now, deltaTime)
            }

            if (elapsedTime < tickDuration) {
                // TODO: marcel tips?
                sleep(tickDuration - elapsedTime)
            }

            lastTickTime = now
        }
    }

    abstract fun onTick(now: Long, deltaTime: Long)

    final override fun run(vararg args: String) {
        start()
        log.info("$name started")
    }
}