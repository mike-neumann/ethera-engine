package me.animaengine.system

import me.animaengine.entity.Entity
import me.animaengine.scene.Scene
import java.awt.Graphics

/**
 * System interface for rendering related systems implementations
 */
fun interface RenderingSystem {
    fun render(g: Graphics, scene: Scene, entities: List<Entity>)
}