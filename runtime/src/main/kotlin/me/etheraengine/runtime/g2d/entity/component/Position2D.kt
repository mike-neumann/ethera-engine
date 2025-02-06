package me.etheraengine.runtime.g2d.entity.component

import java.awt.geom.Point2D

/**
 * Convenience class to standardize which position component is used across multiple implementations
 */
open class Position2D(x: kotlin.Double, y: kotlin.Double) : Point2D.Double(x, y)