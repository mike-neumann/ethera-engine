package me.etheraengine.entity

import me.etheraengine.entity.component.Text
import java.awt.Color
import java.awt.Dimension
import java.awt.geom.Point2D

open class UIElement(
    x: Double,
    y: Double,
    width: Int,
    height: Int,
    text: String,
    textSize: Float,
    textColor: Color,
    textStyle: Int
) : Entity() {
    init {
        addComponents(
            Point2D.Double(x, y),
            Dimension(width, height),
            Text(text, textSize, textColor, textStyle)
        )
    }
}