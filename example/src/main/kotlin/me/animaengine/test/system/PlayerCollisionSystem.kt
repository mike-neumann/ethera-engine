package me.animaengine.test.system

import me.animaengine.Anima
import me.animaengine.entity.Entity
import me.animaengine.g2d.entity.component.Movement2D
import me.animaengine.scene.Scene
import me.animaengine.system.LogicSystem
import me.animaengine.test.entity.Player
import me.animaengine.test.entity.component.Position
import org.springframework.stereotype.Component

@Component
class PlayerCollisionSystem : LogicSystem {
    override fun update(deltaTime: Long, scene: Scene, entities: List<Entity>) {
        entities
            .filterIsInstance<Player>()
            .forEach {
                val movement = it.getComponent<Movement2D>()!!
                val position = it.getComponent<Position>()!!

                // top collision
                if (position.y < 0) {
                    position.y = 0f
                }

                // bottom collision
                if (position.y + position.height > Anima.frame.height) {
                    movement.vy = 0f
                    position.y = (Anima.frame.height - position.height).toFloat()
                }

                // right collision
                if (position.x + position.width > Anima.frame.width) {
                    position.x = (Anima.frame.width - position.width).toFloat()
                }

                // left collision
                if (position.x < 0) {
                    position.x = 0f
                }
            }
    }
}