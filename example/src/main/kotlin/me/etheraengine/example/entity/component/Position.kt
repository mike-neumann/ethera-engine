package me.etheraengine.example.entity.component

import java.awt.geom.Point2D


class Position(
    x: kotlin.Double,
    y: kotlin.Double,
    var direction: Direction = Direction.LEFT
) : Point2D.Double(x, y)
