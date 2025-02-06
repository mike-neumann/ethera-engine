package me.etheraengine.runtime.system

import me.etheraengine.runtime.entity.component.UIFocusable
import me.etheraengine.runtime.scene.Scene
import org.springframework.stereotype.Component
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

/**
 * System responsible for managing the focused state of ui entities
 */
@Component
class UIFocusLogicSystem : LogicSystem, KeyListener {
    private var isTab = false
    private var isShift = false
    private var isUp = false
    private var isDown = false

    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        // handle focus switch
        val entities = scene.getEntities { it.hasComponent<UIFocusable>() }

        for (entity in entities) {
            val focusable = entity.getComponent<UIFocusable>()!!

            if (!focusable.isFocused) {
                continue
            }
            val index = scene.getEntities().indexOf(entity)

            if (isUp || (isTab && isShift)) {
                // search for previous focusable element
                val previous = scene.getEntities()
                    .reversed()
                    .firstOrNull { previous ->
                        scene.getEntities().indexOf(previous) < index && previous.hasComponent<UIFocusable>()
                    }
                    ?: continue
                val previousFocusable = previous.getComponent<UIFocusable>()!!

                focusable.offFocus(entity)
                focusable.isFocused = false
                previousFocusable.isFocused = true
                previousFocusable.onFocus(previous)
                isUp = false
                isTab = false
                isShift = false
            } else if (isTab || isDown) {
                // search for next focusable element
                val next = scene.getEntities()
                    .filterIndexed { nextIndex, next -> nextIndex > index && next.hasComponent<UIFocusable>() }
                    .firstOrNull()
                    ?: continue
                val nextFocusable = next.getComponent<UIFocusable>()!!

                focusable.offFocus(entity)
                focusable.isFocused = false
                nextFocusable.isFocused = true
                nextFocusable.onFocus(next)
                isTab = false
                isDown = false
            }
        }
        val focusedEntities = scene.getEntities {
            it.hasComponent<UIFocusable>() && it.getComponent<UIFocusable>()!!.isFocused
        }

        if (focusedEntities.isEmpty()) {
            // activate first non focused element
            val first = scene.getEntities {
                it.hasComponent<UIFocusable>()
            }.firstOrNull()
                ?: return
            val focusable = first.getComponent<UIFocusable>()!!

            if (isUp || isTab || isDown) {
                isUp = false
                isTab = false
                isDown = false
                focusable.isFocused = true
                focusable.onFocus(first)
            }
        }
    }

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_TAB -> isTab = true
            KeyEvent.VK_SHIFT -> isShift = true
            KeyEvent.VK_UP -> isUp = true
            KeyEvent.VK_DOWN -> isDown = true
        }
    }

    override fun keyReleased(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_TAB -> isTab = false
            KeyEvent.VK_SHIFT -> isShift = false
            KeyEvent.VK_UP -> isUp = false
            KeyEvent.VK_DOWN -> isDown = false
        }
    }

    override fun keyTyped(e: KeyEvent) {}
}