package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.entity.component.State
import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.Attack
import me.etheraengine.example.entity.component.Direction
import me.etheraengine.example.entity.component.Health
import me.etheraengine.example.entity.component.Position
import me.etheraengine.g2d.entity.component.Movement2D
import me.etheraengine.scene.Scene
import me.etheraengine.service.SoundService
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityStateSystem(
    private val soundService: SoundService
) : LogicSystem {
    override fun update(scene: Scene, entities: List<Entity>, now: Long, deltaTime: Long) {
        entities
            .filter { it.hasComponent<State>() }
            .forEach { entity ->
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
                        it.lastAttackTime = System.currentTimeMillis()
                        state.state = EntityState.ATTACK
                        soundService.playSound("attack.wav")
                        state.lock(750)

                        return@forEach
                    }
                }

                entity.getComponent<Movement2D>()?.let {
                    if (it.isMoving()) {
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