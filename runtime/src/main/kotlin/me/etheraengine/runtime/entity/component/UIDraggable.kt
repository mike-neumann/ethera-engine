package me.etheraengine.runtime.entity.component

import me.etheraengine.runtime.entity.Entity

typealias DragEvent = (it: Entity, fromX: Double, fromY: Double, toX: Double, toY: Double) -> Unit

/**
 * UI component, specifies that the attached entity is draggable from position A to B
 */
open class UIDraggable(val onDrag: DragEvent = { _, _, _, _, _ -> }, val offDrag: DragEvent = { _, _, _, _, _ -> }) {
    var dragging = false
    var fromX = 0.0
    var fromY = 0.0
    var toX = 0.0
    var toY = 0.0
}