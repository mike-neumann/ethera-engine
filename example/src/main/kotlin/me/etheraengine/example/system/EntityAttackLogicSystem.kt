package me.etheraengine.example.system

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

@Component
class EntityAttackLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        scene.getEntities {
            it.hasComponent<State>() && it.hasComponent<Position>() && it.hasComponent<Dimension2D>() && it.hasComponent<Attack>()
        }.forEach { entity ->
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

            scene.getEntities {
                it != entity && it.hasComponent<Point2D>() && it.hasComponent<Dimension2D>() && it.hasComponent<Health>() && CollisionUtils2D.checkCollision(
                    it.getComponent<Position>()!!,
                    it.getComponent<Dimension2D>()!!,
                    damagePosition,
                    damageDimension
                )
            }.forEach {
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