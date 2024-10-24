package me.etheraengine.entity

import me.etheraengine.entity.component.Value
import java.awt.Color

open class UIInputElement<T : Number>(
    x: Double,
    y: Double,
    width: Int,
    height: Int,
    text: String,
    textSize: Float,
    textColor: Color,
    textStyle: Int,
    value: T,
    maxValue: T = value
) : UIElement(
    x,
    y,
    width,
    height,
    text,
    textSize,
    textColor,
    textStyle
) {
    init {
        addComponents(
            Value(value, maxValue)
        )
    }
}