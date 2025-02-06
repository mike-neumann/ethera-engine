package me.etheraengine.runtime.entity.component

import java.awt.Color

/**
 * UI component, specifies that the attached entity displays text
 */
open class UIText(val text: String, val size: Float, val color: Color, val style: Int)