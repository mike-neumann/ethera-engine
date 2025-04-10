package me.etheraengine.example.system

import me.etheraengine.example.world.Tile
import me.etheraengine.runtime.g2d.util.CollisionUtils2D.collidesWith
import me.etheraengine.runtime.g2d.util.CollisionUtils2D.mtv
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityWorldCollisionLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val tiles = scene.getFilteredEntities { it is Tile } as List<Tile>
        val entities = scene.getFilteredEntities { it !in tiles }

        for (entity in entities) {
            val nextCollidingTiles = tiles.filter { entity collidesWith it }
            val nextNotPassableCollidingTiles = nextCollidingTiles.filter { !it.tileType.isPassable }

            for (nextNotPassableCollidingTile in nextNotPassableCollidingTiles) {
                val (tX, tY) = entity mtv nextNotPassableCollidingTile

                entity.x += tX
                entity.y += tY
            }
        }
    }
}