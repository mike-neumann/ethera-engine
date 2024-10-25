package me.etheraengine.entity

import java.awt.Color

/**
 * UI entity used for when displaying raw 2d text to the user
 */
open class UILabel(
    x: Double,
    y: Double,
    text: String,
    textSize: Float,
    textColor: Color,
    textStyle: Int,
    onFocus: (it: Entity) -> Unit = {},
    offFocus: (it: Entity) -> Unit = {}
) : UIElement(x, y, 0, 0, text, textSize, textColor, textStyle, onFocus, offFocus)