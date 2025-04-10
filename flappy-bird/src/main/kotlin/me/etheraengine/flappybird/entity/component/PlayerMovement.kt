package me.etheraengine.flappybird.entity.component

import me.etheraengine.runtime.g2d.entity.component.Movement2D

class PlayerMovement(speed: Double, tvx: Double = 1.0, tvy: Double = 1.0, var jumping: Boolean = false) : Movement2D(speed, tvx, tvy)