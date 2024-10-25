package me.etheraengine.entity

import me.etheraengine.entity.component.UIValue
import java.awt.Color
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

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
    onFocus: (it: Entity) -> Unit = {},
    offFocus: (it: Entity) -> Unit = {}
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
    offFocus
) {
    /**
     * Gets the sliders draggable pin x position by the current value its holding
     */
    fun getPinXPositionForCurrentValue(pinWidth: Double): Double {
        val point = getComponent<Point2D>()!!
        val dimension = getComponent<Dimension2D>()!!
        val value = getComponent<UIValue<Double>>()!!
        val valueX = point.x + value.value / 100 * value.maxValue / 100 * dimension.width

        return if (valueX + pinWidth > point.x + dimension.width) {
            (point.x + dimension.width) - pinWidth
        } else {
            valueX
        }
    }
}