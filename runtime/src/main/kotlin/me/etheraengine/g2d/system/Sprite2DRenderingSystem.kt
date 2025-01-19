package me.etheraengine.g2d.system

import me.etheraengine.g2d.entity.component.Dimensions2D
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
    override fun render(
        scene: Scene,
        g: Graphics,
        now: Long,
        deltaTime: Long,
    ) {
        scene.getEntities {
            it.hasComponent<Sprite2D>() && it.hasComponent<Position2D>() && it.hasComponent<Dimensions2D>()
        }.forEach {
            val sprite = it.getComponent<Sprite2D>()!!
            val position = it.getComponent<Position2D>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!

            g.drawImage(
                sprite.image,
                position.x.toInt() + sprite.renderOffsetX,
                position.y.toInt() + sprite.renderOffsetY,
                dimensions.width.toInt(),
                dimensions.height.toInt(),
                null
            )
        }
    }
}