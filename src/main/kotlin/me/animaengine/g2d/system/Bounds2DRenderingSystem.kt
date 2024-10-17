package me.animaengine.g2d.system

import me.animaengine.entity.Entity
import me.animaengine.g2d.entity.component.Position2D
import me.animaengine.scene.Scene
import me.animaengine.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics

/**
 * Prebuilt system to render the bounds of every registered entity.
 *
 * Register this rendering system if you want every entity to render their bounds
 */
@Component
class Bounds2DRenderingSystem : RenderingSystem {
    override fun render(g: Graphics, scene: Scene, entities: List<Entity>) {
        entities
            .filter { it.hasComponent<Position2D>() }
            .forEach {
                val position = it.getComponent<Position2D>()!!

                g.drawRect(
                    position.x.toInt(),
                    position.y.toInt(),
                    position.width,
                    position.height
                )
            }
    }
}