package me.etheraengine.example.system

import me.etheraengine.example.entity.component.Health
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics

@Component
class EntityHealthHudRenderingSystem : RenderingSystem {
    override fun render(
        scene: Scene,
        g: Graphics,
        now: Long,
        deltaTime: Long,
    ) {
        scene.getEntities {
            it.hasComponent<Position2D>() && it.hasComponent<Dimensions2D>() && it.hasComponent<Health>()
        }.forEach {
            val position = it.getComponent<Position2D>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!
            val health = it.getComponent<Health>()!!

            g.color = Color.RED
            g.drawRect(
                position.x.toInt(),
                position.y.toInt() - 40,
                dimensions.width.toInt(),
                10
            )
            g.fillRect(
                position.x.toInt(),
                position.y.toInt() - 40,
                ((health.health / health.maxHealth * 100) / 100 * dimensions.width).toInt(),
                10
            )
        }
    }
}