package me.etheraengine.example.world

import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.g2d.entity.component.Animations2D

class Tile(val tileType: TileType, x: Double, y: Double) : Entity(x, y, ExampleWorld.tileSize, ExampleWorld.tileSize) {
    init {
        if (tileType.sprite != null) {
            addComponents(tileType.sprite)
        }

        if (tileType.animations != null) {
            val animations = Animations2D(
                tileType.animations.keys.firstOrNull()
                    ?: "",
                tileType.animations,
                ExampleWorld.tileSize,
                ExampleWorld.tileSize
            )

            addComponents(animations)
        }
    }
}