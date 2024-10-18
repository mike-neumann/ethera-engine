package me.animaengine.example.entity.component

import me.animaengine.g2d.entity.component.Position2D

class Position(
    x: Float,
    y: Float,
    width: Int,
    height: Int,
    var direction: Direction = Direction.LEFT
) : Position2D(x, y, width, height)
