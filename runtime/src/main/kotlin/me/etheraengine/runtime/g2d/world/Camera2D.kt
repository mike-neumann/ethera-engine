package me.etheraengine.runtime.g2d.world

import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.g2d.entity.component.Dimensions2D
import me.etheraengine.runtime.g2d.entity.component.Position2D
import me.etheraengine.runtime.g2d.util.CollisionUtils2D
import java.awt.Graphics

/**
 * Simple 2d camera object, used to shift the rendering context to the specified position and offset
 */
class Camera2D(var position: Position2D, var offsetX: Double = 0.0, var offsetY: Double = 0.0) {
    fun translate(g: Graphics) = g.translate((-position.x + offsetX).toInt(), (-position.y + offsetY).toInt())
    fun closeTranslation(g: Graphics) = g.translate(-(-position.x + offsetX).toInt(), -(-position.y + offsetY).toInt())
    fun canSee(entity: Entity) = let {
        val entityPosition = entity.getComponent<Position2D>()!!
        val entityDimensions = entity.getComponent<Dimensions2D>()!!
        CollisionUtils2D.checkCollision(
            (position.x - offsetX),
            (position.y - offsetY),
            Ethera.frame.width,
            Ethera.frame.height,
            entityPosition.x,
            entityPosition.y,
            entityDimensions.width,
            entityDimensions.height
        )
    }
}