package me.etheraengine.runtime.entity

import me.etheraengine.runtime.entity.component.*
import me.etheraengine.runtime.g2d.entity.component.Dimensions2D
import me.etheraengine.runtime.g2d.entity.component.Position2D
import java.awt.Color

open class UIElement(
    x: Double,
    y: Double,
    width: Int,
    height: Int,
    text: String,
    textSize: Float,
    textColor: Color,
    textStyle: Int,
    onFocus: FocusEvent = {},
    offFocus: FocusEvent = {},
) : Entity() {
    init {
        addComponents(
            UIFocusable(onFocus, offFocus),
            Position2D(x, y),
            Dimensions2D(width, height),
            UIText(text, textSize, textColor, textStyle)
        )
    }
}