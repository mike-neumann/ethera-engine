package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.example.entity.component.Collideable
import me.etheraengine.example.entity.component.Position
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.g2d.util.CollisionUtils2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityCollisionLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        scene.getEntities {
            it.hasComponent<Position2D>() && it.hasComponent<Dimensions2D>() && it.getComponent<Collideable>()?.isCollideable == true
        }.forEach {
            val position = it.getComponent<Position2D>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!
            val collidingEntities =
                getCollidingEntities(
                    scene,
                    position.x,
                    position.y,
                    dimensions.width,
                    dimensions.height
                )
                    .filter { e -> e != it }

            collidingEntities.forEach {
                val entityPosition = it.getComponent<Position>()!!
                val entityDimensions = it.getComponent<Dimensions2D>()!!
                val mtv = CollisionUtils2D.getMinimumTranslationVector(
                    position.x,
                    position.y,
                    dimensions.width,
                    dimensions.height,
                    entityPosition.x,
                    entityPosition.y,
                    entityDimensions.width,
                    entityDimensions.height
                )

                // allow for floaty mtv correction (entities can walk slightly into other entities hitboxes)
                position.setLocation(
                    position.x + (mtv.first / 20f),
                    position.y + (mtv.second / 20f)
                )
            }
        }
    }

    private fun getCollidingEntities(
        scene: Scene,
        x: Double,
        y: Double,
        width: Int,
        height: Int,
    ): List<Entity> {
        return scene.getEntities {
            it.hasComponent<Position>() && it.hasComponent<Dimensions2D>() && it.hasComponent<Collideable>()
        }.filter {
            val position = it.getComponent<Position>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!

            CollisionUtils2D.checkCollision(
                position.x,
                position.y,
                dimensions.width,
                dimensions.height,
                x,
                y,
                width,
                height
            )
        }
    }
}