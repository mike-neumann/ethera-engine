package me.etheraengine.example.entity.component

import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.g2d.entity.component.Position2D

class EnemyAI(var target: Entity?) {
    fun getTargetPosition() = target?.getComponent<Position2D>()
}