package me.etheraengine.runtime.system

import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.scene.Scene

/**
 * System interface used for logic related systems implementations
 */
interface LogicSystem {
    fun update(scene: Scene, now: Long, deltaTime: Long) {}
    fun update(entity: Entity, scene: Scene, now: Long, deltaTime: Long) {}
}