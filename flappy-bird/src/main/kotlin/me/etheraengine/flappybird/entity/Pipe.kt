package me.etheraengine.flappybird.entity

import me.etheraengine.flappybird.entity.component.Obstacle
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.g2d.entity.component.Sprite2D
import org.springframework.util.ResourceUtils

class Pipe(type: String, x: Double, y: Double, width: Int, height: Int) : Entity(x, y, width, height) {
    init {
        if (type == Type.FILL) {
            // only FILL type pipes should be collidable so the player doesn't get stuck
            addComponents(Obstacle())
        }
        val sprite = when (type) {
            Type.PART -> Sprite2D(ResourceUtils.getFile("flappy-bird/src/main/resources/assets/pipe_part.png"), 0, 0)
            Type.FILL -> Sprite2D(ResourceUtils.getFile("flappy-bird/src/main/resources/assets/pipe_fill.png"), 0, 0)
            else -> throw IllegalArgumentException("Unsupported pipe type: $type")
        }

        addComponents(sprite)
    }

    object Type {
        const val PART = "PART"
        const val FILL = "FILL"
    }
}