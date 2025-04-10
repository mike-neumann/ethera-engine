package me.etheraengine.runtime.system

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

    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val sliders = scene.getFilteredEntities { it is UISlider && it.hasComponent<UIDraggable>() && it.hasComponent<UIValue<Double>>() }

        for (slider in sliders) {
            val draggable = slider.getComponent<UIDraggable>()!!
            val value = slider.getComponent<UIValue<Double>>()!!

            if (draggable.isDragging) {
                val draggedDistance = draggable.toX - slider.x
                val newValue = Math.clamp((draggedDistance / slider.width * 100) / 100 * value.maxValue, 0.0, value.maxValue)
                val oldValue = value.value

                value.value = newValue
                value.onChange(slider, oldValue, newValue)
            }
            val focusable = slider.getComponent<UIFocusable>()!!

            if (focusable.isFocused && leftKeyDown && !rightKeyDown) {
                value.value = Math.clamp(value.value - 1, 0.0, value.maxValue)
            } else if (focusable.isFocused && rightKeyDown && !leftKeyDown) {
                value.value = Math.clamp(value.value + 1, 0.0, value.maxValue)
            }
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