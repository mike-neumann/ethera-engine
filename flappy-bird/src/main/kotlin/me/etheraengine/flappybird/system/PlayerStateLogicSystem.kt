package me.etheraengine.flappybird.system

import me.etheraengine.flappybird.entity.Player
import me.etheraengine.flappybird.entity.component.PlayerMovement
import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.entity.component.State
import me.etheraengine.runtime.g2d.entity.component.Animations2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.service.SceneService
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class PlayerStateLogicSystem(private val player: Player, private val sceneService: SceneService) : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val state = player.getComponent<State>()!!
        if (state.state == Player.State.DEAD) return
        val playerMovement = player.getComponent<PlayerMovement>()!!

        if (playerMovement.jumping) {
            state.state = Player.State.JUMPING
            playerMovement.jumping = false
        }

        if (player.y >= Ethera.frame.height - 100) {
            state.state = Player.State.DEAD
            val playerAnimations = player.getComponent<Animations2D>()!!

            playerAnimations.currentAnimation = Player.Animation.DEAD
        }
    }
}