package me.etheraengine.example.system

import me.etheraengine.example.entity.component.*
import me.etheraengine.runtime.entity.component.State
import me.etheraengine.example.entity.EntityState
import me.etheraengine.runtime.g2d.entity.component.*
import me.etheraengine.runtime.g2d.util.CollisionUtils2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityAttackLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val entities =
            scene.getEntities { it.hasComponent<State>() && it.hasComponent<Position>() && it.hasComponent<Dimensions2D>() && it.hasComponent<Attack>() }

        for (entity in entities) {
            val state = entity.getComponent<State>()!!
            val entityPosition = entity.getComponent<Position>()!!
            val entityDimensions = entity.getComponent<Dimensions2D>()!!
            val attack = entity.getComponent<Attack>()!!

            if (state.state != EntityState.ATTACK || now - attack.lastAttackTime < attack.damageDelay || now - attack.lastAttackTime - attack.damageDelay > attack.damageTimeRange) {
                continue
            }
            val damagePosition = when (entityPosition.direction) {
                Direction.LEFT -> Position(
                    entityPosition.x - attack.range,
                    entityPosition.y
                )

                Direction.RIGHT -> Position(
                    entityPosition.x + entityDimensions.width,
                    entityPosition.y
                )
            }
            val damageDimensions = Dimensions2D(attack.range.toInt(), attack.range.toInt())

            scene.getEntities {
                it != entity && it.hasComponent<Position2D>() && it.hasComponent<Dimensions2D>() && it.hasComponent<Health>() && CollisionUtils2D.checkCollision(
                    it.getComponent<Position2D>()!!,
                    it.getComponent<Dimensions2D>()!!,
                    damagePosition,
                    damageDimensions
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
                    movement.vx = when (entityPosition.direction) {
                        Direction.LEFT -> -attack.knockback
                        Direction.RIGHT -> attack.knockback
                    }
                }
            }
        }
    }
}