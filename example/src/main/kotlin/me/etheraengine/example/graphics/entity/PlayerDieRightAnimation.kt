package me.etheraengine.example.graphics.entity

import me.etheraengine.g2d.graphics.Animation2D
import me.etheraengine.g2d.graphics.Spritesheet2D
import org.springframework.util.ResourceUtils

class PlayerDieRightAnimation : Animation2D(
    Spritesheet2D(
        ResourceUtils.getFile("classpath:assets/animations/player/die-right.png"),
        100,
        100,
        1,
        30
    ),
    250,
    false
)