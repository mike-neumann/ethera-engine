package me.etheraengine.system

import me.etheraengine.entity.Entity
import me.etheraengine.entity.component.UIFocusable
import me.etheraengine.scene.Scene
import org.springframework.stereotype.Component
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * System responsible for managing the focused state of ui entities
 */
@Component
class UIFocusSystem : LogicSystem, KeyListener {
    private var isTab = false
    private var isShift = false
    private var isUp = false
    private var isDown = false

    override fun update(scene: Scene, entities: ConcurrentLinkedQueue<Entity>, now: Long, deltaTime: Long) {
        // handle focus switch
        entities
            .filter { it.hasComponent<UIFocusable>() }
            .forEach {
                val focusable = it.getComponent<UIFocusable>()!!

                if (!focusable.isFocused) {
                    return@forEach
                }

                val index = entities.indexOf(it)

                if (isUp || (isTab && isShift)) {
                    // search for previous focusable element
                    val previous = entities
                        .toList()
                        .reversed()
                        .firstOrNull { previous -> entities.indexOf(previous) < index && previous.hasComponent<UIFocusable>() }
                        ?: return@forEach
                    val previousFocusable = previous.getComponent<UIFocusable>()!!

                    focusable.offFocus(it)
                    focusable.isFocused = false
                    previousFocusable.isFocused = true
                    previousFocusable.onFocus(previous)
                    isUp = false
                    isTab = false
                    isShift = false
                } else if (isTab || isDown) {
                    // search for next focusable element
                    val next = entities
                        .filterIndexed { nextIndex, next -> nextIndex > index && next.hasComponent<UIFocusable>() }
                        .firstOrNull()
                        ?: return@forEach
                    val nextFocusable = next.getComponent<UIFocusable>()!!

                    focusable.offFocus(it)
                    focusable.isFocused = false
                    nextFocusable.isFocused = true
                    nextFocusable.onFocus(next)
                    isTab = false
                    isDown = false
                }
            }

        val focusedEntities = entities
            .filter { it.hasComponent<UIFocusable>() }
            .filter { it.getComponent<UIFocusable>()!!.isFocused }

        if (focusedEntities.toList().isEmpty()) {
            // activate first non focused element
            val first = entities
                .firstOrNull { it.hasComponent<UIFocusable>() }
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