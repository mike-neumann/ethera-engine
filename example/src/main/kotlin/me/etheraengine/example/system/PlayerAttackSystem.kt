package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.entity.component.State
import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.Attack
import me.etheraengine.example.entity.component.Direction
import me.etheraengine.example.entity.component.Health
import me.etheraengine.example.entity.component.Position
import me.etheraengine.g2d.entity.component.Movement2D
import me.etheraengine.g2d.util.CollisionUtils
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
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

                        if (now - health.lastDamageTime < health.cooldown) {
                            return@forEach
                        }

                        attack.lastAttackHitTime = System.currentTimeMillis()
                        health.health -= attack.damage

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