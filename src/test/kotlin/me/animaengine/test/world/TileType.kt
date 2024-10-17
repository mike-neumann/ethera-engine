package me.animaengine.test.world

import me.animaengine.g2d.entity.component.Sprite2D
import me.animaengine.g2d.graphics.Animation2D
import me.animaengine.g2d.graphics.Spritesheet2D
import org.springframework.util.ResourceUtils

enum class TileType(
    val mask: Char,
    val sprite: Sprite2D? = null,
    val animations: Map<String, Animation2D>? = null,
    val isPassable: Boolean
) {
    AIR(
        ' ',
        Sprite2D(ResourceUtils.getFile("classpath:assets/tiles/air.png")),
        isPassable = false
    ),
    GRASS(
        'G',
        Sprite2D(ResourceUtils.getFile("classpath:assets/tiles/grass.png")),
        isPassable = true
    ),
    WATER(
        'W',
        animations = mapOf(
            "DEFAULT" to Animation2D(
                Spritesheet2D(
                    ResourceUtils.getFile("classpath:assets/tiles/water.png"),
                    100,
                    100,
                    1
                ),
                250,
                true
            )
        ),
        isPassable = false
    );
}