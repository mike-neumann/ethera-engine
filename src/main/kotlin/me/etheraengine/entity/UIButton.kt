package me.etheraengine.entity

import java.awt.Color

/**
 * UI entity used for when catching user click input
 */
open class UIButton(
    x: Double,
    y: Double,
    width: Int,
    height: Int,
    text: String,
    textSize: Float,
    textColor: Color,
    textStyle: Int,
    onFocus: (it: Entity) -> Unit = {},
    offFocus: (it: Entity) -> Unit = {}
) : UIElement(x, y, width, height, text, textSize, textColor, textStyle, onFocus, offFocus)