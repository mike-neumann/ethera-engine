package me.etheraengine.entity

import me.etheraengine.entity.component.UIFocusable
import me.etheraengine.entity.component.UIText
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Position2D
import java.awt.Color

open class UIElement(
    x: Double,
    y: Double,
    width: Int,
    height: Int,
    text: String,
    textSize: Float,
    textColor: Color,
    textStyle: Int,
    onFocus: (it: Entity) -> Unit = {},
    offFocus: (it: Entity) -> Unit = {},
) : Entity() {
    init {
        addComponents(
            UIFocusable(onFocus, offFocus),
            Position2D(x, y),
            Dimensions2D(width, height),
            UIText(text, textSize, textColor, textStyle)
        )
    }
}