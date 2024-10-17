package me.animaengine.g2d.entity.component

import kotlin.math.abs

open class Position2D(
    var x: Float,
    var y: Float,
    var width: Int,
    var height: Int
) {
    fun distance(theirX: Float, theirY: Float) = abs(((x + width) - theirX) + ((y + height) - theirY))
    fun distance(their: Position2D) = distance(their.x, their.y)
}