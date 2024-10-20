package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.scene.Scene
import me.etheraengine.system.RenderingSystem
import me.etheraengine.example.entity.component.Health
import me.etheraengine.example.entity.component.Position
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics

@Component
class EntityHealthHudRenderingSystem : RenderingSystem {
    override fun render(scene: Scene, entities: List<Entity>, g: Graphics, now: Long, deltaTime: Long) {
        entities
            .filter { it.hasComponent<Position>() }
            .filter { it.hasComponent<Health>() }
            .forEach {
                val position = it.getComponent<Position>()!!
                val health = it.getComponent<Health>()!!

                g.color = Color.RED
                g.drawRect(
                    position.x.toInt(),
                    position.y.toInt() - 40,
                    position.width,
                    10
                )
                g.fillRect(
                    position.x.toInt(),
                    position.y.toInt() - 40,
                    ((health.health / health.maxHealth * 100) / 100 * position.width).toInt(),
                    10
                )
            }
    }
}