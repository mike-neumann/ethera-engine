package me.animaengine.test.system

import me.animaengine.entity.Entity
import me.animaengine.scene.Scene
import me.animaengine.system.RenderingSystem
import me.animaengine.test.entity.component.Health
import me.animaengine.test.entity.component.Position
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics

@Component
class EntityHealthHudRenderingSystem : RenderingSystem {
    override fun render(g: Graphics, scene: Scene, entities: List<Entity>) {
        entities
            .filter { it.hasComponent<Position>() }
            .filter { it.hasComponent<Health>() }
            .forEach {
                val position = it.getComponent<Position>()!!
                val health = it.getComponent<Health>()!!

                g.color = Color.RED
                g.drawRect(
                    position.x.toInt(),
                    position.y.toInt() - 15,
                    position.width,
                    10
                )
                g.fillRect(
                    position.x.toInt(),
                    position.y.toInt() - 15,
                    ((health.health / health.maxHealth * 100) / 100 * position.width).toInt(),
                    10
                )
            }
    }
}