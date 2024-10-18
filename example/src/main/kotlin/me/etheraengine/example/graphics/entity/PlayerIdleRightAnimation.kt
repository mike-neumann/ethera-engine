package me.etheraengine.example.graphics.entity

import me.etheraengine.g2d.graphics.Animation2D
import me.etheraengine.g2d.graphics.Spritesheet2D
import org.springframework.util.ResourceUtils

class PlayerIdleRightAnimation : Animation2D(
    Spritesheet2D(
        ResourceUtils.getFile("classpath:assets/animations/player/idle-right.png"),
        100,
        100,
        1,
        30
    ),
    200,
    true
)