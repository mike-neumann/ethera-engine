package me.etheraengine.runtime.entity

import me.etheraengine.runtime.entity.component.*
import java.awt.Color

class UITextbox(
    x: Double,
    y: Double,
    width: Int,
    height: Int,
    text: String,
    textSize: Float,
    textColor: Color,
    textStyle: Int,
    value: String,
    onHover: HoverEvent = {},
    offHover: HoverEvent = {},
    onClick: ClickEvent = {},
    offClick: ClickEvent = {},
) : UIInputElement<String>(x, y, width, height, text, textSize, textColor, textStyle, value) {
    init {
        addComponents(UIHoverable(onHover, offHover), UIClickable(onClick, offClick))
    }
}