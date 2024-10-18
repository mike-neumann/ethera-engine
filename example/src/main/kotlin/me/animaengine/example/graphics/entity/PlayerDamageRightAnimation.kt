package me.animaengine.example.graphics.entity

import me.animaengine.g2d.graphics.Animation2D
import me.animaengine.g2d.graphics.Spritesheet2D
import org.springframework.util.ResourceUtils

class PlayerDamageRightAnimation : Animation2D(
    Spritesheet2D(
        ResourceUtils.getFile("classpath:assets/animations/player/damage-right.png"),
        100,
        100,
        1,
        30
    ),
    200,
    false
)