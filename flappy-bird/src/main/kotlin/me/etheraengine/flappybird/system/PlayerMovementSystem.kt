package me.etheraengine.flappybird.system

import me.etheraengine.flappybird.entity.Player
import me.etheraengine.flappybird.entity.component.PlayerMovement
import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.entity.component.StateHolder
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class PlayerMovementSystem(private val player: Player) : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val movement = player.getComponent<PlayerMovement>()!!
        val stateHolder = player.getComponent<StateHolder>()!!

        if (stateHolder.state == Player.State.JUMPING) {
            stateHolder.state = Player.State.FALLING
            movement.vy = -4.5
        }

        if (player.y >= Ethera.frame.height - 100) {
            movement.vy = 0.0
        }

        if (stateHolder.state == Player.State.DEAD) {
            movement.vx *= .99
        }

        player.x += movement.vx * movement.speed
        player.y += movement.vy
    }
}