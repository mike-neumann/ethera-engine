package me.etheraengine.runtime.entity

import me.etheraengine.runtime.entity.component.*
import java.awt.Color

/**
 * UI entity used for when catching user click input
 */
open class UIButton(
    x: Double,
    y: Double,
    width: Int,
    height: Int,
    text: String,
    textSize: Float,
    textColor: Color,
    textStyle: Int,
    onHover: HoverEvent = {},
    offHover: HoverEvent = {},
    onClick: ClickEvent = {},
    offClick: ClickEvent = {},
) : UIElement(x, y, width, height, text, textSize, textColor, textStyle) {
    init {
        addComponents(UIHoverable(onHover, offHover), UIClickable(onClick, offClick))
    }
}