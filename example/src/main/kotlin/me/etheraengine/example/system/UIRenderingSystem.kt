package me.etheraengine.example.system

import me.etheraengine.entity.Button
import me.etheraengine.entity.Entity
import me.etheraengine.entity.Label
import me.etheraengine.entity.Slider
import me.etheraengine.entity.component.Clickable
import me.etheraengine.entity.component.Hoverable
import me.etheraengine.entity.component.Text
import me.etheraengine.entity.component.Value
import me.etheraengine.scene.Scene
import me.etheraengine.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D
import kotlin.math.roundToInt

@Component
class UIRenderingSystem : RenderingSystem {
    override fun render(scene: Scene, entities: List<Entity>, g: Graphics, now: Long, deltaTime: Long) {
        entities
            .filterIsInstance<Button>()
            .forEach {
                val position = it.getComponent<Point2D>()!!
                val dimension = it.getComponent<Dimension2D>()!!
                val text = it.getComponent<Text>()!!

                g.font = g.font.deriveFont(text.size)
                g.color = Color.GRAY

                if (it.hasComponent<Clickable>()) {
                    val clickable = it.getComponent<Clickable>()!!

                    if (clickable.isClicked) {
                        g.color = Color.DARK_GRAY
                    }
                }

                g.fillRect(position.x.toInt(), position.y.toInt(), dimension.width.toInt(), dimension.height.toInt())
                g.color = text.color

                if (it.hasComponent<Hoverable>()) {
                    val hoverable = it.getComponent<Hoverable>()!!

                    if (hoverable.isHovered) {
                        g.color = Color.WHITE
                    }
                }

                val xOffset = (dimension.width / 2) - (g.fontMetrics.stringWidth(text.text) / 2)

                g.drawString(
                    text.text,
                    (position.x + xOffset).toInt(),
                    (position.y + (dimension.height / 2) + (g.fontMetrics.height / 3)).toInt()
                )
            }

        entities
            .filterIsInstance<Label>()
            .forEach {
                val position = it.getComponent<Point2D>()!!
                val text = it.getComponent<Text>()!!

                g.color = text.color
                g.font = g.font.deriveFont(text.size)
                g.font = g.font.deriveFont(text.style)

                g.drawString(
                    text.text,
                    position.x.toInt(),
                    position.y.toInt()
                )
            }

        entities
            .filterIsInstance<Slider>()
            .forEach {
                val position = it.getComponent<Point2D>()!!
                val dimension = it.getComponent<Dimension2D>()!!
                val text = it.getComponent<Text>()!!
                val value = it.getComponent<Value<Double>>()!!

                g.color = Color.GRAY

                g.fillRect(position.x.toInt(), position.y.toInt(), dimension.width.toInt(), dimension.height.toInt())
                g.color = Color.DARK_GRAY

                if (it.hasComponent<Hoverable>()) {
                    val hoverable = it.getComponent<Hoverable>()!!

                    if (hoverable.isHovered) {
                        g.color = Color.WHITE
                    }
                }

                g.fillRect(
                    it.getPinXPositionForCurrentValue().toInt(),
                    position.y.toInt(),
                    10,
                    dimension.height.toInt()
                )

                g.color = text.color
                g.font = g.font.deriveFont(text.size)
                g.font = g.font.deriveFont(text.style)

                g.drawString(text.text, position.x.toInt(), position.y.toInt() + g.fontMetrics.height)
                g.drawString(
                    "${value.value.roundToInt()}%",
                    position.x.toInt(),
                    position.y.toInt() + dimension.height.toInt()
                )
            }
    }
}