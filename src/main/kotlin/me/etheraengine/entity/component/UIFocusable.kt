package me.etheraengine.entity.component

import me.etheraengine.entity.Entity

/**
 * UI component, specifies that the attached entity is focusable
 */
open class UIFocusable(
    var onFocus: (it: Entity) -> Unit = {},
    var offFocus: (it: Entity) -> Unit = {}
) {
    var isFocused = false
}