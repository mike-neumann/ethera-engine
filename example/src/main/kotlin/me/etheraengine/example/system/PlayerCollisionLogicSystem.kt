package me.etheraengine.example.system

import me.etheraengine.Ethera
import me.etheraengine.example.entity.Player
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Movement2D
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class PlayerCollisionLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        scene.getEntities {
            it is Player
        }.forEach {
            val movement = it.getComponent<Movement2D>()!!
            val position = it.getComponent<Position2D>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!

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