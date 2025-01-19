package me.etheraengine.example.entity.component

import me.etheraengine.g2d.entity.component.Position2D

class Position(
    x: kotlin.Double,
    y: kotlin.Double,
    var direction: Direction = Direction.LEFT,
) : Position2D(x, y)