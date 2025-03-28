package me.etheraengine.example.system

import me.etheraengine.example.entity.component.EnemyAI
import me.etheraengine.example.entity.component.Position
import me.etheraengine.runtime.g2d.entity.component.Movement2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EnemyAIMovementLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val enemies =
            scene.getFilteredEntities { it.hasComponent<EnemyAI>() && it.hasComponent<Position>() && it.hasComponent<Movement2D>() }

        for (enemy in enemies) {
            val enemyAi = enemy.getComponent<EnemyAI>()!!

            if (enemyAi.target == null) {
                continue
            }
            val enemyPosition = enemy.getComponent<Position>()!!
            val enemyMovement = enemy.getComponent<Movement2D>()!!
            val targetPosition = enemyAi.getTargetPosition()
            val distanceX = targetPosition!!.x - enemyPosition.x
            val distanceY = targetPosition.y - enemyPosition.y
            val xMovement = distanceX / enemyMovement.speed
            val yMovement = distanceY / enemyMovement.speed

            enemyMovement.vx = Math.clamp(xMovement, -1.0, 1.0)
            enemyMovement.vy = Math.clamp(yMovement, -1.0, 1.0)
        }
    }
}