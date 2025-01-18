package me.etheraengine.g2d.system

import me.etheraengine.scene.Scene
import me.etheraengine.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

/**
 * Prebuilt system to render the bounds of every registered entity.
 *
 * Register this rendering system if you want every entity to render their bounds
 */
@Component
class Bounds2DRenderingSystem : RenderingSystem {
    override fun render(
        scene: Scene,
        g: Graphics,
        now: Long,
        deltaTime: Long,
    ) {
        scene.getEntities {
            it.hasComponent<Point2D>() && it.hasComponent<Dimension2D>()
        }.forEach {
            val position = it.getComponent<Point2D>()!!
            val dimension = it.getComponent<Dimension2D>()!!

            g.drawRect(
                position.x.toInt(),
                position.y.toInt(),
                dimension.width.toInt(),
                dimension.height.toInt()
            )
        }
    }
}