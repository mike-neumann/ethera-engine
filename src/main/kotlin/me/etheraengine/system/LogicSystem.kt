package me.etheraengine.system

import me.etheraengine.entity.Entity
import me.etheraengine.scene.Scene

/**
 * System interface used for logic related systems implementations
 */
fun interface LogicSystem {
    fun update(scene: Scene, entities: List<Entity>, now: Long, deltaTime: Long)
}