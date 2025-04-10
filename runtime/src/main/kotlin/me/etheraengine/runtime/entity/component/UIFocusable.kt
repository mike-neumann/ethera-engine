package me.etheraengine.runtime.entity.component

import me.etheraengine.runtime.entity.Entity

typealias FocusEvent = (it: Entity) -> Unit

/**
 * UI component, specifies that the attached entity is focusable
 */
open class UIFocusable(var onFocus: FocusEvent = {}, var offFocus: FocusEvent = {}) {
    var focused = false
}