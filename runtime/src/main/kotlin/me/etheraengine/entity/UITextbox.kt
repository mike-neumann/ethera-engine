package me.etheraengine.entity

import me.etheraengine.entity.component.UIClickable
import me.etheraengine.entity.component.UIHoverable
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
    onHover: (it: Entity) -> Unit = {},
    offHover: (it: Entity) -> Unit = {},
    onClick: (it: Entity) -> Unit = {},
    offClick: (it: Entity) -> Unit = {},
) : UIInputElement<String>(
    x,
    y,
    width,
    height,
    text,
    textSize,
    textColor,
    textStyle,
    value
) {
    init {
        addComponents(
            UIHoverable(onHover, offHover),
            UIClickable(onClick, offClick)
        )
    }
}