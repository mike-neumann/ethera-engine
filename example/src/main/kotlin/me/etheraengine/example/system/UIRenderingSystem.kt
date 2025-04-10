package me.etheraengine.example.system

import me.etheraengine.runtime.entity.*
import me.etheraengine.runtime.entity.component.*
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics2D
import kotlin.math.roundToInt

@Component
class UIRenderingSystem : RenderingSystem {
    override fun render(scene: Scene, g: Graphics2D, now: Long, deltaTime: Long) {
        val buttons = scene.getFilteredEntities { it is UIButton }

        for (button in buttons) {
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
            button.getComponent<UIClickable>()?.let {
                if (it.isClicked) {
                    g.font = g.font.deriveFont(text.size - 5)
                }
            }
            // can be used to center text on the rendered UIButton (x-axis)
            val xOffset = (button.width / 2) - (g.fontMetrics.stringWidth(text.text) / 2)

            g.drawString(
                text.text,
                (button.x).toInt(),
                (button.y + (button.height / 2) + (g.fontMetrics.height / 3)).toInt()
            )
        }
        val labels = scene.getFilteredEntities { it is UILabel }

        for (label in labels) {
            val text = label.getComponent<UIText>()!!

            g.color = text.color
            g.font = g.font.deriveFont(text.size)
            g.font = g.font.deriveFont(text.style)
            g.drawString(text.text, label.x.toInt(), label.y.toInt())
        }
        val sliders = scene.getFilteredEntities { it is UISlider }

        for (slider in sliders) {
            val text = slider.getComponent<UIText>()!!
            val value = slider.getComponent<UIValue<Double>>()!!

            g.font = g.font.deriveFont(text.size)
            g.font = g.font.deriveFont(text.style)
            g.color = Color.LIGHT_GRAY
            val pinHeight = slider.height / 8

            g.fillRect(
                slider.x.toInt(),
                slider.y.toInt() + ((slider.height / 2) - pinHeight / 2),
                slider.width,
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
                slider.y.toInt(),
                10,
                slider.height
            )

            g.color = text.color

            g.drawString(text.text, slider.x.toInt(), slider.y.toInt() + g.fontMetrics.height)
            g.drawString(
                "${value.value.roundToInt()}%",
                slider.x.toInt(),
                slider.y.toInt() + slider.height
            )
        }
        val checkboxes = scene.getFilteredEntities { it is UICheckbox }

        for (checkbox in checkboxes) {
            val text = checkbox.getComponent<UIText>()!!

            g.color = text.color
            g.font = g.font.deriveFont(text.size)
            g.font = g.font.deriveFont(text.style)
            // can be used to center text on the rendered UIButton (x-axis)
            val xOffset = (checkbox.width / 2) - (g.fontMetrics.stringWidth(text.text) / 2)

            g.drawRect(checkbox.x.toInt(), checkbox.y.toInt(), checkbox.width, checkbox.height)
            g.drawString(
                text.text,
                (checkbox.x + checkbox.width + (checkbox.width / 4)).toInt(),
                (checkbox.y + (checkbox.height / 2) + (g.fontMetrics.height / 3)).toInt()
            )
            val value = checkbox.getComponent<UIValue<Boolean>>()!!

            if (!value.value) {
                g.fillRect(
                    (checkbox.x + 4).toInt(),
                    (checkbox.y + 4).toInt(),
                    // TODO: uneven
                    checkbox.width - 7,
                    checkbox.height - 7
                )
            }
        }

        scene.getFilteredEntities { it.hasComponent<UIFocusable>() }.forEach {
            val focusable = it.getComponent<UIFocusable>()!!

            if (focusable.isFocused) {
                g.color = Color.WHITE
                g.drawRect(it.x.toInt(), it.y.toInt(), it.width, it.height)
            }
        }
    }
}