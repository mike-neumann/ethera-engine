package me.animaengine.test.world

import me.animaengine.g2d.world.World2D
import java.io.File

open class World(
    file: File,
    val tileSize: Int
) : World2D(file) {
    val tileTypeMask = mask.map {
        it.map { mask -> TileType.entries.find { it.mask == mask }!! }
    }
}