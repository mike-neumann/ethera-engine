package me.etheraengine.runtime.engine

import me.etheraengine.runtime.service.ConfigurationService
import me.etheraengine.runtime.service.SceneService
import org.springframework.stereotype.Component

/**
 * Engine responsible for calling all registered logic related systems
 */
@Component
open class LogicEngine(configurationService: ConfigurationService, val sceneService: SceneService) :
    Engine("LogicEngine", 1_000 / configurationService.tps) {
    override fun onTick(now: Long, deltaTime: Long): Unit = run { sceneService.update(now, deltaTime) }
}