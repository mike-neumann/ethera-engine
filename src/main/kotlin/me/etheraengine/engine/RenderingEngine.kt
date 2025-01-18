package me.etheraengine.engine

import me.etheraengine.Screen
import me.etheraengine.service.ConfigurationService
import org.springframework.stereotype.Component

/**
 * Engine responsible for calling all registered rendering related systems
 */
@Component
class RenderingEngine(
    configurationService: ConfigurationService,
    val screen: Screen,
) : Engine("RenderingEngine", 1_000 / configurationService.maxFps) {
    override fun onTick(deltaTime: Long) {
        screen.repaint()
    }
}