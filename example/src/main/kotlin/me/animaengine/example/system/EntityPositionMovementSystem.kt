package me.animaengine.example.system

import me.animaengine.entity.Entity
import me.animaengine.entity.component.State
import me.animaengine.g2d.entity.component.Movement2D
import me.animaengine.scene.Scene
import me.animaengine.system.LogicSystem
import me.animaengine.example.entity.EntityState
import me.animaengine.example.entity.component.Position
import org.springframework.stereotype.Component

@Component
class EntityPositionMovementSystem : LogicSystem {
    override fun update(deltaTime: Long, scene: Scene, entities: List<Entity>) {
        val deltaSeconds = deltaTime / 1_000f

        entities
            .filter { it.hasComponent<State>() }
            .filter { it.hasComponent<Position>() }
            .filter { it.hasComponent<Movement2D>() }
            .forEach {
                val state = it.getComponent<State>()!!

                if (state.state == EntityState.DYING) {
                    return@forEach
                }

                val position = it.getComponent<Position>()!!
                val movement = it.getComponent<Movement2D>()!!

                position.y += movement.vy * movement.speed * deltaSeconds
                position.x += movement.vx * movement.speed * deltaSeconds
            }
    }
}