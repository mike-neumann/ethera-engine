package me.etheraengine.flappybird.system

import me.etheraengine.flappybird.entity.Player
import me.etheraengine.flappybird.entity.component.PlayerMovement
import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.entity.component.StateHolder
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.service.SceneService
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class PlayerStateLogicSystem(private val player: Player, private val sceneService: SceneService) : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val stateHolder = player.getComponent<StateHolder>()!!
        if (stateHolder.state == Player.State.DEAD) return
        val playerMovement = player.getComponent<PlayerMovement>()!!

        if (playerMovement.jumping) {
            stateHolder.state = Player.State.JUMPING
            playerMovement.jumping = false
        } else {
            stateHolder.state = Player.State.FALLING
        }

        if (player.y >= Ethera.frame.height - 100) {
            stateHolder.state = Player.State.DEAD
        }
    }
}