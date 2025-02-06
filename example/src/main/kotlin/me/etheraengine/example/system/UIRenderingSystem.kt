package me.etheraengine.example.system

import me.etheraengine.runtime.entity.*
import me.etheraengine.runtime.entity.component.*
import me.etheraengine.runtime.g2d.entity.component.Dimensions2D
import me.etheraengine.runtime.g2d.entity.component.Position2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics
import kotlin.math.roundToInt

@Component
class UIRenderingSystem : RenderingSystem {
    override fun render(scene: Scene, g: Graphics, now: Long, deltaTime: Long) {
        val buttons = scene.getEntities { it is UIButton }

        for (button in buttons) {
            val position = button.getComponent<Position2D>()!!
            val dimensions = button.getComponent<Dimensions2D>()!!
            val text = button.getComponent<UIText>()!!

            g.color = text.color
            g.font = g.font.deriveFont(text.size)
            g.font = g.font.deriveFont(text.style)
            // if the entity hovered, scale up the font size a bit
            button.getComponent<UIHoverable>()?.let {
                if (it.isHovered) {
                    g.font = g.font.deriveFont(text.size + 5)
                }
            }
            // if the entity is clicked, reset the font size
            button.getComponent<UIClickable>()?.let {
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
        val labels = scene.getEntities { it is UILabel }

        for (label in labels) {
            val position = label.getComponent<Position2D>()!!
            val text = label.getComponent<UIText>()!!

            g.color = text.color
            g.font = g.font.deriveFont(text.size)
            g.font = g.font.deriveFont(text.style)

            g.drawString(
                text.text,
                position.x.toInt(),
                position.y.toInt()
            )
        }
        val sliders = scene.getEntities { it is UISlider }

        for (slider in sliders) {
            val position = slider.getComponent<Position2D>()!!
            val dimensions = slider.getComponent<Dimensions2D>()!!
            val text = slider.getComponent<UIText>()!!
            val value = slider.getComponent<UIValue<Double>>()!!

            g.font = g.font.deriveFont(text.size)
            g.font = g.font.deriveFont(text.style)
            g.color = Color.LIGHT_GRAY
            val pinHeight = dimensions.height / 8

            g.fillRect(
                position.x.toInt(),
                position.y.toInt() + ((dimensions.height / 2) - pinHeight / 2),
                dimensions.width,
                pinHeight
            )

            g.color = Color.GRAY
            // if the entity is hoverable, paint it white
            slider.getComponent<UIHoverable>()?.let {
                if (it.isHovered) {
                    g.color = Color.WHITE
                }
            }

            g.fillRect(
                (slider as UISlider).getPinXPositionForCurrentValue(10.0).toInt(),
                position.y.toInt(),
                10,
                dimensions.height
            )

            g.color = text.color

            g.drawString(text.text, position.x.toInt(), position.y.toInt() + g.fontMetrics.height)
            g.drawString(
                "${value.value.roundToInt()}%",
                position.x.toInt(),
                position.y.toInt() + dimensions.height
            )
        }
        val checkboxes = scene.getEntities { it is UICheckbox }

        for (checkbox in checkboxes) {
            val position = checkbox.getComponent<Position2D>()!!
            val dimensions = checkbox.getComponent<Dimensions2D>()!!
            val text = checkbox.getComponent<UIText>()!!

            g.color = text.color
            g.font = g.font.deriveFont(text.size)
            g.font = g.font.deriveFont(text.style)
            // can be used to center text on the rendered UIButton (x-axis)
            val xOffset = (dimensions.width / 2) - (g.fontMetrics.stringWidth(text.text) / 2)

            g.drawRect(
                position.x.toInt(),
                position.y.toInt(),
                dimensions.width,
                dimensions.height
            )
            g.drawString(
                text.text,
                (position.x + dimensions.width + (dimensions.width / 4)).toInt(),
                (position.y + (dimensions.height / 2) + (g.fontMetrics.height / 3)).toInt()
            )
            val value = checkbox.getComponent<UIValue<Boolean>>()!!

            if (!value.value) {
                g.fillRect(
                    (position.x + 4).toInt(),
                    (position.y + 4).toInt(),
                    // TODO: uneven
                    dimensions.width - 7,
                    dimensions.height - 7
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
                    dimensions.width,
                    dimensions.height
                )
            }
        }
    }
}