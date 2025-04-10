package me.etheraengine.example.system

import me.etheraengine.example.entity.component.Health
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics2D

@Component
class EntityHealthHudRenderingSystem : RenderingSystem {
    override fun render(entity: Entity, scene: Scene, g: Graphics2D, now: Long, deltaTime: Long) {
        if (!entity.hasComponent<Health>()) return
        val health = entity.getComponent<Health>()!!

        g.color = Color.RED
        g.drawRect(entity.x.toInt(), entity.y.toInt() - 40, entity.width, 10)
        g.fillRect(entity.x.toInt(), entity.y.toInt() - 40, ((health.health / health.maxHealth * 100) / 100 * entity.width).toInt(), 10)
    }
}