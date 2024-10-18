package me.etheraengine.system

import me.etheraengine.entity.Entity
import me.etheraengine.scene.Scene

/**
 * System interface used for logic related systems implementations
 */
fun interface LogicSystem {
    fun update(deltaTime: Long, scene: Scene, entities: List<Entity>)
}