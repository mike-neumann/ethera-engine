package me.etheraengine.example.system

import me.etheraengine.example.entity.component.EnemyAI
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.g2d.entity.component.Movement2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EnemyAIMovementLogicSystem : LogicSystem {
    override fun update(entity: Entity, scene: Scene, now: Long, deltaTime: Long) {
        if (!entity.hasComponent<EnemyAI>() || !entity.hasComponent<Movement2D>()) return
        val enemyAi = entity.getComponent<EnemyAI>()!!

        if (enemyAi.target == null) return
        val movement = entity.getComponent<Movement2D>()!!
        val distanceX = enemyAi.target!!.x - entity.x
        val distanceY = enemyAi.target!!.y - entity.y
        val xMovement = distanceX / movement.speed
        val yMovement = distanceY / movement.speed

        movement.vx = Math.clamp(xMovement, -movement.tvx, movement.tvx)
        movement.vy = Math.clamp(yMovement, -movement.tvy, movement.tvy)
    }
}