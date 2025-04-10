package me.etheraengine.example.system

import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.PlayerMovement
import me.etheraengine.runtime.g2d.entity.component.Movement2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component
import kotlin.math.sqrt

@Component
class PlayerMovementLogicSystem(private val player: Player) : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val playerMovement = player.getComponent<PlayerMovement>()!!
        val movement = player.getComponent<Movement2D>()!!

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