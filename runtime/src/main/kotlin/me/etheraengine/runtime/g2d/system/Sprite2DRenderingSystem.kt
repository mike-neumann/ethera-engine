package me.etheraengine.runtime.g2d.system

import me.etheraengine.runtime.g2d.entity.component.Sprite2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics2D

/**
 * Prebuilt system to render 2D sprite graphics
 */
@Component
class Sprite2DRenderingSystem : RenderingSystem {
    override fun render(scene: Scene, g: Graphics2D, now: Long, deltaTime: Long) {
        val entities = scene.getFilteredEntities { scene.camera2D.canSee(it) && it.hasComponent<Sprite2D>() }

        for (entity in entities) {
            val sprite = entity.getComponent<Sprite2D>()!!

            g.drawImage(
                sprite.image,
                entity.x.toInt() + sprite.renderOffsetX,
                entity.y.toInt() + sprite.renderOffsetY,
                entity.width,
                entity.height,
                null
            )
        }
    }
}