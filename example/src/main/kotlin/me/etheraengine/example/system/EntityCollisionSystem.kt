package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.g2d.util.CollisionUtils
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import me.etheraengine.example.entity.component.Collideable
import me.etheraengine.example.entity.component.Position
import org.springframework.stereotype.Component

@Component
class EntityCollisionSystem : LogicSystem {
    override fun update(deltaTime: Long, scene: Scene, entities: List<Entity>) {
        entities
            .filter { it.hasComponent<Position2D>() }
            .filter { it.hasComponent<Collideable>() }
            .forEach {
                val position = it.getComponent<Position2D>()!!
                val collidingEntities =
                    getCollidingEntities(entities, position.x, position.y, position.width, position.height)
                        .filter { e -> e != it }

                collidingEntities.forEach {
                    val entityPosition = it.getComponent<Position>()!!
                    val mtv = CollisionUtils.getMiniumTranslationVector(
                        position.x,
                        position.y,
                        position.width,
                        position.height,
                        entityPosition.x,
                        entityPosition.y,
                        entityPosition.width,
                        entityPosition.height
                    )

                    // allow for floaty mtv correction (entities can walk slightly into other entities hitboxes)
                    position.x += (mtv.first / 20f)
                    position.y += (mtv.second / 20f)
                }
            }
    }

    private fun getCollidingEntities(
        entities: List<Entity>,
        x: Float,
        y: Float,
        width: Int,
        height: Int
    ): List<Entity> {
        return entities
            .filter { it.hasComponent<Collideable>() }
            .filter {
                val positionComponent = it.getComponent<Position>()!!

                CollisionUtils.checkCollision(
                    positionComponent.x,
                    positionComponent.y,
                    positionComponent.width,
                    positionComponent.height,
                    x,
                    y,
                    width,
                    height
                )
            }
    }
}