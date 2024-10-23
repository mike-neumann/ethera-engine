package me.etheraengine.example.world

import me.etheraengine.entity.Entity
import me.etheraengine.example.entity.component.Position
import me.etheraengine.g2d.entity.component.Animations2D
import java.awt.Dimension

class Tile(
    val tileType: TileType,
    x: Double,
    y: Double
) : Entity() {
    init {
        addComponents(
            Position(x, y),
            Dimension(ExampleWorld.tileSize, ExampleWorld.tileSize),
        )

        if (tileType.sprite != null) {
            addComponents(tileType.sprite)
        }

        if (tileType.animations != null) {
            val animations = Animations2D(
                tileType.animations.keys.firstOrNull() ?: "",
                tileType.animations,
                ExampleWorld.tileSize,
                ExampleWorld.tileSize
            )

            addComponents(animations)
        }
    }
}