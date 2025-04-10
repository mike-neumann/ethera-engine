package me.etheraengine.runtime.entity

import me.etheraengine.runtime.entity.component.*
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
) : Entity(x, y, width, height) {
    init {
        addComponents(UIFocusable(onFocus, offFocus), UIText(text, textSize, textColor, textStyle))
    }
}