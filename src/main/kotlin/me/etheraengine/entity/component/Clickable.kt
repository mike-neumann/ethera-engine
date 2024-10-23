package me.etheraengine.entity.component

import me.etheraengine.entity.Entity

/**
 * UI component, specifies that the attached entity is clickable
 */
open class Clickable(
    /**
     * Disclaimer: onClick triggers right after the user clicks on the entity,
     * this can cause some problems when switching scenes, as users may click new scene buttons before the scene is rendered.
     * therefore it is recommended to use offClick for button interactions
     */
    val onClick: (it: Entity) -> Unit = {},
    val offClick: (it: Entity) -> Unit = {}
) {
    var isClicked = false
}