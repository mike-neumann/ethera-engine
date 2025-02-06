package me.etheraengine.runtime.g2d.entity.component

import java.awt.Dimension

/**
 * Convenience class to standardize which dimensions component is used across multiple implementations
 */
open class Dimensions2D(width: Int, height: Int) : Dimension(width, height)