package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.entity.component.State
import me.etheraengine.example.entity.EntityState
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
import java.util.concurrent.ConcurrentLinkedQueue

@Component
class EntityAttackLogicSystem : LogicSystem {
    override fun update(scene: Scene, entities: ConcurrentLinkedQueue<Entity>, now: Long, deltaTime: Long) {
        entities
            .asSequence()
            .filter { it.hasComponent<State>() }
            .filter { it.hasComponent<Position>() }
            .filter { it.hasComponent<Dimension2D>() }
            .filter { it.hasComponent<Attack>() }
            .forEach { entity ->
                val state = entity.getComponent<State>()!!
                val entityPosition = entity.getComponent<Position>()!!
                val entityDimension = entity.getComponent<Dimension2D>()!!
                val attack = entity.getComponent<Attack>()!!

                if (state.state != EntityState.ATTACK || now - attack.lastAttackTime < attack.damageDelay || now - attack.lastAttackTime - attack.damageDelay > attack.damageTimeRange) {
                    return@forEach
                }

                val damagePosition =
                    when (entityPosition.direction) {
                        Direction.LEFT -> Position(
                            entityPosition.x - attack.range,
                            entityPosition.y
                        )

                        Direction.RIGHT -> Position(
                            entityPosition.x + entityDimension.width,
                            entityPosition.y
                        )
                    }
                val damageDimension = Dimension(attack.range.toInt(), attack.range.toInt())

                entities
                    .asSequence()
                    .filter { it != entity }
                    .filter { it.hasComponent<Point2D>() }
                    .filter { it.hasComponent<Dimension2D>() }
                    .filter { it.hasComponent<Health>() }
                    .filter {
                        val position = it.getComponent<Position>()!!
                        val dimension = it.getComponent<Dimension2D>()!!

                        CollisionUtils2D.checkCollision(position, dimension, damagePosition, damageDimension)
                    }
                    .toList()
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
                                when (entityPosition.direction) {
                                    Direction.LEFT -> -attack.knockback
                                    Direction.RIGHT -> attack.knockback
                                }
                        }
                    }
            }
    }
}