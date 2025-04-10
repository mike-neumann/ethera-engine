package me.etheraengine.flappybird.system

import me.etheraengine.flappybird.entity.Player
import me.etheraengine.runtime.entity.component.StateHolder
import me.etheraengine.runtime.g2d.entity.component.Animations2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class PlayerAnimationLogicSystem(private val player: Player) : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val stateHolder = player.getComponent<StateHolder>()!!
        val animations = player.getComponent<Animations2D>()!!
        val animation = when (stateHolder.state) {
            Player.State.FALLING -> Player.Animation.IDLE
            Player.State.JUMPING -> Player.Animation.IDLE
            Player.State.DEAD -> Player.Animation.DEAD
            else -> throw IllegalStateException("illegal player state '${stateHolder.state}'")
        }

        animations.currentAnimation = animation
    }
}