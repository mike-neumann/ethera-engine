package me.etheraengine.example.system

import me.etheraengine.example.entity.Player
import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.g2d.entity.component.Movement2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class PlayerCollisionLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val players = scene.getFilteredEntities { it is Player }

        for (player in players) {
            val movement = player.getComponent<Movement2D>()!!
            // top collision
            if (player.y < 0) {
                player.y = 0.0
            }
            // bottom collision
            if (player.y + player.height > Ethera.frame.height) {
                movement.vy = 0.0
                player.y = Ethera.frame.height - player.height.toDouble()
            }
            // right collision
            if (player.x + player.width > Ethera.frame.width) {
                player.x = Ethera.frame.width - player.width.toDouble()
            }
            // left collision
            if (player.x < 0) {
                player.x = 0.0
            }
        }
    }
}