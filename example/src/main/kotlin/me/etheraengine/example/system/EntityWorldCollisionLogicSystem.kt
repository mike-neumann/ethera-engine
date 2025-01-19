package me.etheraengine.example.system

import me.etheraengine.example.world.Tile
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.g2d.util.CollisionUtils2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityWorldCollisionLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val tiles = scene.getEntities {
            it is Tile
        } as List<Tile>

        scene.getEntities {
            it !in tiles && it.hasComponent<Position2D>() && it.hasComponent<Dimensions2D>()
        }.forEach {
            val position = it.getComponent<Position2D>()!!
            val dimensions = it.getComponent<Dimensions2D>()!!
            val nextCollidingTiles =
                getCollidingTiles(
                    tiles,
                    position.x,
                    position.y,
                    dimensions.width,
                    dimensions.height
                )
            val nextNotPassableCollidingTiles = nextCollidingTiles.filter { !it.tileType.isPassable }

            nextNotPassableCollidingTiles.forEach {
                val tilePosition = it.getComponent<Position2D>()!!
                val tileDimensions = it.getComponent<Dimensions2D>()!!
                val mtv = CollisionUtils2D.getMinimumTranslationVector(
                    position.x,
                    position.y,
                    dimensions.width,
                    dimensions.height,
                    tilePosition.x,
                    tilePosition.y,
                    tileDimensions.width,
                    tileDimensions.height
                )

                position.setLocation(
                    position.x + mtv.first,
                    position.y + mtv.second
                )
            }
        }
    }

    private fun getCollidingTiles(
        tiles: List<Tile>,
        x: Double,
        y: Double,
        width: Int,
        height: Int,
    ): List<Tile> {
        return tiles
            .filter {
                val position = it.getComponent<Position2D>()!!
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