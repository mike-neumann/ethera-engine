package me.etheraengine.runtime.g2d.util

import me.etheraengine.runtime.entity.Entity
import kotlin.math.abs

/**
 * Utility class containing methods to check for collisions on a 2D plane
 */
object CollisionUtils2D {
    /**
     * Calculates the MTV (Minimum Translation Vector) for the given coordinates, representing the translation values needed to move one object out of the other as a Pair<X, Y>
     */
    fun mtv(
        ourX: Double,
        ourY: Double,
        ourWidth: Int,
        ourHeight: Int,
        theirX: Double,
        theirY: Double,
        theirWidth: Int,
        theirHeight: Int,
    ) = if (!collidesWith(ourX, ourY, ourWidth, ourHeight, theirX, theirY, theirWidth, theirHeight)) {
        0.0 to 0.0
    } else {
        val ourMaxY = ourY + ourHeight
        val theirMaxY = theirY + theirHeight
        val ourMaxX = ourX + ourWidth
        val theirMaxX = theirX + theirWidth
        val topTranslation = -(ourMaxY - theirY)
        val bottomTranslation = theirMaxY - ourY
        val leftTranslation = -(ourMaxX - theirX)
        val rightTranslation = theirMaxX - ourX
        val (x, y) = listOf(leftTranslation, rightTranslation).minBy { abs(it) } to listOf(
            topTranslation,
            bottomTranslation
        ).minBy { abs(it) }
        if (abs(x) < abs(y)) x to 0.0 else 0.0 to y
    }

    infix fun Entity.mtv(their: Entity) = mtv(x, y, width, height, their.x, their.y, their.width, their.height)

    fun collisionX(ourX: Double, ourWidth: Int, theirX: Double, theirWidth: Int) =
        (theirX <= ourX + ourWidth || theirX + theirWidth <= ourX + ourWidth) && (theirX >= ourX || theirX + theirWidth >= ourX)

    infix fun Entity.collisionX(their: Entity) = collisionX(x, width, their.x, their.width)

    fun collisionY(ourY: Double, ourHeight: Int, theirY: Double, theirHeight: Int) =
        (theirY <= ourY + ourHeight || theirY + theirHeight <= ourY + ourHeight) && (theirY >= ourY || theirY + theirHeight >= ourY)

    infix fun Entity.collisionY(their: Entity) = collisionY(y, height, their.y, their.height)

    fun collidesWith(
        ourX: Double,
        ourY: Double,
        ourWidth: Int,
        ourHeight: Int,
        theirX: Double,
        theirY: Double,
        theirWidth: Int,
        theirHeight: Int,
    ) = collisionX(ourX, ourWidth, theirX, theirWidth) && collisionY(ourY, ourHeight, theirY, theirHeight)

    infix fun Entity.collidesWith(their: Entity) = collidesWith(x, y, width, height, their.x, their.y, their.width, their.height)
}