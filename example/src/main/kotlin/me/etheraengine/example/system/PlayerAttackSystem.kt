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
import me.etheraengine.g2d.util.CollisionUtils2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component
import java.awt.Dimension
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

@Component
class PlayerAttackSystem : LogicSystem {
    override fun update(scene: Scene, entities: List<Entity>, now: Long, deltaTime: Long) {
        entities
            .filterIsInstance<Player>()
            .forEach { player ->
                val state = player.getComponent<State>()!!
                val playerPosition = player.getComponent<Position>()!!
                val playerDimension = player.getComponent<Dimension2D>()!!
                val attack = player.getComponent<Attack>()!!

                if (state.state != EntityState.ATTACK || now - attack.lastAttackTime < attack.damageDelay || now - attack.lastAttackTime - attack.damageDelay > attack.damageTimeRange) {
                    return@forEach
                }

                val damagePosition =
                    when (playerPosition.direction) {
                        Direction.LEFT -> Position(
                            playerPosition.x - attack.range,
                            playerPosition.y
                        )

                        Direction.RIGHT -> Position(
                            playerPosition.x + playerDimension.width,
                            playerPosition.y
                        )
                    }
                val damageDimension = Dimension(attack.range.toInt(), attack.range.toInt())

                entities
                    .filter { it != player }
                    .filter { it.hasComponent<Point2D>() }
                    .filter { it.hasComponent<Dimension2D>() }
                    .filter { it.hasComponent<Health>() }
                    .filter {
                        val position = it.getComponent<Position>()!!
                        val dimension = it.getComponent<Dimension2D>()!!

                        CollisionUtils2D.checkCollision(position, dimension, damagePosition, damageDimension)
                    }
                    .forEach {
                        val health = it.getComponent<Health>()!!

                        if (now - health.lastDamageTime < health.cooldown) {
                            return@forEach
                        }

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