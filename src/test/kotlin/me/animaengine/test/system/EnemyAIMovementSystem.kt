package me.animaengine.test.system

import me.animaengine.entity.Entity
import me.animaengine.g2d.entity.component.Movement2D
import me.animaengine.scene.Scene
import me.animaengine.system.LogicSystem
import me.animaengine.test.entity.component.EnemyAI
import me.animaengine.test.entity.component.Position
import org.springframework.stereotype.Component

@Component
class EnemyAIMovementSystem : LogicSystem {
    override fun update(deltaTime: Long, scene: Scene, entities: List<Entity>) {
        entities
            .filter { it.hasComponent<EnemyAI>() }
            .filter { it.hasComponent<Position>() }
            .filter { it.hasComponent<Movement2D>() }
            .forEach {
                val enemyAi = it.getComponent<EnemyAI>()!!

                if (enemyAi.target == null) {
                    return@forEach
                }

                val enemyPosition = it.getComponent<Position>()!!
                val enemyMovement = it.getComponent<Movement2D>()!!
                val targetPosition = enemyAi.getTargetPosition()
                val distanceX = targetPosition!!.x - enemyPosition.x
                val distanceY = targetPosition.y - enemyPosition.y
                val xMovement = distanceX / enemyMovement.speed
                val yMovement = distanceY / enemyMovement.speed

                enemyMovement.vx = xMovement
                enemyMovement.vy = yMovement
            }
    }
}