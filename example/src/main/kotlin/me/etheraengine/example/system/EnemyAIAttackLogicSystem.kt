package me.etheraengine.example.system

import me.etheraengine.example.entity.Enemy
import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.AttackHolder
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component
import java.awt.geom.Point2D

@Component
class EnemyAIAttackLogicSystem(private val player: Player) : LogicSystem {
    private var playerCenterX = 0.0
    private var playerCenterY = 0.0

    override fun update(entity: Entity, scene: Scene, now: Long, deltaTime: Long) {
        if (entity !is Enemy) return
        val enemyCenterX = entity.x + (player.width / 2)
        val enemyCenterY = entity.y + (player.height / 2)
        val distance = Point2D.distance(playerCenterX, playerCenterY, enemyCenterX, enemyCenterY)
        val attackHolder = entity.getComponent<AttackHolder>()!!

        if (distance > attackHolder.range) return

        attackHolder.isAttacking = true
    }

    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        playerCenterX = player.x + (player.width / 2)
        playerCenterY = player.y + (player.height / 2)
    }
}