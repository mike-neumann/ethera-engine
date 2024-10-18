package me.etheraengine.g2d.entity.component

open class Movement2D(
    var speed: Float
) {
    var vx = 0f
    var vy = 0f

    open fun isMoving() = vx != 0f || vy != 0f
}