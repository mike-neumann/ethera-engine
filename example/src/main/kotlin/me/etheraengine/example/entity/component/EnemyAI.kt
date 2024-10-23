package me.etheraengine.example.entity.component

import me.etheraengine.entity.Entity
import java.awt.geom.Point2D

class EnemyAI(
    var target: Entity?
) {
    fun getTargetPosition() = target?.getComponent<Point2D>()
}