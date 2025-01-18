package me.etheraengine.example.system

import me.etheraengine.example.entity.component.Health
import me.etheraengine.scene.Scene
import me.etheraengine.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

@Component
class EntityHealthHudRenderingSystem : RenderingSystem {
    override fun render(
        scene: Scene,
        g: Graphics,
        now: Long,
        deltaTime: Long,
    ) {
        scene.getEntities {
            it.hasComponent<Point2D>() && it.hasComponent<Dimension2D>() && it.hasComponent<Health>()
        }.forEach {
            val position = it.getComponent<Point2D>()!!
            val dimension = it.getComponent<Dimension2D>()!!
            val health = it.getComponent<Health>()!!

            g.color = Color.RED
            g.drawRect(
                position.x.toInt(),
                position.y.toInt() - 40,
                dimension.width.toInt(),
                10
            )
            g.fillRect(
                position.x.toInt(),
                position.y.toInt() - 40,
                ((health.health / health.maxHealth * 100) / 100 * dimension.width).toInt(),
                10
            )
        }
    }
}