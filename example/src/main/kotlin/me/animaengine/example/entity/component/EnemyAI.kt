package me.animaengine.example.entity.component

import me.animaengine.entity.Entity
import me.animaengine.g2d.entity.component.Position2D

class EnemyAI(
    var target: Entity?
) {
    fun getTargetPosition() = target?.getComponent<Position2D>()
}