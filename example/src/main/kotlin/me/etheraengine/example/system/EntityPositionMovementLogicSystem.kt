package me.etheraengine.example.system

import me.etheraengine.example.entity.EntityState
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.entity.component.StateHolder
import me.etheraengine.runtime.g2d.entity.component.Movement2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityPositionMovementLogicSystem : LogicSystem {
    override fun update(entity: Entity, scene: Scene, now: Long, deltaTime: Long) {
        if (!entity.hasComponent<StateHolder>() || !entity.hasComponent<Movement2D>()) return
        val stateHolder = entity.getComponent<StateHolder>()!!

        if (stateHolder.state == EntityState.DYING || stateHolder.state == EntityState.DESPAWN || stateHolder.state == EntityState.DEAD) return
        val movement = entity.getComponent<Movement2D>()!!
        // limit velocities to their respective terminal velocities by default
        movement.vx = Math.clamp(movement.vx, -movement.tvx, movement.tvx)
        movement.vy = Math.clamp(movement.vy, -movement.tvy, movement.tvy)
        entity.y += movement.vy * movement.speed
        entity.x += movement.vx * movement.speed
    }
}