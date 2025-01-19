package me.etheraengine.example.entity.component

import me.etheraengine.entity.Entity
import me.etheraengine.g2d.entity.component.Position2D

class EnemyAI(
    var target: Entity?,
) {
    fun getTargetPosition() = target?.getComponent<Position2D>()
}