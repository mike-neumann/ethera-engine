package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.PlayerMovement
import me.etheraengine.g2d.entity.component.Movement2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.math.sqrt

@Component
class PlayerMovementSystem : LogicSystem {
    override fun update(scene: Scene, entities: ConcurrentLinkedQueue<Entity>, now: Long, deltaTime: Long) {
        entities
            .filterIsInstance<Player>()
            .forEach {
                val playerMovement = it.getComponent<PlayerMovement>()!!
                val movement = it.getComponent<Movement2D>()!!

                if (playerMovement.isMovingUp) {
                    movement.vy = -1.0
                } else if (playerMovement.isMovingDown) {
                    movement.vy = 1.0
                } else {
                    movement.vy = 0.0
                }

                if (playerMovement.isMovingLeft) {
                    movement.vx = -1.0
                } else if (playerMovement.isMovingRight) {
                    movement.vx = 1.0
                } else {
                    movement.vx = 0.0
                }

                // normalize diagonal movement
                val magnitude =
                    sqrt(movement.vx * movement.vx + movement.vy * movement.vy)

                if (magnitude > 0) {
                    movement.vx /= magnitude
                    movement.vy /= magnitude
                }
            }
    }
}
