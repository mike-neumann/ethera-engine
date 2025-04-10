package me.etheraengine.runtime.system

import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.entity.UISlider
import me.etheraengine.runtime.entity.component.*
import me.etheraengine.runtime.scene.Scene
import org.springframework.stereotype.Component
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

/**
 * Logic system handling ui slider value control
 */
@Component
class UISliderValueLogicSystem : KeyAdapter(), LogicSystem {
    private var leftKeyDown = false
    private var rightKeyDown = false

    override fun update(entity: Entity, scene: Scene, now: Long, deltaTime: Long) {
        if (entity !is UISlider || !entity.hasComponent<UIDraggable>() || !entity.hasComponent<UIValueHolder<Double>>()) return
        val draggable = entity.getComponent<UIDraggable>()!!
        val valueHolder = entity.getComponent<UIValueHolder<Double>>()!!

        if (draggable.dragging) {
            val draggedDistance = draggable.toX - entity.x
            val newValue = Math.clamp((draggedDistance / entity.width * 100) / 100 * valueHolder.maxValue, 0.0, valueHolder.maxValue)
            val oldValue = valueHolder.value

            valueHolder.value = newValue
            valueHolder.onChange(entity, oldValue, newValue)
        }
        val focusable = entity.getComponent<UIFocusable>()!!

        if (focusable.focused && leftKeyDown && !rightKeyDown) {
            valueHolder.value = Math.clamp(valueHolder.value - 1, 0.0, valueHolder.maxValue)
        } else if (focusable.focused && rightKeyDown && !leftKeyDown) {
            valueHolder.value = Math.clamp(valueHolder.value + 1, 0.0, valueHolder.maxValue)
        }
    }

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_LEFT -> leftKeyDown = true
            KeyEvent.VK_RIGHT -> rightKeyDown = true
        }
    }

    override fun keyReleased(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_LEFT -> leftKeyDown = false
            KeyEvent.VK_RIGHT -> rightKeyDown = false
        }
    }
}