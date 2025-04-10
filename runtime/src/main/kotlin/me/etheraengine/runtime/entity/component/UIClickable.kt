package me.etheraengine.runtime.entity.component

import me.etheraengine.runtime.entity.Entity

typealias ClickEvent = (it: Entity) -> Unit

/**
 * UI component, specifies that the attached entity is clickable
 */
open class UIClickable(
    /**
     * Disclaimer: onClick triggers right after the user clicks on the entity,
     * this can cause some problems when switching scenes, as users may click new scene buttons before the scene is rendered.
     * therefore it is recommended to use offClick for button interactions
     */
    val onClick: ClickEvent = {},
    val offClick: ClickEvent = {},
) {
    var clicked = false
}