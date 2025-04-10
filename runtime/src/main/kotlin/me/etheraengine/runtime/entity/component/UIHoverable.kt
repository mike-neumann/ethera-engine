package me.etheraengine.runtime.entity.component

import me.etheraengine.runtime.entity.Entity

typealias HoverEvent = (it: Entity) -> Unit

/**
 * UI component, specifies that the attached entity is hoverable
 */
open class UIHoverable(val onHover: HoverEvent = {}, val offHover: HoverEvent = {}) {
    var hovered = false
}