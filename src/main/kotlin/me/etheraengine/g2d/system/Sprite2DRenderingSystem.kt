package me.etheraengine.g2d.system

import me.etheraengine.entity.Entity
import me.etheraengine.g2d.entity.component.Sprite2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

/**
 * Prebuilt system to render 2D sprite graphics
 */
@Component
class Sprite2DRenderingSystem : RenderingSystem {
    override fun render(scene: Scene, entities: List<Entity>, g: Graphics, now: Long, deltaTime: Long) {
        entities
            .filter { it.hasComponent<Sprite2D>() }
            .filter { it.hasComponent<Point2D>() }
            .filter { it.hasComponent<Dimension2D>() }
            .forEach {
                val sprite = it.getComponent<Sprite2D>()!!
                val position = it.getComponent<Point2D>()!!
                val dimension = it.getComponent<Dimension2D>()!!

                g.drawImage(
                    sprite.image,
                    position.x.toInt() + sprite.renderOffsetX,
                    position.y.toInt() + sprite.renderOffsetY,
                    dimension.width.toInt(),
                    dimension.height.toInt(),
                    null
                )
            }
    }
}