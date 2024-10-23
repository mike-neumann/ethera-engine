package me.etheraengine.listener

import me.etheraengine.entity.Cursor
import me.etheraengine.entity.component.Mouse
import org.springframework.stereotype.Component
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

/**
 * Listener responsible for user cursor control in the ECS pattern
 */
@Component
class CursorListener(
    private val cursor: Cursor
) : MouseMotionListener, MouseListener {
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

    override fun mouseClicked(e: MouseEvent) {

    }

    override fun mousePressed(e: MouseEvent) {
        val mouse = cursor.getComponent<Mouse>()!!

        when (e.button) {
            MouseEvent.BUTTON1 -> {
                mouse.isLeftClicked = true
            }

            MouseEvent.BUTTON2 -> {
                mouse.isRightClicked = true
            }
        }
    }

    override fun mouseReleased(e: MouseEvent) {
        val mouse = cursor.getComponent<Mouse>()!!

        when (e.button) {
            MouseEvent.BUTTON1 -> {
                mouse.isLeftClicked = false
            }

            MouseEvent.BUTTON2 -> {
                mouse.isRightClicked = false
            }
        }
    }

    override fun mouseEntered(e: MouseEvent?) {
    }

    override fun mouseExited(e: MouseEvent?) {
    }
}