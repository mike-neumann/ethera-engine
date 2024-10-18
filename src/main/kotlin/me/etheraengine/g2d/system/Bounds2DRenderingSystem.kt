package me.etheraengine.g2d.system

import me.etheraengine.entity.Entity
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.RenderingSystem
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