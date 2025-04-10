package me.etheraengine.runtime.g2d.world

import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.g2d.util.CollisionUtils2D
import java.awt.Graphics

/**
 * Simple 2d camera object, used to shift the rendering context to the specified position and offset
 */
class Camera2D(var x: Double = 0.0, var y: Double = 0.0, var offsetX: Double = 0.0, var offsetY: Double = 0.0) {
    fun translate(g: Graphics) = g.translate((-x + offsetX).toInt(), (-y + offsetY).toInt())
    fun closeTranslation(g: Graphics) = g.translate(-(-x + offsetX).toInt(), -(-y + offsetY).toInt())
    fun canSee(entity: Entity) = let {
        CollisionUtils2D.collidesWith(
            x - offsetX,
            y - offsetY,
            Ethera.frame.width,
            Ethera.frame.height,
            entity.x,
            entity.y,
            entity.width,
            entity.height
        )
    }
}