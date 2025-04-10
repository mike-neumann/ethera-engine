package me.etheraengine.runtime.system

import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.scene.Scene
import java.awt.Graphics2D

/**
 * System interface for rendering related systems implementations
 */
interface RenderingSystem {
    fun render(scene: Scene, g: Graphics2D, now: Long, deltaTime: Long) {}
    fun render(entity: Entity, scene: Scene, g: Graphics2D, now: Long, deltaTime: Long) {}
}