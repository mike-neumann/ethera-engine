package me.etheraengine.system

import me.etheraengine.scene.Scene

/**
 * System interface used for logic related systems implementations
 */
fun interface LogicSystem {
    fun update(scene: Scene, now: Long, deltaTime: Long)
}