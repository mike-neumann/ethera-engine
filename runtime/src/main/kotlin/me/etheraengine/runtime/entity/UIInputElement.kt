package me.etheraengine.runtime.entity

import me.etheraengine.runtime.entity.component.*
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
    onFocus: FocusEvent = {},
    offFocus: FocusEvent = {},
    onChange: ChangeEvent<T> = { _, _, _ -> },
) : UIElement(x, y, width, height, text, textSize, textColor, textStyle, onFocus, offFocus) {
    init {
        addComponents(UIValue(value, maxValue, onChange))
    }
}