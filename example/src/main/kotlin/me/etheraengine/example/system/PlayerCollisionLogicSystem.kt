package me.etheraengine.example.system

import me.etheraengine.runtime.Ethera
import me.etheraengine.example.entity.Player
import me.etheraengine.runtime.g2d.entity.component.*
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class PlayerCollisionLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val players = scene.getEntities { it is Player }

        for (player in players) {
            val movement = player.getComponent<Movement2D>()!!
            val position = player.getComponent<Position2D>()!!
            val dimensions = player.getComponent<Dimensions2D>()!!
            // top collision
            if (position.y < 0) {
                position.setLocation(
                    0.0,
                    position.x
                )
            }
            // bottom collision
            if (position.y + dimensions.height > Ethera.frame.height) {
                movement.vy = 0.0
                position.setLocation(
                    Ethera.frame.height - dimensions.height.toDouble(),
                    position.x
                )
            }
            // right collision
            if (position.x + dimensions.width > Ethera.frame.width) {
                position.setLocation(
                    position.y,
                    Ethera.frame.width - dimensions.width.toDouble()
                )
            }
            // left collision
            if (position.x < 0) {
                position.setLocation(
                    position.y,
                    0.0
                )
            }
        }
    }
}