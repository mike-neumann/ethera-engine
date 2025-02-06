package me.etheraengine.runtime.system

import me.etheraengine.runtime.scene.Scene

/**
 * System interface used for logic related systems implementations
 */
fun interface LogicSystem {
    fun update(scene: Scene, now: Long, deltaTime: Long)
}