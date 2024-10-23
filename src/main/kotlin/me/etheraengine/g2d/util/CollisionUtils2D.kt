package me.etheraengine.g2d.util

import me.etheraengine.entity.Entity
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D
import kotlin.math.abs

/**
 * Utility class containing methods to check for collisions on a 2D plane
 */
object CollisionUtils2D {
    /**
     * Calculates the MTV (Minimum Translation Vector) for the given coordinates, representing the translation values needed to move one object out of the other as a Pair<X, Y>
     */
    fun getMiniumTranslationVector(
        ourX: Double,
        ourY: Double,
        ourWidth: Double,
        ourHeight: Double,
        theirX: Double,
        theirY: Double,
        theirWidth: Double,
        theirHeight: Double
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

            return if (abs(mtv.first) < abs(mtv.second)) {
                mtv.first to 0.0
            } else {
                0.0 to mtv.second
            }
        }
    }

    fun checkXCollision(ourX: Double, ourWidth: Double, theirX: Double, theirWidth: Double) =
        (theirX <= ourX + ourWidth || theirX + theirWidth <= ourX + ourWidth) && (theirX >= ourX || theirX + theirWidth >= ourX)

    fun checkYCollision(ourY: Double, ourHeight: Double, theirY: Double, theirHeight: Double) =
        (theirY <= ourY + ourHeight || theirY + theirHeight <= ourY + ourHeight) && (theirY >= ourY || theirY + theirHeight >= ourY)

    fun checkXCollision(
        ourPosition: Point2D,
        ourDimension: Dimension2D,
        theirPosition: Point2D,
        theirDimension2D: Dimension2D
    ) =
        checkXCollision(
            ourPosition.x,
            ourDimension.width,
            theirPosition.x,
            theirDimension2D.width
        )

    fun checkYCollision(
        ourPosition: Point2D,
        ourDimension: Dimension2D,
        theirPosition: Point2D,
        theirDimension: Dimension2D
    ) =
        checkYCollision(
            ourPosition.y,
            ourDimension.height,
            theirPosition.y,
            theirDimension.height
        )

    fun checkCollision(
        ourX: Double,
        ourY: Double,
        ourWidth: Double,
        ourHeight: Double,
        theirX: Double,
        theirY: Double,
        theirWidth: Double,
        theirHeight: Double
    ) = checkXCollision(ourX, ourWidth, theirX, theirWidth) && checkYCollision(ourY, ourHeight, theirY, theirHeight)

    fun checkCollision(
        ourPosition: Point2D,
        ourDimension: Dimension2D,
        theirPosition: Point2D,
        theirDimension: Dimension2D
    ) =
        checkCollision(
            ourPosition.x,
            ourPosition.y,
            ourDimension.width,
            ourDimension.height,
            theirPosition.x,
            theirPosition.y,
            theirDimension.width,
            theirDimension.height
        )

    fun checkCollision(our: Entity, their: Entity): Boolean {
        val ourPosition = our.getComponent<Point2D>()!!
        val ourDimension = our.getComponent<Dimension2D>()!!
        val theirPosition = their.getComponent<Point2D>()!!
        val theirDimension = their.getComponent<Dimension2D>()!!

        return checkCollision(ourPosition, ourDimension, theirPosition, theirDimension)
    }
}