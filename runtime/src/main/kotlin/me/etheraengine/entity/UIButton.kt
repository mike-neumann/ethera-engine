package me.etheraengine.entity

import me.etheraengine.entity.component.UIClickable
import me.etheraengine.entity.component.UIHoverable
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
    onHover: (it: Entity) -> Unit = {},
    offHover: (it: Entity) -> Unit = {},
    onClick: (it: Entity) -> Unit = {},
    offClick: (it: Entity) -> Unit = {},
) : UIElement(x, y, width, height, text, textSize, textColor, textStyle) {
    init {
        addComponents(
            UIHoverable(onHover, offHover),
            UIClickable(onClick, offClick)
        )
    }
}