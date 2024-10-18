package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.g2d.entity.component.Movement2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.PlayerMovement
import org.springframework.stereotype.Component
import kotlin.math.sqrt

@Component
class PlayerMovementSystem : LogicSystem {
    override fun update(deltaTime: Long, scene: Scene, entities: List<Entity>) {
        entities
            .filterIsInstance<Player>()
            .forEach {
                val playerMovement = it.getComponent<PlayerMovement>()!!
                val movement = it.getComponent<Movement2D>()!!

                if (playerMovement.isMovingUp) {
                    movement.vy = -1f
                } else if (playerMovement.isMovingDown) {
                    movement.vy = 1f
                } else {
                    movement.vy = 0f
                }

                if (playerMovement.isMovingLeft) {
                    movement.vx = -1f
                } else if (playerMovement.isMovingRight) {
                    movement.vx = 1f
                } else {
                    movement.vx = 0f
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
