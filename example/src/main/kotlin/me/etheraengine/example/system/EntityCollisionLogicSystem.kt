package me.etheraengine.example.system

import me.etheraengine.example.entity.component.Collidable
import me.etheraengine.example.world.Tile
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.g2d.util.CollisionUtils2D.collidesWith
import me.etheraengine.runtime.g2d.util.CollisionUtils2D.mtv
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityCollisionLogicSystem : LogicSystem {
    override fun update(entity: Entity, scene: Scene, now: Long, deltaTime: Long) {
        if (!entity.hasComponent<Collidable>()) return
        val collidingEntities = scene.getFilteredEntities { it !is Tile && it != entity && it collidesWith entity }

        for (collidingEntity in collidingEntities) {
            val (tX, tY) = entity mtv collidingEntity
            // allow for floaty mtv correction (entities can walk slightly into other entities hitboxes)
            entity.x += tX / 20f
            entity.y += tY / 20f
        }
    }
}