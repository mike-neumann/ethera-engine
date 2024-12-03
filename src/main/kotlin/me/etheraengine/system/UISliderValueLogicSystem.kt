package me.etheraengine.system

import me.etheraengine.entity.Entity
import me.etheraengine.entity.UISlider
import me.etheraengine.entity.component.UIDraggable
import me.etheraengine.entity.component.UIValue
import me.etheraengine.scene.Scene
import org.springframework.stereotype.Component
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Logic system handling ui slider value control
 */
@Component
class UISliderValueLogicSystem : LogicSystem {
    override fun update(scene: Scene, entities: ConcurrentLinkedQueue<Entity>, now: Long, deltaTime: Long) {
        entities
            .asSequence()
            .filterIsInstance<UISlider>()
            .filter { it.hasComponent<Point2D>() }
            .filter { it.hasComponent<Dimension2D>() }
            .filter { it.hasComponent<UIDraggable>() }
            .filter { it.hasComponent<UIValue<Double>>() }
            .toList()
            .forEach {
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