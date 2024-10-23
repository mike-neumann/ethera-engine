package me.etheraengine.g2d.entity.component

/**
 * Component in the ECS pattern containing information of possible movements on the x and y axis via velocities
 */
open class Movement2D(
    var speed: Double
) {
    var vx = 0.0
    var vy = 0.0

    fun isMoving() = vx != 0.0 || vy != 0.0
}