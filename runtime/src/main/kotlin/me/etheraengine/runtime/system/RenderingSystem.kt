package me.etheraengine.runtime.system

import me.etheraengine.runtime.scene.Scene
import java.awt.Graphics

/**
 * System interface for rendering related systems implementations
 */
fun interface RenderingSystem {
    fun render(scene: Scene, g: Graphics, now: Long, deltaTime: Long)
}