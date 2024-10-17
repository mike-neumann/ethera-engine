package me.animaengine.test.system

import me.animaengine.entity.Entity
import me.animaengine.g2d.util.CollisionUtils
import me.animaengine.scene.Scene
import me.animaengine.system.LogicSystem
import me.animaengine.test.entity.component.Position
import me.animaengine.test.world.Tile
import org.springframework.stereotype.Component

@Component
class EntityWorldCollisionSystem : LogicSystem {
    override fun update(deltaTime: Long, scene: Scene, entities: List<Entity>) {
        val tiles = entities.filterIsInstance<Tile>()

        entities
            .filter { it !in tiles }
            .filter { it.hasComponent<Position>() }
            .forEach {
                val position = it.getComponent<Position>()!!
                val nextCollidingTiles =
                    getCollidingTiles(tiles, position.x, position.y, position.width, position.height)
                val nextNotPassableCollidingTiles = nextCollidingTiles.filter { !it.tileType.isPassable }

                nextNotPassableCollidingTiles.forEach {
                    val tilePositionComponent = it.getComponent<Position>()!!
                    val mtv = CollisionUtils.getMiniumTranslationVector(
                        position.x,
                        position.y,
                        position.width,
                        position.height,
                        tilePositionComponent.x,
                        tilePositionComponent.y,
                        tilePositionComponent.width,
                        tilePositionComponent.height
                    )

                    position.x += mtv.first
                    position.y += mtv.second
                }
            }
    }

    private fun getCollidingTiles(tiles: List<Tile>, x: Float, y: Float, width: Int, height: Int): List<Tile> {
        return tiles.filter {
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