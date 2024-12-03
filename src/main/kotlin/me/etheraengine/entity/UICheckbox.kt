package me.etheraengine.entity

import me.etheraengine.entity.component.UIClickable
import me.etheraengine.entity.component.UIHoverable
import me.etheraengine.entity.component.UIValue
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
    onFocus: (it: Entity) -> Unit = {},
    offFocus: (it: Entity) -> Unit = {},
    onHover: (it: Entity) -> Unit = {},
    offHover: (it: Entity) -> Unit = {},
    onChange: (it: Entity, oldValue: Boolean, newValue: Boolean) -> Unit = { _, _, _ -> }
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
        addComponents(
            UIHoverable(onHover, offHover),
            UIClickable({
                val value = it.getComponent<UIValue<Boolean>>()!!

                value.value = !value.value
            }, {})
        )
    }
}