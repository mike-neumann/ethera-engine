package me.etheraengine.system

import me.etheraengine.entity.Cursor
import me.etheraengine.entity.Entity
import me.etheraengine.entity.UIElement
import me.etheraengine.entity.component.Clickable
import me.etheraengine.entity.component.Draggable
import me.etheraengine.entity.component.Hoverable
import me.etheraengine.entity.component.Mouse
import me.etheraengine.g2d.util.CollisionUtils2D
import me.etheraengine.scene.Scene
import org.springframework.stereotype.Component
import java.awt.geom.Point2D

/**
 * System handling ui entity element logic like: hover and click events
 */
@Component
class UIEventSystem(
    private val cursor: Cursor
) : LogicSystem {
    override fun update(scene: Scene, entities: List<Entity>, now: Long, deltaTime: Long) {
        val cursorPosition = cursor.getComponent<Point2D>()!!
        val mouse = cursor.getComponent<Mouse>()!!

        entities
            .filterIsInstance<UIElement>()
            .forEach {
                val isHovering = CollisionUtils2D.checkCollision(it, cursor)

                if (it.hasComponent<Hoverable>()) {
                    val hoverable = it.getComponent<Hoverable>()!!

                    if (isHovering && !hoverable.isHovered) {
                        hoverable.isHovered = true
                        hoverable.onHover(it)
                    } else if (!isHovering && hoverable.isHovered) {
                        hoverable.isHovered = false
                        hoverable.offHover(it)
                    }
                }

                if (it.hasComponent<Clickable>()) {
                    val clickable = it.getComponent<Clickable>()!!

                    if (isHovering && mouse.isLeftClicked && !clickable.isClicked) {
                        clickable.isClicked = true
                        clickable.onClick(it)
                    } else if ((!isHovering || !mouse.isLeftClicked) && clickable.isClicked) {
                        clickable.isClicked = false
                        clickable.offClick(it)
                    }
                }

                if (it.hasComponent<Draggable>()) {
                    val draggable = it.getComponent<Draggable>()!!

                    if (isHovering && mouse.isLeftClicked && !draggable.isDragging) {
                        draggable.fromX = cursorPosition.x
                        draggable.fromY = cursorPosition.y
                        draggable.toX = cursorPosition.x
                        draggable.toY = cursorPosition.y
                        draggable.isDragging = true
                        draggable.onDrag(it, draggable.fromX, draggable.fromY, draggable.toX, draggable.toY)
                    }

                    if (isHovering && mouse.isLeftClicked && draggable.isDragging) {
                        draggable.toX = cursorPosition.x
                        draggable.toY = cursorPosition.y
                        draggable.isDragging = true
                        draggable.onDrag(it, draggable.fromX, draggable.fromY, draggable.toX, draggable.toY)
                    }

                    if ((!mouse.isLeftClicked || !isHovering) && draggable.isDragging) {
                        draggable.isDragging = false
                        draggable.offDrag(it, draggable.fromX, draggable.fromY, draggable.toX, draggable.toY)
                    }
                }
            }
    }
}