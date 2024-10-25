package me.etheraengine.system

import me.etheraengine.entity.Cursor
import me.etheraengine.entity.Entity
import me.etheraengine.entity.UIElement
import me.etheraengine.entity.component.UIClickable
import me.etheraengine.entity.component.UIDraggable
import me.etheraengine.entity.component.UIFocusable
import me.etheraengine.entity.component.UIHoverable
import me.etheraengine.g2d.util.CollisionUtils2D
import me.etheraengine.scene.Scene
import org.springframework.stereotype.Component
import java.awt.event.*
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

/**
 * System handling ui entity element logic like: hover and click events
 * Also responsible for updating the users cursor position via ECS pattern
 */
@Component
class UIEventSystem(
    private val cursor: Cursor
) : LogicSystem, MouseListener, MouseMotionListener, KeyListener {
    private var isLeftMouse = false
    private var isSpace = false
    private var isEnter = false

    override fun update(scene: Scene, entities: List<Entity>, now: Long, deltaTime: Long) {
        val cursorPosition = cursor.getComponent<Point2D>()!!

        entities
            .filterIsInstance<UIElement>()
            .forEach {
                val focusable = it.getComponent<UIFocusable>()!!
                val isMouseHovered = CollisionUtils2D.checkCollision(it, cursor)
                val isMouseInteracted = isMouseHovered && isLeftMouse
                val isKeyboardHovered = focusable.isFocused
                val isKeyboardInteracted = isKeyboardHovered && (isSpace || isEnter)

                if (it.hasComponent<UIHoverable>()) {
                    val hoverable = it.getComponent<UIHoverable>()!!

                    if ((isMouseHovered || isKeyboardHovered) && !hoverable.isHovered) {
                        hoverable.isHovered = true
                        hoverable.onHover(it)
                    } else if ((!isMouseHovered && !isKeyboardHovered) && hoverable.isHovered) {
                        hoverable.isHovered = false
                        hoverable.offHover(it)
                    }
                }

                if (it.hasComponent<UIClickable>()) {
                    val clickable = it.getComponent<UIClickable>()!!

                    if ((isMouseInteracted || isKeyboardInteracted) && !clickable.isClicked) {
                        clickable.isClicked = true
                        clickable.onClick(it)
                    } else if ((!isMouseInteracted && !isKeyboardInteracted) && clickable.isClicked) {
                        clickable.isClicked = false
                        clickable.offClick(it)
                    }
                }

                if (it.hasComponent<UIDraggable>()) {
                    val draggable = it.getComponent<UIDraggable>()!!

                    if (isMouseInteracted && !draggable.isDragging) {
                        draggable.fromX = cursorPosition.x
                        draggable.fromY = cursorPosition.y
                        draggable.toX = cursorPosition.x
                        draggable.toY = cursorPosition.y
                        draggable.isDragging = true
                        draggable.onDrag(it, draggable.fromX, draggable.fromY, draggable.toX, draggable.toY)
                    }

                    if (isMouseInteracted && draggable.isDragging) {
                        draggable.toX = cursorPosition.x
                        draggable.toY = cursorPosition.y
                        draggable.isDragging = true
                        draggable.onDrag(it, draggable.fromX, draggable.fromY, draggable.toX, draggable.toY)
                    }

                    if (!isMouseInteracted && draggable.isDragging) {
                        draggable.isDragging = false
                        draggable.offDrag(it, draggable.fromX, draggable.fromY, draggable.toX, draggable.toY)
                    }
                }
            }
    }

    private fun updateCursorPosition(x: Double, y: Double) {
        val position = cursor.getComponent<Point2D>()!!
        val dimension = cursor.getComponent<Dimension2D>()!!

        position.setLocation(
            x - (dimension.width / 2),
            y - 30 - (dimension.height / 2)
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