package me.etheraengine.runtime.entity

import me.etheraengine.runtime.entity.component.*
import java.awt.Color

open class UISlider(
    x: Double,
    y: Double,
    width: Int,
    height: Int,
    text: String,
    textSize: Float,
    textColor: Color,
    textStyle: Int,
    value: Double,
    maxValue: Double = value,
    onHover: HoverEvent = {},
    offHover: HoverEvent = {},
    onFocus: FocusEvent = {},
    offFocus: FocusEvent = {},
    onClick: ClickEvent = {},
    offClick: ClickEvent = {},
    onDrag: DragEvent = { _, _, _, _, _ -> },
    offDrag: DragEvent = { _, _, _, _, _ -> },
    onChange: ChangeEvent<Double> = { _, _, _ -> },
) : UIInputElement<Double>(
    x,
    y,
    width,
    height,
    text,
    textSize,
    textColor,
    textStyle,
    value,
    maxValue,
    onFocus,
    offFocus,
    onChange
) {
    init {
        // add components needed for all sliders
        addComponents(UIHoverable(onHover, offHover), UIClickable(onClick, offClick), UIDraggable(onDrag, offDrag))
    }

    /**
     * Gets the sliders draggable pin x position by the current value its holding
     */
    fun getPinXPositionForCurrentValue(pinWidth: Double) = let {
        val value = getComponent<UIValue<Double>>()!!
        val valueX = x + value.value / 100 * value.maxValue / 100 * width
        if (valueX + pinWidth > x + width) (x + width) - pinWidth else valueX
    }
}