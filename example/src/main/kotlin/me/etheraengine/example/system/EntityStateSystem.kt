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
            .forEach {
                val state = it.getComponent<State>()!!

                if (System.currentTimeMillis() - state.lockTime < state.lockedTime) {
                    return@forEach
                }

                state.unlock()

                if (state.state == EntityState.DYING) {
                    state.state = EntityState.DEAD
                    soundService.playSound("despawn.wav", false)
                    state.lock(450)

                    return@forEach
                }

                if (state.state == EntityState.DEAD) {
                    state.state = EntityState.DESPAWN

                    return@forEach
                }

                if (state.state == EntityState.DESPAWN) {
                    scene.removeEntities(it)

                    return@forEach
                }

                if (it.hasComponent<Health>()) {
                    val health = it.getComponent<Health>()!!

                    if (health.health < health.lastHealth && state.state != EntityState.DAMAGE) {
                        health.lastHealth = health.health
                        state.state = EntityState.DAMAGE
                        soundService.playSound("damage.wav", false)
                        state.lock(600)

                        return@forEach
                    }

                    if (health.health <= 0 && state.state != EntityState.DYING && state.state != EntityState.DEAD) {
                        state.state = EntityState.DYING
                        soundService.playSound("death.wav", false)
                        state.lock(4_000)

                        return@forEach
                    }
                }

                if (it.hasComponent<Attack>()) {
                    val attack = it.getComponent<Attack>()!!

                    if (attack.isAttacking && state.state != EntityState.ATTACK) {
                        attack.lastAttackTime = System.currentTimeMillis()
                        state.state = EntityState.ATTACK
                        soundService.playSound("attack.wav", false)
                        state.lock(750)

                        return@forEach
                    }
                }

                if (it.hasComponent<Movement2D>()) {
                    val movement = it.getComponent<Movement2D>()!!

                    if (movement.isMoving()) {
                        state.state = EntityState.WALK

                        val position = it.getComponent<Position>()!!

                        if (movement.vx > 0f) {
                            position.direction = Direction.RIGHT
                        } else if (movement.vx < 0f) {
                            position.direction = Direction.LEFT
                        }
                    } else {
                        state.state = EntityState.IDLE
                    }
                }
            }
    }
}