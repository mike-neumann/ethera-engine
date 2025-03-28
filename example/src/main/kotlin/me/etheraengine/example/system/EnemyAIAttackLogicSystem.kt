package me.etheraengine.example.system

import me.etheraengine.example.entity.Enemy
import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.Attack
import me.etheraengine.example.entity.component.Position
import me.etheraengine.runtime.g2d.entity.component.Dimensions2D
import me.etheraengine.runtime.g2d.entity.component.Position2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EnemyAIAttackLogicSystem(private val player: Player) : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val playerPositionComponent = player.getComponent<Position>()!!
        val playerDimensions = player.getComponent<Dimensions2D>()!!
        val playerPosition = Position2D(
            playerPositionComponent.x + (playerDimensions.width / 2),
            playerPositionComponent.y + (playerDimensions.height / 2)
        )
        val enemies = scene.getFilteredEntities { it is Enemy }

        for (enemy in enemies) {
            val positionComponent = enemy.getComponent<Position>()!!
            val dimensions = enemy.getComponent<Dimensions2D>()!!
            val position = Position2D(
                positionComponent.x + (dimensions.width / 2),
                positionComponent.y + (dimensions.height / 2)
            )
            val attack = enemy.getComponent<Attack>()!!

            if (position.distance(playerPosition) > attack.range) {
                continue
            }

            attack.isAttacking = true
        }
    }
}