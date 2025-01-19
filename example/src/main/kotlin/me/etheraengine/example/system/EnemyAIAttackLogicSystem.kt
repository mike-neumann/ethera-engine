package me.etheraengine.example.system

import me.etheraengine.example.entity.Enemy
import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.Attack
import me.etheraengine.example.entity.component.Position
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EnemyAIAttackLogicSystem(
    private val player: Player,
) : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val playerPositionComponent = player.getComponent<Position>()!!
        val playerDimensions = player.getComponent<Dimensions2D>()!!
        val playerPosition = Position2D(
            playerPositionComponent.x + (playerDimensions.width / 2),
            playerPositionComponent.y + (playerDimensions.height / 2)
        )

        scene.getEntities {
            it is Enemy
        }.forEach {
            val positionComponent = it.getComponent<Position>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!
            val position = Position2D(
                positionComponent.x + (dimensions.width / 2),
                positionComponent.y + (dimensions.height / 2)
            )
            val attack = it.getComponent<Attack>()!!

            if (position.distance(playerPosition) > attack.range) {
                return@forEach
            }

            attack.isAttacking = true
        }
    }
}