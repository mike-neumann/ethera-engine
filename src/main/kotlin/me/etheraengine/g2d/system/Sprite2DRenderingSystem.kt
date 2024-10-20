package me.etheraengine.g2d.system

import me.etheraengine.entity.Entity
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.g2d.entity.component.Sprite2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics

/**
 * Prebuilt system to render 2D sprite graphics
 */
@Component
class Sprite2DRenderingSystem : RenderingSystem {
    override fun render(g: Graphics, scene: Scene, entities: List<Entity>) {
        entities
            .filter { it.hasComponent<Sprite2D>() }
            .filter { it.hasComponent<Position2D>() }
            .forEach {
                val sprite = it.getComponent<Sprite2D>()!!
                val position = it.getComponent<Position2D>()!!

                g.drawImage(
                    sprite.image,
                    position.x.toInt(),
                    position.y.toInt(),
                    position.width,
                    position.height,
                    null
                )
            }
    }
}