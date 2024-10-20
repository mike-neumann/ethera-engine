package me.etheraengine.example.system

import me.etheraengine.Ethera
import me.etheraengine.entity.Entity
import me.etheraengine.g2d.entity.component.Movement2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.Position
import org.springframework.stereotype.Component

@Component
class PlayerCollisionSystem : LogicSystem {
    override fun update(scene: Scene, entities: List<Entity>, now: Long, deltaTime: Long) {
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
                if (position.y + position.height > Ethera.frame.height) {
                    movement.vy = 0f
                    position.y = (Ethera.frame.height - position.height).toFloat()
                }

                // right collision
                if (position.x + position.width > Ethera.frame.width) {
                    position.x = (Ethera.frame.width - position.width).toFloat()
                }

                // left collision
                if (position.x < 0) {
                    position.x = 0f
                }
            }
    }
}