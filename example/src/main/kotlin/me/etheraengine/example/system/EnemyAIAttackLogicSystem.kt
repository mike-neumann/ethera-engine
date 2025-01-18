package me.etheraengine.example.system

import me.etheraengine.example.entity.Enemy
import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.Attack
import me.etheraengine.example.entity.component.Position
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

@Component
class EnemyAIAttackLogicSystem(
    private val player: Player,
) : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val playerPositionComponent = player.getComponent<Position>()!!
        val playerDimension = player.getComponent<Dimension2D>()!!
        val playerPosition = Point2D.Double(
            playerPositionComponent.x + (playerDimension.width / 2),
            playerPositionComponent.y + (playerDimension.height / 2)
        )

        scene.getEntities {
            it is Enemy
        }.forEach {
            val positionComponent = it.getComponent<Position>()!!
            val dimension = it.getComponent<Dimension2D>()!!
            val position = Point2D.Double(
                positionComponent.x + (dimension.width / 2),
                positionComponent.y + (dimension.height / 2)
            )
            val attack = it.getComponent<Attack>()!!

            if (position.distance(playerPosition) > attack.range) {
                return@forEach
            }

            attack.isAttacking = true
        }
    }
}