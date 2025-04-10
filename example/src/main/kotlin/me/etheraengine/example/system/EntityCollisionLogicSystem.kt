package me.etheraengine.example.system

import me.etheraengine.example.entity.component.Collideable
import me.etheraengine.runtime.g2d.util.CollisionUtils2D
import me.etheraengine.runtime.g2d.util.CollisionUtils2D.mtv
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityCollisionLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val entities = scene.getFilteredEntities { it.getComponent<Collideable>()?.isCollideable == true }

        for (entity in entities) {
            val collidingEntities = getCollidingEntities(scene, entity.x, entity.y, entity.width, entity.height).filter { e -> e != entity }

            for (collidingEntity in collidingEntities) {
                val (tX, tY) = entity mtv collidingEntity
                // allow for floaty mtv correction (entities can walk slightly into other entities hitboxes)
                entity.x += tX / 20f
                entity.y += tY / 20f
            }
        }
    }

    private fun getCollidingEntities(scene: Scene, x: Double, y: Double, width: Int, height: Int) = let {
        scene.getFilteredEntities { it.hasComponent<Collideable>() }
            .filter { CollisionUtils2D.collidesWith(x, y, width, height, x, y, width, height) }
    }
}
