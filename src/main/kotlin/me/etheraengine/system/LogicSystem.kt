package me.etheraengine.system

import me.etheraengine.entity.Entity
import me.etheraengine.scene.Scene
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * System interface used for logic related systems implementations
 */
fun interface LogicSystem {
    fun update(scene: Scene, entities: ConcurrentLinkedQueue<Entity>, now: Long, deltaTime: Long)
}