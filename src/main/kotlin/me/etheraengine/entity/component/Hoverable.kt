package me.etheraengine.entity.component

import me.etheraengine.entity.Entity

/**
 * UI component, speciifes that the attached entitiy is hoverable
 */
open class Hoverable(
    val onHover: (it: Entity) -> Unit = {},
    val offHover: (it: Entity) -> Unit = {}
) {
    var isHovered = false
}