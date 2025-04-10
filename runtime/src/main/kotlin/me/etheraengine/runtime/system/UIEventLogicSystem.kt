package me.etheraengine.runtime.system

import me.etheraengine.runtime.entity.*
import me.etheraengine.runtime.entity.component.*
import me.etheraengine.runtime.g2d.util.CollisionUtils2D.collidesWith
import me.etheraengine.runtime.scene.Scene
import org.springframework.stereotype.Component
import java.awt.event.*

/**
 * System handling ui entity element logic like: hover and click events
 * Also responsible for updating the users cursor position via ECS pattern
 */
@Component
class UIEventLogicSystem(val cursor: Cursor) : LogicSystem, MouseListener, MouseMotionListener, KeyListener {
    private var leftMouseDown = false
    private var spaceDown = false
    private var enterDown = false
    private var hovering = false
    private var clicking = false
    private var dragging = false

    override fun update(entity: Entity, scene: Scene, now: Long, deltaTime: Long) {
        if (entity !is UIElement) return

        entity.getComponent<UIFocusable>()?.let {
            val mouseHovered = entity collidesWith cursor
            val mouseInteracted = mouseHovered && leftMouseDown
            val focused = it.focused
            val keyboardInteracted = focused && (spaceDown || enterDown)
            // handle hover event is uiElement is hoverable
            entity.getComponent<UIHoverable>()?.let {
                if ((mouseHovered || focused) && !it.hovered && !leftMouseDown) {
                    hovering = true
                    it.hovered = true
                    it.onHover(entity)
                } else if ((!mouseHovered && !focused) && it.hovered && !leftMouseDown) {
                    hovering = false
                    it.hovered = false
                    it.offHover(entity)
                }
            }
            // handle click event if uiElement is clickable
            entity.getComponent<UIClickable>()?.let {
                if ((mouseInteracted || keyboardInteracted) && !it.clicked && !clicking) {
                    clicking = true
                    it.clicked = true
                    it.onClick(entity)
                } else if ((!leftMouseDown && !keyboardInteracted) && it.clicked && clicking) {
                    clicking = false
                    it.clicked = false
                    it.offClick(entity)
                }
            }
            // handle drag event if uiElement is draggable
            entity.getComponent<UIDraggable>()?.let {
                if (mouseInteracted && !it.dragging && !dragging) {
                    dragging = true
                    it.fromX = cursor.x
                    it.fromY = cursor.y
                    it.toX = cursor.x
                    it.toY = cursor.y
                    it.dragging = true
                    it.onDrag(entity, it.fromX, it.fromY, it.toX, it.toY)
                } else if (it.dragging && dragging) {
                    it.toX = cursor.x
                    it.toY = cursor.y
                    it.dragging = true
                    it.onDrag(entity, it.fromX, it.fromY, it.toX, it.toY)
                }

                if (!leftMouseDown && it.dragging && dragging) {
                    dragging = false
                    it.dragging = false
                    it.offDrag(entity, it.fromX, it.fromY, it.toX, it.toY)
                }
            }
        }

        entity.getComponent<UIValueHolder<Any>>()?.run {
            if (value != lastValue) {
                lastValue = value
                onChange(entity, lastValue, value)
            }
        }
    }

    private fun updateCursorPosition(x: Double, y: Double) {
        cursor.x = x - 9
        cursor.y = y - 30 - (cursor.height / 2)
    }

    override fun mouseDragged(e: MouseEvent) = updateCursorPosition(e.x.toDouble(), e.y.toDouble())
    override fun mouseMoved(e: MouseEvent) = updateCursorPosition(e.x.toDouble(), e.y.toDouble())

    override fun mousePressed(e: MouseEvent) {
        when (e.button) {
            MouseEvent.BUTTON1 -> leftMouseDown = true
        }
    }

    override fun mouseReleased(e: MouseEvent) {
        when (e.button) {
            MouseEvent.BUTTON1 -> leftMouseDown = false
        }
    }

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_SPACE -> spaceDown = true
            KeyEvent.VK_ENTER -> enterDown = true
        }
    }

    override fun keyReleased(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_SPACE -> spaceDown = false
            KeyEvent.VK_ENTER -> enterDown = false
        }
    }

    override fun mouseClicked(e: MouseEvent) {}
    override fun mouseEntered(e: MouseEvent) {}
    override fun mouseExited(e: MouseEvent) {}
    override fun keyTyped(e: KeyEvent) {}
}