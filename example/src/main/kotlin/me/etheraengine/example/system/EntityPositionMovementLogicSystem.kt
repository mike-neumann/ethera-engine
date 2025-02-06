package me.etheraengine.example.system

import me.etheraengine.runtime.entity.component.State
import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.Position
import me.etheraengine.runtime.g2d.entity.component.Movement2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityPositionMovementLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val deltaSeconds = deltaTime / 1_000f
        val entities =
            scene.getEntities { it.hasComponent<State>() && it.hasComponent<Position>() && it.hasComponent<Movement2D>() }

        for (entity in entities) {
            val state = entity.getComponent<State>()!!

            if (state.state == EntityState.DYING || state.state == EntityState.DESPAWN || state.state == EntityState.DEAD) {
                continue
            }
            val position = entity.getComponent<Position>()!!
            val movement = entity.getComponent<Movement2D>()!!
            // limit velocities to their respective terminal velocities by default
            movement.vx = Math.clamp(movement.vx, -movement.tvx, movement.tvx)
            movement.vy = Math.clamp(movement.vy, -movement.tvy, movement.tvy)

            position.y += movement.vy * movement.speed * deltaSeconds
            position.x += movement.vx * movement.speed * deltaSeconds
        }
    }
}