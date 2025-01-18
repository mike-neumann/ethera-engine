package me.etheraengine.g2d.system

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
    override fun render(
        scene: Scene,
        g: Graphics,
        now: Long,
        deltaTime: Long,
    ) {
        scene.getEntities {
            it.hasComponent<Sprite2D>() && it.hasComponent<Point2D>() && it.hasComponent<Dimension2D>()
        }.forEach {
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