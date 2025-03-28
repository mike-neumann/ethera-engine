package me.etheraengine.runtime.system

import me.etheraengine.runtime.entity.Cursor
import me.etheraengine.runtime.entity.UIElement
import me.etheraengine.runtime.entity.component.*
import me.etheraengine.runtime.g2d.entity.component.Dimensions2D
import me.etheraengine.runtime.g2d.entity.component.Position2D
import me.etheraengine.runtime.g2d.util.CollisionUtils2D
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

    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val cursorPosition = cursor.getComponent<Position2D>()!!
        val uiElements = scene.getFilteredEntities { it is UIElement }

        for (uiElement in uiElements) {
            uiElement.getComponent<UIFocusable>()?.let {
                val mouseHovered = CollisionUtils2D.checkCollision(uiElement, cursor)
                val mouseInteracted = mouseHovered && leftMouseDown
                val focused = it.isFocused
                val keyboardInteracted = focused && (spaceDown || enterDown)
                // handle hover event is uiElement is hoverable
                uiElement.getComponent<UIHoverable>()?.let {
                    if ((mouseHovered || focused) && !it.isHovered && !leftMouseDown) {
                        hovering = true
                        it.isHovered = true
                        it.onHover(uiElement)
                    } else if ((!mouseHovered && !focused) && it.isHovered && !leftMouseDown) {
                        hovering = false
                        it.isHovered = false
                        it.offHover(uiElement)
                    }
                }
                // handle click event if uiElement is clickable
                uiElement.getComponent<UIClickable>()?.let {
                    if ((mouseInteracted || keyboardInteracted) && !it.isClicked && !clicking) {
                        clicking = true
                        it.isClicked = true
                        it.onClick(uiElement)
                    } else if ((!leftMouseDown && !keyboardInteracted) && it.isClicked && clicking) {
                        clicking = false
                        it.isClicked = false
                        it.offClick(uiElement)
                    }
                }
                // handle drag event if uiElement is draggable
                uiElement.getComponent<UIDraggable>()?.let {
                    if (mouseInteracted && !it.isDragging && !dragging) {
                        dragging = true
                        it.fromX = cursorPosition.x
                        it.fromY = cursorPosition.y
                        it.toX = cursorPosition.x
                        it.toY = cursorPosition.y
                        it.isDragging = true
                        it.onDrag(uiElement, it.fromX, it.fromY, it.toX, it.toY)
                    } else if (it.isDragging && dragging) {
                        it.toX = cursorPosition.x
                        it.toY = cursorPosition.y
                        it.isDragging = true
                        it.onDrag(uiElement, it.fromX, it.fromY, it.toX, it.toY)
                    }

                    if (!leftMouseDown && it.isDragging && dragging) {
                        dragging = false
                        it.isDragging = false
                        it.offDrag(uiElement, it.fromX, it.fromY, it.toX, it.toY)
                    }
                }
            }

            uiElement.getComponent<UIValue<Any>>()?.let {
                if (it.value != it.lastValue) {
                    it.lastValue = it.value
                    it.onChange(uiElement, it.lastValue, it.value)
                }
            }
        }
    }

    private fun updateCursorPosition(x: Double, y: Double) {
        val position = cursor.getComponent<Position2D>()!!
        val dimensions = cursor.getComponent<Dimensions2D>()!!

        position.setLocation(x - 9, y - 30 - (dimensions.height / 2))
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