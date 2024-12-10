package me.etheraengine.example.system

import me.etheraengine.entity.*
import me.etheraengine.entity.component.*
import me.etheraengine.scene.Scene
import me.etheraengine.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.math.roundToInt

@Component
class UIRenderingSystem : RenderingSystem {
    override fun render(
        scene: Scene,
        entities: ConcurrentLinkedQueue<Entity>,
        g: Graphics,
        now: Long,
        deltaTime: Long
    ) {
        entities
            .filterIsInstance<UIButton>()
            .forEach {
                val position = it.getComponent<Point2D>()!!
                val dimension = it.getComponent<Dimension2D>()!!
                val text = it.getComponent<UIText>()!!

                g.color = text.color
                g.font = g.font.deriveFont(text.size)
                g.font = g.font.deriveFont(text.style)

                // if the entity hovered, scale up the font size a bit
                it.getComponent<UIHoverable>()?.let {
                    if (it.isHovered) {
                        g.font = g.font.deriveFont(text.size + 5)
                    }
                }

                // if the entity is clicked, reset the font size
                it.getComponent<UIClickable>()?.let {
                    if (it.isClicked) {
                        g.font = g.font.deriveFont(text.size)
                    }
                }

                // can be used to center text on the rendered UIButton (x-axis)
                val xOffset = (dimension.width / 2) - (g.fontMetrics.stringWidth(text.text) / 2)

                g.drawString(
                    text.text,
                    (position.x).toInt(),
                    (position.y + (dimension.height / 2) + (g.fontMetrics.height / 3)).toInt()
                )
            }

        entities
            .filterIsInstance<UILabel>()
            .forEach {
                val position = it.getComponent<Point2D>()!!
                val text = it.getComponent<UIText>()!!

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
            .filterIsInstance<UISlider>()
            .forEach {
                val position = it.getComponent<Point2D>()!!
                val dimension = it.getComponent<Dimension2D>()!!
                val text = it.getComponent<UIText>()!!
                val value = it.getComponent<UIValue<Double>>()!!

                g.font = g.font.deriveFont(text.size)
                g.font = g.font.deriveFont(text.style)
                g.color = Color.LIGHT_GRAY

                val pinHeight = dimension.height / 8

                g.fillRect(
                    position.x.toInt(),
                    position.y.toInt() + ((dimension.height.toInt() / 2) - pinHeight.toInt() / 2),
                    dimension.width.toInt(),
                    pinHeight.toInt()
                )

                g.color = Color.GRAY

                // if the entity is hoverable, paint it white
                it.getComponent<UIHoverable>()?.let {
                    if (it.isHovered) {
                        g.color = Color.WHITE
                    }
                }

                g.fillRect(
                    it.getPinXPositionForCurrentValue(10.0).toInt(),
                    position.y.toInt(),
                    10,
                    dimension.height.toInt()
                )

                g.color = text.color

                g.drawString(text.text, position.x.toInt(), position.y.toInt() + g.fontMetrics.height)
                g.drawString(
                    "${value.value.roundToInt()}%",
                    position.x.toInt(),
                    position.y.toInt() + dimension.height.toInt()
                )
            }

        entities
            .filterIsInstance<UICheckbox>()
            .forEach {
                val position = it.getComponent<Point2D>()!!
                val dimension = it.getComponent<Dimension2D>()!!
                val text = it.getComponent<UIText>()!!

                g.color = text.color
                g.font = g.font.deriveFont(text.size)
                g.font = g.font.deriveFont(text.style)

                // can be used to center text on the rendered UIButton (x-axis)
                val xOffset = (dimension.width / 2) - (g.fontMetrics.stringWidth(text.text) / 2)

                g.drawRect(
                    position.x.toInt(),
                    position.y.toInt(),
                    dimension.width.toInt(),
                    dimension.height.toInt()
                )
                g.drawString(
                    text.text,
                    (position.x + dimension.width + (dimension.width / 4)).toInt(),
                    (position.y + (dimension.height / 2) + (g.fontMetrics.height / 3)).toInt()
                )

                val value = it.getComponent<UIValue<Boolean>>()!!

                if (!value.value) {
                    g.fillRect(
                        (position.x + 4).toInt(),
                        (position.y + 4).toInt(),
                        // TODO: uneven
                        dimension.width.toInt() - 7,
                        dimension.height.toInt() - 7
                    )
                }
            }

        entities
            .filter { it.hasComponent<Point2D>() }
            .filter { it.hasComponent<Dimension2D>() }
            .filter { it.hasComponent<UIFocusable>() }
            .forEach {
                val position = it.getComponent<Point2D>()!!
                val dimension = it.getComponent<Dimension2D>()!!
                val focusable = it.getComponent<UIFocusable>()!!

                if (focusable.isFocused) {
                    g.color = Color.WHITE
                    g.drawRect(
                        position.x.toInt(),
                        position.y.toInt(),
                        dimension.width.toInt(),
                        dimension.height.toInt()
                    )
                }
            }
    }
}