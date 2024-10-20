package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.entity.component.State
import me.etheraengine.g2d.entity.component.Movement2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.Position
import org.springframework.stereotype.Component

@Component
class EntityPositionMovementSystem : LogicSystem {
    override fun update(scene: Scene, entities: List<Entity>, now: Long, deltaTime: Long) {
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