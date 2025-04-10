package me.etheraengine.flappybird.system

import me.etheraengine.flappybird.entity.Player
import me.etheraengine.flappybird.entity.component.PlayerMovement
import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.entity.component.State
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class PlayerMovementSystem(private val player: Player) : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val movement = player.getComponent<PlayerMovement>()!!
        // gravity
        movement.vy += 0.1
        val state = player.getComponent<State>()!!

        if (state.state == Player.State.JUMPING) {
            state.state = Player.State.FALLING
            movement.vy = -4.5
        }

        if (player.y >= Ethera.frame.height - 100) {
            movement.vy = 0.0
        }

        player.x += movement.vx * movement.speed
        player.y += movement.vy
    }
}