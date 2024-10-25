package me.etheraengine.entity.component

import me.etheraengine.entity.Entity

/**
 * UI component, specifies that the attached entity is hoverable
 */
open class UIHoverable(
    val onHover: (it: Entity) -> Unit = {},
    val offHover: (it: Entity) -> Unit = {}
) {
    var isHovered = false
}