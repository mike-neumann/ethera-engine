package me.etheraengine.runtime.entity

import me.etheraengine.runtime.entity.component.*
import me.etheraengine.runtime.g2d.entity.component.Dimensions2D
import me.etheraengine.runtime.g2d.entity.component.Position2D
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
        addComponents(
            UIHoverable(onHover, offHover),
            UIClickable(onClick, offClick),
            UIDraggable(onDrag, offDrag)
        )
    }

    /**
     * Gets the sliders draggable pin x position by the current value its holding
     */
    fun getPinXPositionForCurrentValue(pinWidth: Double): Double {
        val position = getComponent<Position2D>()!!
        val dimensions = getComponent<Dimensions2D>()!!
        val value = getComponent<UIValue<Double>>()!!
        val valueX = position.x + value.value / 100 * value.maxValue / 100 * dimensions.width
        return if (valueX + pinWidth > position.x + dimensions.width) (position.x + dimensions.width) - pinWidth else valueX
    }
}