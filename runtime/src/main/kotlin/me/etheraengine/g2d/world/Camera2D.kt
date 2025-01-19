package me.etheraengine.g2d.world

import me.etheraengine.g2d.entity.component.Position2D
import java.awt.Graphics

/**
 * Simple 2d camera object, used to shift the rendering context to the specified position and offset
 */
class Camera2D(
    var position: Position2D,
    var offsetX: Double = 0.0,
    var offsetY: Double = 0.0,
) {
    fun translate(g: Graphics) {
        g.translate((-position.x + offsetX).toInt(), (-position.y + offsetY).toInt())
    }

    fun closeTranslation(g: Graphics) {
        g.translate(-(-position.x + offsetX).toInt(), -(-position.y + offsetY).toInt())
    }
}