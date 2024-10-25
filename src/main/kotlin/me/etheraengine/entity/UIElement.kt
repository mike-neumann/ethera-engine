package me.etheraengine.entity

import me.etheraengine.entity.component.UIFocusable
import me.etheraengine.entity.component.UIText
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
    textStyle: Int,
    onFocus: (it: Entity) -> Unit = {},
    offFocus: (it: Entity) -> Unit = {}
) : Entity() {
    init {
        addComponents(
            UIFocusable(onFocus, offFocus),
            Point2D.Double(x, y),
            Dimension(width, height),
            UIText(text, textSize, textColor, textStyle)
        )
    }
}