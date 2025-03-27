package me.etheraengine.runtime.entity

import me.etheraengine.runtime.entity.component.*
import java.awt.Color

class UICheckbox(
    x: Double,
    y: Double,
    width: Int,
    height: Int,
    text: String,
    textSize: Float,
    textColor: Color,
    textStyle: Int,
    value: Boolean,
    onFocus: FocusEvent = {},
    offFocus: FocusEvent = {},
    onHover: HoverEvent = {},
    offHover: HoverEvent = {},
    onChange: ChangeEvent<Boolean> = { _, _, _ -> },
) : UIInputElement<Boolean>(
    x,
    y,
    width,
    height,
    text,
    textSize,
    textColor,
    textStyle,
    value,
    onFocus = onFocus,
    offFocus = offFocus,
    onChange = onChange
) {
    init {
        addComponents(UIHoverable(onHover, offHover), UIClickable({ it.getComponent<UIValue<Boolean>>()!!.apply { this.value = !value } }))
    }
}