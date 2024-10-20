package me.etheraengine.example.graphics.entity

import me.etheraengine.g2d.graphics.Animation2D
import me.etheraengine.g2d.graphics.Spritesheet2D
import org.springframework.util.ResourceUtils

class EntityDespawnAnimation : Animation2D(
    Spritesheet2D(
        ResourceUtils.getFile("classpath:assets/animations/despawn.png"),
        128,
        128,
        1,
        0
    ),
    100,
    false
)