package me.etheraengine.runtime.service

import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.logger
import me.etheraengine.runtime.scene.Scene
import org.springframework.stereotype.Service
import java.awt.Graphics2D

/**
 * Service to manage the currently active scene and switch between different ones
 */
@Service
class SceneService {
    private final val logger = logger<SceneService>()
    final var lastScene: Scene? = null
        private set
    final var currentScene: Scene? = null
        private set(value) {
            if (value != field) lastScene = field
            field = value
        }

    @PublishedApi
    internal fun switchScene(scene: Scene) {
        currentScene?.let {
            logger.debug("Disabling scene {}", it::class.java.simpleName)
            it.onDisable()
        }

        currentScene = scene

        currentScene?.let {
            logger.debug("Enabling scene {}", it::class.java.simpleName)

            if (!it.isInitialized) {
                it.isInitialized = true
            }

            it.onEnable()
        }
    }

    final inline fun <reified T : Scene> switchScene() = switchScene(Ethera.context.getBean(T::class.java))
    fun switchToPreviousScene() = lastScene?.let { switchScene(it) }
    fun update(now: Long, deltaTime: Long) = currentScene?.update(now, deltaTime)
    fun render(g: Graphics2D, now: Long, deltaTime: Long) = currentScene?.render(g, now, deltaTime)
}