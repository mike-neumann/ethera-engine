package me.etheraengine.example.system

import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.*
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.entity.component.StateHolder
import me.etheraengine.runtime.g2d.util.CollisionUtils2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityAttackLogicSystem : LogicSystem {
    override fun update(entity: Entity, scene: Scene, now: Long, deltaTime: Long) {
        if (!entity.hasComponent<StateHolder>() || !entity.hasComponent<AttackHolder>()) return
        val stateHolder = entity.getComponent<StateHolder>()!!
        val attackHolder = entity.getComponent<AttackHolder>()!!

        if (stateHolder.state != EntityState.ATTACK || now - attackHolder.lastAttackTime < attackHolder.damageDelay || now - attackHolder.lastAttackTime - attackHolder.damageDelay > attackHolder.damageTimeRange) {
            return
        }
        val movementDirection = entity.getComponent<MovementDirection>()!!
        val (damageX, damageY) = when (movementDirection.direction) {
            Direction.LEFT -> entity.x - attackHolder.range to entity.y
            Direction.RIGHT -> entity.x + entity.width to entity.y
        }
        val hitEntities = scene.getFilteredEntities {
            it != entity && it.hasComponent<Health>() &&
                    CollisionUtils2D.collidesWith(
                        it.x, it.y, it.width, it.height,
                        damageX, damageY, attackHolder.range.toInt(), attackHolder.range.toInt()
                    )
        }

        for (hitEntity in hitEntities) {
            val health = hitEntity.getComponent<Health>()!!

            if (now - health.lastDamageTime < health.cooldown) continue

            health.health -= attackHolder.damage
            hitEntity.x += when (movementDirection.direction) {
                Direction.LEFT -> -attackHolder.knockback
                Direction.RIGHT -> attackHolder.knockback
            }
        }
    }
}