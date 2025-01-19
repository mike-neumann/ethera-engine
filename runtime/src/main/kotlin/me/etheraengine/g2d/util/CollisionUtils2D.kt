package me.etheraengine.g2d.util

import me.etheraengine.entity.Entity
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Position2D
import kotlin.math.abs

/**
 * Utility class containing methods to check for collisions on a 2D plane
 */
object CollisionUtils2D {
    /**
     * Calculates the MTV (Minimum Translation Vector) for the given coordinates, representing the translation values needed to move one object out of the other as a Pair<X, Y>
     */
    fun getMinimumTranslationVector(
        ourX: Double,
        ourY: Double,
        ourWidth: Int,
        ourHeight: Int,
        theirX: Double,
        theirY: Double,
        theirWidth: Int,
        theirHeight: Int,
    ): Pair<Double, Double> {
        if (!checkXCollision(ourX, ourWidth, theirX, theirWidth) && !checkYCollision(
                ourY,
                ourHeight,
                theirY,
                theirHeight
            )
        ) {
            return 0.0 to 0.0
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

            return when (abs(mtv.first) < abs(mtv.second)) {
                true -> mtv.first to 0.0
                false -> 0.0 to mtv.second
            }
        }
    }

    fun getMinimumTranslationVector(
        our: Entity,
        their: Entity,
    ): Pair<Double, Double> {
        val ourPosition = our.getComponent<Position2D>()!!
        val ourDimensions = our.getComponent<Dimensions2D>()!!
        val theirPosition = their.getComponent<Position2D>()!!
        val theirDimensions = their.getComponent<Dimensions2D>()!!

        return getMinimumTranslationVector(
            ourPosition.x,
            ourPosition.y,
            ourDimensions.width,
            ourDimensions.height,
            theirPosition.x,
            theirPosition.y,
            theirDimensions.width,
            theirDimensions.height
        )
    }

    fun checkXCollision(ourX: Double, ourWidth: Int, theirX: Double, theirWidth: Int) =
        (theirX <= ourX + ourWidth || theirX + theirWidth <= ourX + ourWidth) && (theirX >= ourX || theirX + theirWidth >= ourX)

    fun checkYCollision(ourY: Double, ourHeight: Int, theirY: Double, theirHeight: Int) =
        (theirY <= ourY + ourHeight || theirY + theirHeight <= ourY + ourHeight) && (theirY >= ourY || theirY + theirHeight >= ourY)

    fun checkXCollision(
        ourPosition: Position2D,
        ourDimensions: Dimensions2D,
        theirPosition: Position2D,
        theirDimensions: Dimensions2D,
    ) =
        checkXCollision(
            ourPosition.x,
            ourDimensions.width,
            theirPosition.x,
            theirDimensions.width
        )

    fun checkYCollision(
        ourPosition: Position2D,
        ourDimensions: Dimensions2D,
        theirPosition: Position2D,
        theirDimensions: Dimensions2D,
    ) =
        checkYCollision(
            ourPosition.y,
            ourDimensions.height,
            theirPosition.y,
            theirDimensions.height
        )

    fun checkCollision(
        ourX: Double,
        ourY: Double,
        ourWidth: Int,
        ourHeight: Int,
        theirX: Double,
        theirY: Double,
        theirWidth: Int,
        theirHeight: Int,
    ) = checkXCollision(ourX, ourWidth, theirX, theirWidth) && checkYCollision(ourY, ourHeight, theirY, theirHeight)

    fun checkCollision(
        ourPosition: Position2D,
        ourDimensions: Dimensions2D,
        theirPosition: Position2D,
        theirDimensions: Dimensions2D,
    ) =
        checkCollision(
            ourPosition.x,
            ourPosition.y,
            ourDimensions.width,
            ourDimensions.height,
            theirPosition.x,
            theirPosition.y,
            theirDimensions.width,
            theirDimensions.height
        )

    fun checkCollision(our: Entity, their: Entity): Boolean {
        val ourPosition = our.getComponent<Position2D>()!!
        val ourDimension = our.getComponent<Dimensions2D>()!!
        val theirPosition = their.getComponent<Position2D>()!!
        val theirDimension = their.getComponent<Dimensions2D>()!!

        return checkCollision(ourPosition, ourDimension, theirPosition, theirDimension)
    }
}