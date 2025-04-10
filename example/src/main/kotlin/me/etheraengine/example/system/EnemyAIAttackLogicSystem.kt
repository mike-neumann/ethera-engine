package me.etheraengine.example.system

import me.etheraengine.example.entity.Enemy
import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.Attack
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component
import java.awt.geom.Point2D

@Component
class EnemyAIAttackLogicSystem(private val player: Player) : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val playerCenterX = player.x + (player.width / 2)
        val playerCenterY = player.y + (player.height / 2)
        val enemies = scene.getFilteredEntities { it is Enemy }

        for (enemy in enemies) {
            val enemyCenterX = enemy.x + (player.width / 2)
            val enemyCenterY = enemy.y + (player.height / 2)
            val distance = Point2D.distance(playerCenterX, playerCenterY, enemyCenterX, enemyCenterY)
            val attack = enemy.getComponent<Attack>()!!

            if (distance > attack.range) continue

            attack.isAttacking = true
        }
    }
}