package me.etheraengine.runtime.system

import me.etheraengine.runtime.entity.UISlider
import me.etheraengine.runtime.entity.component.UIDraggable
import me.etheraengine.runtime.entity.component.UIValue
import me.etheraengine.runtime.g2d.entity.component.Dimensions2D
import me.etheraengine.runtime.g2d.entity.component.Position2D
import me.etheraengine.runtime.scene.Scene
import org.springframework.stereotype.Component

/**
 * Logic system handling ui slider value control
 */
@Component
class UISliderValueLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val sliders = scene.getEntities {
            it is UISlider
                    && it.hasComponent<Position2D>()
                    && it.hasComponent<Dimensions2D>()
                    && it.hasComponent<UIDraggable>()
                    && it.hasComponent<UIValue<Double>>()
        }

        for (slider in sliders) {
            val position = slider.getComponent<Position2D>()!!
            val dimensions = slider.getComponent<Dimensions2D>()!!
            val draggable = slider.getComponent<UIDraggable>()!!
            val value = slider.getComponent<UIValue<Double>>()!!

            if (draggable.isDragging) {
                val draggedDistance = draggable.toX - position.x
                val newValue = (draggedDistance / dimensions.width * 100) / 100 * value.maxValue
                val oldValue = value.value

                value.value = Math.clamp(newValue, 0.0, 0.0.coerceAtLeast(newValue))
                value.onChange(slider, oldValue, newValue)
            }
        }
    }
}