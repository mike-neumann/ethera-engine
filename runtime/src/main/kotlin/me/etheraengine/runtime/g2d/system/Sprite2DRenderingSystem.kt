package me.etheraengine.runtime.g2d.system

import me.etheraengine.runtime.g2d.entity.component.*
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics

/**
 * Prebuilt system to render 2D sprite graphics
 */
@Component
class Sprite2DRenderingSystem : RenderingSystem {
    override fun render(scene: Scene, g: Graphics, now: Long, deltaTime: Long) {
        val entities = scene.getFilteredEntities { scene.camera2D.canSee(it) && it.hasComponent<Sprite2D>() && it.hasComponent<Position2D>() && it.hasComponent<Dimensions2D>() }

        for (entity in entities) {
            val sprite = entity.getComponent<Sprite2D>()!!
            val position = entity.getComponent<Position2D>()!!
            val dimensions = entity.getComponent<Dimensions2D>()!!

            g.drawImage(
                sprite.image,
                position.x.toInt() + sprite.renderOffsetX,
                position.y.toInt() + sprite.renderOffsetY,
                dimensions.width,
                dimensions.height,
                null
            )
        }
    }
}