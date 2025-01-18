package me.etheraengine.example.system

import me.etheraengine.Ethera
import me.etheraengine.example.entity.Player
import me.etheraengine.g2d.entity.component.Movement2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

@Component
class PlayerCollisionLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        scene.getEntities {
            it is Player
        }.forEach {
            val movement = it.getComponent<Movement2D>()!!
            val position = it.getComponent<Point2D>()!!
            val dimension = it.getComponent<Dimension2D>()!!

            // top collision
            if (position.y < 0) {
                position.setLocation(
                    0.0,
                    position.x
                )
            }

            // bottom collision
            if (position.y + dimension.height > Ethera.frame.height) {
                movement.vy = 0.0
                position.setLocation(
                    Ethera.frame.height - dimension.height,
                    position.x
                )
            }

            // right collision
            if (position.x + dimension.width > Ethera.frame.width) {
                position.setLocation(
                    position.y,
                    Ethera.frame.width - dimension.width
                )
            }

            // left collision
            if (position.x < 0) {
                position.setLocation(
                    position.y,
                    0.0
                )
            }
        }
    }
}