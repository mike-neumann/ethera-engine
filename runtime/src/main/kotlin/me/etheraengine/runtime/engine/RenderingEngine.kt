package me.etheraengine.runtime.engine

import me.etheraengine.runtime.Screen
import me.etheraengine.runtime.service.ConfigurationService
import org.springframework.stereotype.Component

/**
 * Engine responsible for calling all registered rendering related systems
 */
@Component
open class RenderingEngine(configurationService: ConfigurationService, val screen: Screen) :
    Engine("RenderingEngine", 1_000 / configurationService.maxFps) {
    override fun onTick(now: Long, deltaTime: Long) = screen.repaint()
}