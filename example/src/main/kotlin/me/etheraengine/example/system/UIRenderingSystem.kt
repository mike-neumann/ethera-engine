package me.etheraengine.example.system

import me.etheraengine.entity.UIButton
import me.etheraengine.entity.UICheckbox
import me.etheraengine.entity.UILabel
import me.etheraengine.entity.UISlider
import me.etheraengine.entity.component.*
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics
import kotlin.math.roundToInt

@Component
class UIRenderingSystem : RenderingSystem {
    override fun render(
        scene: Scene,
        g: Graphics,
        now: Long,
        deltaTime: Long,
    ) {
        scene.getEntities {
            it is UIButton
        }.forEach {
            val position = it.getComponent<Position2D>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!
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
            val xOffset = (dimensions.width / 2) - (g.fontMetrics.stringWidth(text.text) / 2)

            g.drawString(
                text.text,
                (position.x).toInt(),
                (position.y + (dimensions.height / 2) + (g.fontMetrics.height / 3)).toInt()
            )
        }

        scene.getEntities {
            it is UILabel
        }.forEach {
            val position = it.getComponent<Position2D>()!!
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

        scene.getEntities {
            it is UISlider
        }.forEach {
            val position = it.getComponent<Position2D>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!
            val text = it.getComponent<UIText>()!!
            val value = it.getComponent<UIValue<Double>>()!!

            g.font = g.font.deriveFont(text.size)
            g.font = g.font.deriveFont(text.style)
            g.color = Color.LIGHT_GRAY

            val pinHeight = dimensions.height / 8

            g.fillRect(
                position.x.toInt(),
                position.y.toInt() + ((dimensions.height.toInt() / 2) - pinHeight.toInt() / 2),
                dimensions.width.toInt(),
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
                (it as UISlider).getPinXPositionForCurrentValue(10.0).toInt(),
                position.y.toInt(),
                10,
                dimensions.height.toInt()
            )

            g.color = text.color

            g.drawString(text.text, position.x.toInt(), position.y.toInt() + g.fontMetrics.height)
            g.drawString(
                "${value.value.roundToInt()}%",
                position.x.toInt(),
                position.y.toInt() + dimensions.height.toInt()
            )
        }

        scene.getEntities {
            it is UICheckbox
        }.forEach {
            val position = it.getComponent<Position2D>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!
            val text = it.getComponent<UIText>()!!

            g.color = text.color
            g.font = g.font.deriveFont(text.size)
            g.font = g.font.deriveFont(text.style)

            // can be used to center text on the rendered UIButton (x-axis)
            val xOffset = (dimensions.width / 2) - (g.fontMetrics.stringWidth(text.text) / 2)

            g.drawRect(
                position.x.toInt(),
                position.y.toInt(),
                dimensions.width.toInt(),
                dimensions.height.toInt()
            )
            g.drawString(
                text.text,
                (position.x + dimensions.width + (dimensions.width / 4)).toInt(),
                (position.y + (dimensions.height / 2) + (g.fontMetrics.height / 3)).toInt()
            )

            val value = it.getComponent<UIValue<Boolean>>()!!

            if (!value.value) {
                g.fillRect(
                    (position.x + 4).toInt(),
                    (position.y + 4).toInt(),
                    // TODO: uneven
                    dimensions.width.toInt() - 7,
                    dimensions.height.toInt() - 7
                )
            }
        }

        scene.getEntities {
            it.hasComponent<Position2D>() && it.hasComponent<Dimensions2D>() && it.hasComponent<UIFocusable>()
        }.forEach {
            val position = it.getComponent<Position2D>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!
            val focusable = it.getComponent<UIFocusable>()!!

            if (focusable.isFocused) {
                g.color = Color.WHITE
                g.drawRect(
                    position.x.toInt(),
                    position.y.toInt(),
                    dimensions.width.toInt(),
                    dimensions.height.toInt()
                )
            }
        }
    }
}