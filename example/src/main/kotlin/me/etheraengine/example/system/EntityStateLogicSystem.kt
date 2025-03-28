package me.etheraengine.example.system

import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.*
import me.etheraengine.runtime.entity.component.State
import me.etheraengine.runtime.g2d.entity.component.Movement2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.service.SoundService
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityStateLogicSystem(private val soundService: SoundService) : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val entities = scene.getFilteredEntities { it.hasComponent<State>() }
        // must use forEach function call here, since current kotlin versions don't support non-local lambda continue / break statements
        entities.forEach { entity ->
            val state = entity.getComponent<State>()!!

            if (System.currentTimeMillis() - state.lockTime < state.lockedTime) {
                return@forEach
            }

            state.unlock()

            if (state.state == EntityState.DYING) {
                state.state = EntityState.DEAD
                soundService.playSound("despawn.wav")
                state.lock(450)
                return@forEach
            }

            if (state.state == EntityState.DEAD) {
                state.state = EntityState.DESPAWN
                return@forEach
            }

            if (state.state == EntityState.DESPAWN) {
                scene.removeEntities(entity)
                return@forEach
            }

            entity.getComponent<Health>()?.let {
                if (it.health < it.lastHealth && state.state != EntityState.DAMAGE) {
                    it.lastHealth = it.health
                    state.state = EntityState.DAMAGE
                    soundService.playSound("damage.wav")
                    state.lock(600)
                    return@forEach
                }

                if (it.health <= 0 && state.state != EntityState.DYING && state.state != EntityState.DEAD) {
                    state.state = EntityState.DYING
                    soundService.playSound("death.wav")
                    state.lock(4_000)
                    return@forEach
                }
            }

            entity.getComponent<Attack>()?.let {
                if (it.isAttacking && state.state != EntityState.ATTACK) {
                    it.isAttacking = false
                    it.lastAttackTime = System.currentTimeMillis()
                    state.state = EntityState.ATTACK
                    soundService.playSound("attack.wav")
                    state.lock(750)
                    return@forEach
                }
            }

            entity.getComponent<Movement2D>()?.let {
                if (it.isMoving) {
                    state.state = EntityState.WALK
                    val position = entity.getComponent<Position>()!!

                    if (it.vx > 0f) {
                        position.direction = Direction.RIGHT
                    } else if (it.vx < 0f) {
                        position.direction = Direction.LEFT
                    }
                } else {
                    state.state = EntityState.IDLE
                }
            }
        }
    }
}