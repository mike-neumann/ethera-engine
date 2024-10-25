package me.etheraengine.system

import me.etheraengine.entity.Entity
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
class UISliderValueSystem : LogicSystem {
    override fun update(scene: Scene, entities: List<Entity>, now: Long, deltaTime: Long) {
        entities
            .filterIsInstance<UISlider>()
            .forEach {
                val position = it.getComponent<Point2D>()!!
                val dimension = it.getComponent<Dimension2D>()!!
                val draggable = it.getComponent<UIDraggable>()!!
                val value = it.getComponent<UIValue<Double>>()!!

                if (draggable.isDragging) {
                    val draggedDistance = draggable.toX - position.x
                    val newValue = (draggedDistance / dimension.width * 100) / 100 * value.maxValue

                    if (newValue < 0) {
                        value.value = 0.0
                    } else {
                        value.value = newValue
                    }
                }
            }
    }
}