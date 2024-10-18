package me.animaengine.test.system

import me.animaengine.entity.Entity
import me.animaengine.entity.component.State
import me.animaengine.g2d.entity.component.Movement2D
import me.animaengine.g2d.util.CollisionUtils
import me.animaengine.scene.Scene
import me.animaengine.system.LogicSystem
import me.animaengine.test.entity.EntityState
import me.animaengine.test.entity.Player
import me.animaengine.test.entity.component.*
import org.springframework.stereotype.Component

@Component
class PlayerAttackSystem : LogicSystem {
    override fun update(deltaTime: Long, scene: Scene, entities: List<Entity>) {
        entities
            .filterIsInstance<Player>()
            .forEach { player ->
                val state = player.getComponent<State>()!!
                val playerPosition = player.getComponent<Position>()!!
                val attack = player.getComponent<Attack>()!!
                val now = System.currentTimeMillis()

                if (state.state != EntityState.ATTACK || now - attack.lastAttackTime < attack.damageDelay) {
                    return@forEach
                }

                val damageHitbox =
                    when (playerPosition.direction) {
                        Direction.LEFT -> Position(
                            playerPosition.x - attack.range,
                            playerPosition.y,
                            attack.range.toInt(),
                            attack.range.toInt()
                        )

                        Direction.RIGHT -> Position(
                            playerPosition.x + playerPosition.width,
                            playerPosition.y,
                            attack.range.toInt(),
                            attack.range.toInt()
                        )
                    }

                entities
                    .filter { it != player }
                    .filter { it.hasComponent<Position>() }
                    .filter { it.hasComponent<Health>() }
                    .filter {
                        val position = it.getComponent<Position>()!!

                        CollisionUtils.checkCollision(position, damageHitbox)
                    }
                    .forEach {
                        val health = it.getComponent<Health>()!!

                        if (now - health.lastDamageTime >= health.cooldown) {
                            health.health -= attack.damage
                        }

                        val movement = it.getComponent<Movement2D>()

                        if (movement != null) {
                            // apply some knockback
                            movement.vx =
                                when (playerPosition.direction) {
                                    Direction.LEFT -> -attack.knockback
                                    Direction.RIGHT -> attack.knockback
                                }
                        }

                    }
            }
    }
}