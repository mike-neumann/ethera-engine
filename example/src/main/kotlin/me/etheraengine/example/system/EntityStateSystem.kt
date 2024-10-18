package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.entity.component.State
import me.etheraengine.g2d.entity.component.Movement2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.Attack
import me.etheraengine.example.entity.component.Direction
import me.etheraengine.example.entity.component.Health
import me.etheraengine.example.entity.component.Position
import org.springframework.stereotype.Component

@Component
class EntityStateSystem : LogicSystem {
    override fun update(deltaTime: Long, scene: Scene, entities: List<Entity>) {
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
                }

                if (state.state == EntityState.DEAD) {
                    scene.removeEntities(it)

                    return@forEach
                }

                if (it.hasComponent<Health>()) {
                    val health = it.getComponent<Health>()!!

                    if (health.health <= 0 && state.state != EntityState.DYING && state.state != EntityState.DEAD) {
                        state.state = EntityState.DYING
                        state.lock(2_000)

                        return@forEach
                    }

                    if (health.health < health.lastHealth && state.state != EntityState.DAMAGE) {
                        health.lastHealth = health.health
                        state.state = EntityState.DAMAGE
                        state.lock(600)

                        return@forEach
                    }
                }

                if (it.hasComponent<Attack>()) {
                    val attack = it.getComponent<Attack>()!!

                    if (attack.isAttacking && state.state != EntityState.ATTACK) {
                        state.state = EntityState.ATTACK
                        state.lock(1_000)

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