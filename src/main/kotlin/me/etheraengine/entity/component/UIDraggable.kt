package me.etheraengine.entity.component

import me.etheraengine.entity.Entity

/**
 * UI component, specifies that the attached entity is draggable from position A to B
 */
open class UIDraggable(
    val onDrag: (it: Entity, fromX: Double, fromY: Double, toX: Double, toY: Double) -> Unit = { _, _, _, _, _ -> },
    val offDrag: (it: Entity, fromX: Double, fromY: Double, toX: Double, toY: Double) -> Unit = { _, _, _, _, _ -> }
) {
    var isDragging = false
    var fromX = 0.0
    var fromY = 0.0
    var toX = 0.0
    var toY = 0.0
}