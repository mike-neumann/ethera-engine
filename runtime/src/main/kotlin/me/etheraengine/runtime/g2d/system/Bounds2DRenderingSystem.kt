package me.etheraengine.runtime.g2d.system

import me.etheraengine.runtime.g2d.entity.component.Dimensions2D
import me.etheraengine.runtime.g2d.entity.component.Position2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics

/**
 * Prebuilt system to render the bounds of every registered entity.
 *
 * Register this rendering system if you want every entity to render their bounds
 */
@Component
class Bounds2DRenderingSystem : RenderingSystem {
    override fun render(scene: Scene, g: Graphics, now: Long, deltaTime: Long) {
        val entities = scene.getFilteredEntities { scene.camera2D.canSee(it) && it.hasComponent<Position2D>() && it.hasComponent<Dimensions2D>() }

        for (it in entities) {
            val position = it.getComponent<Position2D>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!

            g.drawRect(position.x.toInt(), position.y.toInt(), dimensions.width, dimensions.height)
        }
    }
}