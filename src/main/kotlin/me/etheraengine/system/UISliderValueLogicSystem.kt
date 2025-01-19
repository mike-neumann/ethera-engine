package me.etheraengine.system

import me.etheraengine.entity.UISlider
import me.etheraengine.entity.component.UIDraggable
import me.etheraengine.entity.component.UIValue
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.scene.Scene
import org.springframework.stereotype.Component

/**
 * Logic system handling ui slider value control
 */
@Component
class UISliderValueLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        scene.getEntities {
            it is UISlider
                    && it.hasComponent<Position2D>()
                    && it.hasComponent<Dimensions2D>()
                    && it.hasComponent<UIDraggable>()
                    && it.hasComponent<UIValue<Double>>()
        }.forEach {
            val position = it.getComponent<Position2D>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!
            val draggable = it.getComponent<UIDraggable>()!!
            val value = it.getComponent<UIValue<Double>>()!!

            if (draggable.isDragging) {
                val draggedDistance = draggable.toX - position.x
                val newValue = (draggedDistance / dimensions.width * 100) / 100 * value.maxValue
                val oldValue = value.value

                value.value = Math.clamp(newValue, 0.0, 0.0.coerceAtLeast(newValue))
                value.onChange(it, oldValue, newValue)
            }
        }
    }
}