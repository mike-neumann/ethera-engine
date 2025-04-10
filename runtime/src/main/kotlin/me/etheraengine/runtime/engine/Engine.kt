package me.etheraengine.runtime.engine

import me.etheraengine.runtime.logger
import org.springframework.boot.CommandLineRunner
import kotlin.system.measureTimeMillis

/**
 * An engine defines a continuous running task within the game engine's lifecycle.
 */
abstract class Engine(name: String, val tickDuration: Int) : Thread(name), CommandLineRunner {
    val log = logger<LogicEngine>()

    final override fun run() {
        var lastTickTime = System.currentTimeMillis()

        while (true) {
            val now = System.currentTimeMillis()
            val deltaTime = now - lastTickTime
            val elapsedTime = measureTimeMillis { onTick(now, deltaTime) }
            // TODO: marcel tips?
            if (elapsedTime < tickDuration) sleep(tickDuration - elapsedTime)

            lastTickTime = now
        }
    }

    final override fun run(vararg args: String) {
        start()
        log.debug("$name started")
    }

    abstract fun onTick(now: Long, deltaTime: Long)
}