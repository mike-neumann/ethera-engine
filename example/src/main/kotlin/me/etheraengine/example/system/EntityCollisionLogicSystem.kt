package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.example.entity.component.Collideable
import me.etheraengine.example.entity.component.Position
import me.etheraengine.g2d.util.CollisionUtils2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

@Component
class EntityCollisionLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        scene.getEntities {
            it.hasComponent<Point2D>() && it.hasComponent<Dimension2D>() && it.getComponent<Collideable>()?.isCollideable == true
        }.forEach {
            val position = it.getComponent<Point2D>()!!
            val dimension = it.getComponent<Dimension2D>()!!
            val collidingEntities =
                getCollidingEntities(
                    scene,
                    position.x,
                    position.y,
                    dimension.width,
                    dimension.height
                )
                    .filter { e -> e != it }

            collidingEntities.forEach {
                val entityPosition = it.getComponent<Position>()!!
                val entityDimension = it.getComponent<Dimension2D>()!!
                val mtv = CollisionUtils2D.getMinimumTranslationVector(
                    position.x,
                    position.y,
                    dimension.width,
                    dimension.height,
                    entityPosition.x,
                    entityPosition.y,
                    entityDimension.width,
                    entityDimension.height
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
        width: Double,
        height: Double,
    ): List<Entity> {
        return scene.getEntities {
            it.hasComponent<Position>() && it.hasComponent<Dimension2D>() && it.hasComponent<Collideable>()
        }.filter {
            val position = it.getComponent<Position>()!!
            val dimension = it.getComponent<Dimension2D>()!!

            CollisionUtils2D.checkCollision(
                position.x,
                position.y,
                dimension.width,
                dimension.height,
                x,
                y,
                width,
                height
            )
        }
    }
}