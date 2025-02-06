package me.etheraengine.runtime.g2d.world

import java.io.File

/**
 * Simple class to load a 2D world tile map
 */
open class World2D(val file: File) {
    /**
     * 2D list containing all tile characters in the following order (parent list: y, sub list: x)
     */
    val mask = file.readLines()
        .map { it.toCharArray() }
        .toMutableList()
}