package me.etheraengine.example.system

import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.*
import me.etheraengine.runtime.entity.component.State
import me.etheraengine.runtime.g2d.entity.component.Movement2D
import me.etheraengine.runtime.g2d.util.CollisionUtils2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityAttackLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val entities =
            scene.getFilteredEntities { it.hasComponent<State>() && it.hasComponent<Attack>() }

        for (entity in entities) {
            val state = entity.getComponent<State>()!!
            val attack = entity.getComponent<Attack>()!!

            if (state.state != EntityState.ATTACK || now - attack.lastAttackTime < attack.damageDelay || now - attack.lastAttackTime - attack.damageDelay > attack.damageTimeRange) {
                continue
            }
            val movementDirection = entity.getComponent<MovementDirection>()!!
            val (damageX, damageY) = when (movementDirection.direction) {
                Direction.LEFT -> entity.x - attack.range to entity.y
                Direction.RIGHT -> entity.x + entity.width to entity.y
            }

            scene.getFilteredEntities {
                it != entity && it.hasComponent<Health>() && CollisionUtils2D.collidesWith(
                    it.x, it.y, it.width, it.height,
                    damageX, damageY, attack.range.toInt(), attack.range.toInt()
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
                    movement.vx = when (movementDirection.direction) {
                        Direction.LEFT -> -attack.knockback
                        Direction.RIGHT -> attack.knockback
                    }
                }
            }
        }
    }
}