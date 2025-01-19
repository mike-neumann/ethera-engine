package me.etheraengine.entity

import me.etheraengine.entity.component.UIClickable
import me.etheraengine.entity.component.UIDraggable
import me.etheraengine.entity.component.UIHoverable
import me.etheraengine.entity.component.UIValue
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Position2D
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
    onHover: (it: Entity) -> Unit = {},
    offHover: (it: Entity) -> Unit = {},
    onFocus: (it: Entity) -> Unit = {},
    offFocus: (it: Entity) -> Unit = {},
    onClick: (it: Entity) -> Unit = {},
    offClick: (it: Entity) -> Unit = {},
    onDrag: (it: Entity, fromX: Double, fromY: Double, toX: Double, toY: Double) -> Unit = { _, _, _, _, _ -> },
    offDrag: (it: Entity, fromX: Double, fromY: Double, toX: Double, toY: Double) -> Unit = { _, _, _, _, _ -> },
    onChange: (it: Entity, oldValue: Double, newValue: Double) -> Unit = { _, _, _ -> },
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

        return when (valueX + pinWidth > position.x + dimensions.width) {
            true -> (position.x + dimensions.width) - pinWidth
            false -> valueX
        }
    }
}