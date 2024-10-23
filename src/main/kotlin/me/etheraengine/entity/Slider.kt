package me.etheraengine.entity

import me.etheraengine.entity.component.Value
import java.awt.Color
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

open class Slider(
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
    maxValue
) {
    fun getPinXPositionForCurrentValue(): Double {
        val point = getComponent<Point2D>()!!
        val dimension = getComponent<Dimension2D>()!!
        val value = getComponent<Value<Double>>()!!

        return point.x + value.value / 100 * value.maxValue / 100 * dimension.width
    }
}