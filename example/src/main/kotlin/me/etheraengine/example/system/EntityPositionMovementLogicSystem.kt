package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.entity.component.State
import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.Position
import me.etheraengine.g2d.entity.component.Movement2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedQueue

@Component
class EntityPositionMovementLogicSystem : LogicSystem {
    override fun update(scene: Scene, entities: ConcurrentLinkedQueue<Entity>, now: Long, deltaTime: Long) {
        val deltaSeconds = deltaTime / 1_000f

        entities
            .filter { it.hasComponent<State>() }
            .filter { it.hasComponent<Position>() }
            .filter { it.hasComponent<Movement2D>() }
            .forEach {
                val state = it.getComponent<State>()!!

                if (state.state == EntityState.DYING || state.state == EntityState.DESPAWN || state.state == EntityState.DEAD) {
                    return@forEach
                }

                val position = it.getComponent<Position>()!!
                val movement = it.getComponent<Movement2D>()!!

                // limit velocities to their respective terminal velocities by default
                movement.vx = Math.clamp(movement.vx, -movement.tvx, movement.tvx)
                movement.vy = Math.clamp(movement.vy, -movement.tvy, movement.tvy)

                position.y += movement.vy * movement.speed * deltaSeconds
                position.x += movement.vx * movement.speed * deltaSeconds
            }
    }
}