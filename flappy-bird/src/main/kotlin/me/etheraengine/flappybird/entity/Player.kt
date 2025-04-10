package me.etheraengine.flappybird.entity

import me.etheraengine.flappybird.entity.component.PlayerMovement
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.entity.component.Rotatable
import me.etheraengine.runtime.entity.component.StateHolder
import me.etheraengine.runtime.g2d.entity.component.Animations2D
import me.etheraengine.runtime.g2d.graphics.Animation2D
import me.etheraengine.runtime.g2d.graphics.Spritesheet2D
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils

@Component
class Player : Entity(width = 50, height = 50) {
    init {
        addComponents(
            StateHolder(State.JUMPING),
            PlayerMovement(1.0),
            Rotatable(50.0),
            Animations2D(
                Animation.IDLE,
                mapOf(
                    Animation.IDLE to Animation2D(
                        Spritesheet2D(
                            ResourceUtils.getFile("flappy-bird/src/main/resources/assets/bird_idle.png"),
                            1200,
                            1200,
                            1,
                            0
                        ), 100, true
                    ),
                    Animation.DEAD to Animation2D(
                        Spritesheet2D(
                            ResourceUtils.getFile("flappy-bird/src/main/resources/assets/bird_dead.png"),
                            1200,
                            1200,
                            1,
                            0
                        ), 0, false
                    )
                ), 100, 100, -25, -25
            )
        )
    }

    object State {
        const val FALLING = "FALLING"
        const val JUMPING = "JUMPING"
        const val DEAD = "DEAD"
    }

    object Animation {
        const val IDLE = "IDLE"
        const val DEAD = "DEAD"
    }
}