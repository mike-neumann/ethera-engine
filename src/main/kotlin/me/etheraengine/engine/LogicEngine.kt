package me.etheraengine.engine

import me.etheraengine.service.ConfigurationService
import me.etheraengine.service.SceneService
import org.springframework.stereotype.Component

/**
 * Engine responsible for calling all registered logic related systems
 */
@Component
open class LogicEngine(
    configurationService: ConfigurationService,
    val sceneService: SceneService,
) : Engine("LogicEngine", 1_000 / configurationService.tps) {
    override fun onTick(deltaTime: Long) {
        sceneService.update(deltaTime)
    }
}