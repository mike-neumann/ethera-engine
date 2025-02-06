package me.etheraengine.example.system

import me.etheraengine.example.entity.component.Health
import me.etheraengine.runtime.g2d.entity.component.Dimensions2D
import me.etheraengine.runtime.g2d.entity.component.Position2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics

@Component
class EntityHealthHudRenderingSystem : RenderingSystem {
    override fun render(scene: Scene, g: Graphics, now: Long, deltaTime: Long) {
        val entities =
            scene.getEntities { it.hasComponent<Position2D>() && it.hasComponent<Dimensions2D>() && it.hasComponent<Health>() }

        for (entity in entities) {
            val position = entity.getComponent<Position2D>()!!
            val dimensions = entity.getComponent<Dimensions2D>()!!
            val health = entity.getComponent<Health>()!!

            g.color = Color.RED
            g.drawRect(
                position.x.toInt(),
                position.y.toInt() - 40,
                dimensions.width,
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