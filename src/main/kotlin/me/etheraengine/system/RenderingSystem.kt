package me.etheraengine.system

import me.etheraengine.entity.Entity
import me.etheraengine.scene.Scene
import java.awt.Graphics
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * System interface for rendering related systems implementations
 */
fun interface RenderingSystem {
    fun render(scene: Scene, entities: ConcurrentLinkedQueue<Entity>, g: Graphics, now: Long, deltaTime: Long)
}