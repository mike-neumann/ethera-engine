package me.etheraengine.runtime.system

import me.etheraengine.runtime.entity.component.UIFocusable
import me.etheraengine.runtime.scene.Scene
import org.springframework.stereotype.Component
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

/**
 * System responsible for managing the focused state of ui entities
 */
@Component
class UIFocusLogicSystem : KeyAdapter(), LogicSystem {
    private var isTab = false
    private var isShift = false
    private var isUp = false
    private var isDown = false

    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        // handle focus switch
        val entities = scene.getFilteredEntities { it.hasComponent<UIFocusable>() }

        for (entity in entities) {
            val focusable = entity.getComponent<UIFocusable>()!!

            if (!focusable.focused) continue
            val index = scene.getFilteredEntities().indexOf(entity)

            if (isUp || (isTab && isShift)) {
                // search for previous focusable element
                val previous = scene.getFilteredEntities()
                    .reversed()
                    .firstOrNull { scene.getFilteredEntities().indexOf(it) < index && it.hasComponent<UIFocusable>() }
                    ?: continue
                val previousFocusable = previous.getComponent<UIFocusable>()!!

                focusable.offFocus(entity)
                focusable.focused = false
                previousFocusable.focused = true
                previousFocusable.onFocus(previous)
                isUp = false
                isTab = false
                isShift = false
            } else if (isTab || isDown) {
                // search for next focusable element
                val next = scene.getFilteredEntities()
                    .filterIndexed { nextIndex, next -> nextIndex > index && next.hasComponent<UIFocusable>() }
                    .firstOrNull()
                    ?: continue
                val nextFocusable = next.getComponent<UIFocusable>()!!

                focusable.offFocus(entity)
                focusable.focused = false
                nextFocusable.focused = true
                nextFocusable.onFocus(next)
                isTab = false
                isDown = false
            }
        }
        val focusedEntities = scene.getFilteredEntities { it.hasComponent<UIFocusable>() && it.getComponent<UIFocusable>()!!.focused }

        if (focusedEntities.isEmpty()) {
            // activate first non focused element
            val first = scene.getFilteredEntities { it.hasComponent<UIFocusable>() }.firstOrNull() ?: return
            val focusable = first.getComponent<UIFocusable>()!!

            if (isUp || isTab || isDown) {
                isUp = false
                isTab = false
                isDown = false
                focusable.focused = true
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
}