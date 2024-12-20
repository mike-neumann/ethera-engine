package me.etheraengine.entity

import me.etheraengine.entity.component.UIValue
import java.awt.Color

open class UIInputElement<T>(
    x: Double,
    y: Double,
    width: Int,
    height: Int,
    text: String,
    textSize: Float,
    textColor: Color,
    textStyle: Int,
    value: T,
    maxValue: T = value,
    onFocus: (it: Entity) -> Unit = {},
    offFocus: (it: Entity) -> Unit = {},
    onChange: (it: Entity, oldValue: T, newValue: T) -> Unit = { _, _, _ -> }
) : UIElement(x, y, width, height, text, textSize, textColor, textStyle, onFocus, offFocus) {
    init {
        addComponents(
            UIValue(value, maxValue, onChange)
        )
    }
}