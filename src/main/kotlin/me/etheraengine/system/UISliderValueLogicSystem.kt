package me.etheraengine.system

import me.etheraengine.entity.UISlider
import me.etheraengine.entity.component.UIDraggable
import me.etheraengine.entity.component.UIValue
import me.etheraengine.scene.Scene
import org.springframework.stereotype.Component
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

/**
 * Logic system handling ui slider value control
 */
@Component
class UISliderValueLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        scene.getEntities {
            it is UISlider
                    && it.hasComponent<Point2D>()
                    && it.hasComponent<Dimension2D>()
                    && it.hasComponent<UIDraggable>()
                    && it.hasComponent<UIValue<Double>>()
        }.forEach {
            val position = it.getComponent<Point2D>()!!
            val dimension = it.getComponent<Dimension2D>()!!
            val draggable = it.getComponent<UIDraggable>()!!
            val value = it.getComponent<UIValue<Double>>()!!

            if (draggable.isDragging) {
                val draggedDistance = draggable.toX - position.x
                val newValue = (draggedDistance / dimension.width * 100) / 100 * value.maxValue
                val oldValue = value.value

                value.value = Math.clamp(newValue, 0.0, 0.0.coerceAtLeast(newValue))
                value.onChange(it, oldValue, newValue)
            }
        }
    }
}