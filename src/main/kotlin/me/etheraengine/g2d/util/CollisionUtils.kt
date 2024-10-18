package me.etheraengine.g2d.util

import me.etheraengine.entity.Entity
import me.etheraengine.g2d.entity.component.Position2D
import kotlin.math.abs

object CollisionUtils {
    /**
     * Calculates the MTV (Minimum Translation Vector) for the given coordinates, representing the translation values needed to move one object out of the other as a Pair<X, Y>
     */
    fun getMiniumTranslationVector(
        ourX: Float,
        ourY: Float,
        ourWidth: Int,
        ourHeight: Int,
        theirX: Float,
        theirY: Float,
        theirWidth: Int,
        theirHeight: Int
    ): Pair<Float, Float> {
        if (!checkXCollision(ourX, ourWidth, theirX, theirWidth) && !checkYCollision(
                ourY,
                ourHeight,
                theirY,
                theirHeight
            )
        ) {
            return 0f to 0f
        } else {
            val ourMaxY = ourY + ourHeight
            val theirMaxY = theirY + theirHeight
            val ourMaxX = ourX + ourWidth
            val theirMaxX = theirX + theirWidth
            val topTranslation = -(ourMaxY - theirY)
            val bottomTranslation = theirMaxY - ourY
            val leftTranslation = -(ourMaxX - theirX)
            val rightTranslation = theirMaxX - ourX
            val mtv = listOf(leftTranslation, rightTranslation).minBy { abs(it) } to listOf(
                topTranslation,
                bottomTranslation
            ).minBy { abs(it) }

            return if (abs(mtv.first) < abs(mtv.second)) {
                mtv.first to 0f
            } else {
                0f to mtv.second
            }
        }
    }

    fun checkXCollision(ourX: Float, ourWidth: Int, theirX: Float, theirWidth: Int) =
        (theirX <= ourX + ourWidth || theirX + theirWidth <= ourX + ourWidth) && (theirX >= ourX || theirX + theirWidth >= ourX)

    fun checkYCollision(ourY: Float, ourHeight: Int, theirY: Float, theirHeight: Int) =
        (theirY <= ourY + ourHeight || theirY + theirHeight <= ourY + ourHeight) && (theirY >= ourY || theirY + theirHeight >= ourY)

    fun checkXCollision(our: Position2D, their: Position2D) =
        checkXCollision(our.x, our.width, their.x, their.width)

    fun checkYCollision(our: Position2D, their: Position2D) =
        checkYCollision(our.y, our.height, their.y, their.height)

    fun checkCollision(
        ourX: Float,
        ourY: Float,
        ourWidth: Int,
        ourHeight: Int,
        theirX: Float,
        theirY: Float,
        theirWidth: Int,
        theirHeight: Int
    ) = checkXCollision(ourX, ourWidth, theirX, theirWidth) && checkYCollision(ourY, ourHeight, theirY, theirHeight)

    fun checkCollision(our: Position2D, their: Position2D) =
        checkCollision(our.x, our.y, our.width, our.height, their.x, their.y, their.width, their.height)

    fun checkCollision(our: Entity, their: Entity): Boolean {
        val ourPosition = our.getComponent<Position2D>()!!
        val theirPosition = their.getComponent<Position2D>()!!

        return checkCollision(ourPosition, theirPosition)
    }
}