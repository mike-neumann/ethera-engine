package me.animaengine.system

import me.animaengine.entity.Entity
import me.animaengine.scene.Scene

/**
 * System interface used for logic related systems implementations
 */
fun interface LogicSystem {
    fun update(deltaTime: Long, scene: Scene, entities: List<Entity>)
}