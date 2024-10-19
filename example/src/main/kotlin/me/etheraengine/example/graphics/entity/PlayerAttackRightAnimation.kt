package me.etheraengine.example.graphics.entity

import me.etheraengine.g2d.graphics.Animation2D
import me.etheraengine.g2d.graphics.Spritesheet2D
import org.springframework.util.ResourceUtils

class PlayerAttackRightAnimation : Animation2D(
    Spritesheet2D(
        ResourceUtils.getFile("classpath:assets/animations/player/attack-right.png"),
        100,
        100,
        1,
        30
    ),
    75,
    false
)