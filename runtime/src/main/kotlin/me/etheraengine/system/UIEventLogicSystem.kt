package me.etheraengine.system

import me.etheraengine.entity.Cursor
import me.etheraengine.entity.UIElement
import me.etheraengine.entity.component.*
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.g2d.util.CollisionUtils2D
import me.etheraengine.scene.Scene
import org.springframework.stereotype.Component
import java.awt.event.*

/**
 * System handling ui entity element logic like: hover and click events
 * Also responsible for updating the users cursor position via ECS pattern
 */
@Component
class UIEventLogicSystem(
    val cursor: Cursor,
) : LogicSystem, MouseListener, MouseMotionListener, KeyListener {
    private var isLeftMouse = false
    private var isSpace = false
    private var isEnter = false

    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val cursorPosition = cursor.getComponent<Position2D>()!!

        scene.getEntities {
            it is UIElement
        }.forEach { element ->
            element.getComponent<UIFocusable>()?.let {
                val isMouseHovered = CollisionUtils2D.checkCollision(element, cursor)
                val isMouseInteracted = isMouseHovered && isLeftMouse
                val isKeyboardHovered = it.isFocused
                val isKeyboardInteracted = isKeyboardHovered && (isSpace || isEnter)

                // handle hover event is element is hoverable
                element.getComponent<UIHoverable>()?.let {
                    if ((isMouseHovered || isKeyboardHovered) && !it.isHovered) {
                        it.isHovered = true
                        it.onHover(element)
                    } else if ((!isMouseHovered && !isKeyboardHovered) && it.isHovered) {
                        it.isHovered = false
                        it.offHover(element)
                    }
                }

                // handle click event if element is clickable
                element.getComponent<UIClickable>()?.let {
                    if ((isMouseInteracted || isKeyboardInteracted) && !it.isClicked) {
                        it.isClicked = true
                        it.onClick(element)
                    } else if ((!isMouseInteracted && !isKeyboardInteracted) && it.isClicked) {
                        it.isClicked = false
                        it.offClick(element)
                    }
                }

                // handle drag event if element is draggable
                element.getComponent<UIDraggable>()?.let {
                    if (isMouseInteracted && !it.isDragging) {
                        it.fromX = cursorPosition.x
                        it.fromY = cursorPosition.y
                        it.toX = cursorPosition.x
                        it.toY = cursorPosition.y
                        it.isDragging = true
                        it.onDrag(element, it.fromX, it.fromY, it.toX, it.toY)
                    }

                    if (isMouseInteracted && it.isDragging) {
                        it.toX = cursorPosition.x
                        it.toY = cursorPosition.y
                        it.isDragging = true
                        it.onDrag(element, it.fromX, it.fromY, it.toX, it.toY)
                    }

                    if (!isMouseInteracted && it.isDragging) {
                        it.isDragging = false
                        it.offDrag(element, it.fromX, it.fromY, it.toX, it.toY)
                    }
                }
            }

            element.getComponent<UIValue<Any>>()?.let {
                if (it.value != it.lastValue) {
                    it.lastValue = it.value
                    it.onChange(element, it.lastValue, it.value)
                }
            }
        }
    }

    private fun updateCursorPosition(x: Double, y: Double) {
        val position = cursor.getComponent<Position2D>()!!
        val dimensions = cursor.getComponent<Dimensions2D>()!!

        position.setLocation(
            x - 9,
            y - 30 - (dimensions.height / 2)
        )
    }

    override fun mouseDragged(e: MouseEvent) {
        updateCursorPosition(e.x.toDouble(), e.y.toDouble())
    }

    override fun mouseMoved(e: MouseEvent) {
        updateCursorPosition(e.x.toDouble(), e.y.toDouble())
    }

    override fun mousePressed(e: MouseEvent) {
        when (e.button) {
            MouseEvent.BUTTON1 -> isLeftMouse = true
        }
    }

    override fun mouseReleased(e: MouseEvent) {
        when (e.button) {
            MouseEvent.BUTTON1 -> isLeftMouse = false
        }
    }

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_SPACE -> isSpace = true
            KeyEvent.VK_ENTER -> isEnter = true
        }
    }

    override fun keyReleased(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_SPACE -> isSpace = false
            KeyEvent.VK_ENTER -> isEnter = false
        }
    }

    override fun mouseClicked(e: MouseEvent) {}
    override fun mouseEntered(e: MouseEvent) {}
    override fun mouseExited(e: MouseEvent) {}
    override fun keyTyped(e: KeyEvent) {}
}