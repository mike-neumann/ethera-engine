package me.etheraengine.example.system

import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.*
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.entity.component.StateHolder
import me.etheraengine.runtime.g2d.entity.component.Movement2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.service.SoundService
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityStateLogicSystem(private val soundService: SoundService) : LogicSystem {
    override fun update(entity: Entity, scene: Scene, now: Long, deltaTime: Long) {
        if (!entity.hasComponent<StateHolder>()) return
        val stateHolder = entity.getComponent<StateHolder>()!!
        if (System.currentTimeMillis() - stateHolder.lockedAt < stateHolder.lockedFor) return

        stateHolder.unlock()

        if (stateHolder.state == EntityState.DYING) {
            stateHolder.state = EntityState.DEAD
            soundService.playSound("despawn.wav")
            stateHolder.lock(450)
            return
        }

        if (stateHolder.state == EntityState.DEAD) {
            stateHolder.state = EntityState.DESPAWN
            return
        }

        if (stateHolder.state == EntityState.DESPAWN) {
            entity.markedForRemoval = true
            return
        }

        entity.getComponent<Health>()?.let {
            if (it.health < it.lastHealth && stateHolder.state != EntityState.DAMAGE) {
                it.lastHealth = it.health
                stateHolder.state = EntityState.DAMAGE
                soundService.playSound("damage.wav")
                stateHolder.lock(600)
                return
            }

            if (it.health <= 0 && stateHolder.state != EntityState.DYING && stateHolder.state != EntityState.DEAD) {
                stateHolder.state = EntityState.DYING
                soundService.playSound("death.wav")
                stateHolder.lock(4_000)
                return
            }
        }

        entity.getComponent<AttackHolder>()?.let {
            if (it.isAttacking && stateHolder.state != EntityState.ATTACK) {
                it.isAttacking = false
                it.lastAttackTime = System.currentTimeMillis()
                stateHolder.state = EntityState.ATTACK
                soundService.playSound("attack.wav")
                stateHolder.lock(750)
                return
            }
        }

        entity.getComponent<Movement2D>()?.let {
            if (it.isMoving) {
                stateHolder.state = EntityState.WALK
                val movementDirection = entity.getComponent<MovementDirection>()!!

                if (it.vx > 0f) {
                    movementDirection.direction = Direction.RIGHT
                } else if (it.vx < 0f) {
                    movementDirection.direction = Direction.LEFT
                }
            } else {
                stateHolder.state = EntityState.IDLE
            }
        }
    }
}