package me.etheraengine.flappybird.system

import me.etheraengine.flappybird.entity.Player
import me.etheraengine.flappybird.entity.component.Obstacle
import me.etheraengine.flappybird.entity.component.PlayerMovement
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.entity.component.StateHolder
import me.etheraengine.runtime.g2d.util.CollisionUtils2D.mtv
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class ObstacleCollisionSystem(private val player: Player) : LogicSystem {
    override fun update(entity: Entity, scene: Scene, now: Long, deltaTime: Long) {
        if (!entity.hasComponent<Obstacle>()) return
        val stateHolder = player.getComponent<StateHolder>()!!
        val (tX, tY) = player mtv entity

        if (tX != 0.0 || tY != 0.0) {
            // player collides with pipe, kill player and move outside colliding entity
            stateHolder.state = Player.State.DEAD
            player.x += tX
            player.y += tY

            if (tY != 0.0) {
                player.getComponent<PlayerMovement>()!!.vy = 0.0
            }
        }
    }
}