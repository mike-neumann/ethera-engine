package me.animaengine.test.world

import me.animaengine.entity.Entity
import me.animaengine.g2d.entity.component.Animations2D
import me.animaengine.test.entity.component.Position

class Tile(
    val tileType: TileType,
    x: Float,
    y: Float,
) : Entity() {
    init {
        addComponents(Position(x, y, TestWorld.tileSize, TestWorld.tileSize))

        if (tileType.sprite != null) {
            addComponents(tileType.sprite)
        }

        if (tileType.animations != null) {
            val animations = Animations2D(
                tileType.animations.keys.firstOrNull() ?: "",
                tileType.animations,
                TestWorld.tileSize,
                TestWorld.tileSize
            )

            addComponents(animations)
        }
    }
}