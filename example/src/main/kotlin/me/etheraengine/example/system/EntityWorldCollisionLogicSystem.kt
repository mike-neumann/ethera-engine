package me.etheraengine.example.system

import me.etheraengine.example.world.Tile
import me.etheraengine.g2d.util.CollisionUtils2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component
import java.awt.geom.Dimension2D
import java.awt.geom.Point2D

@Component
class EntityWorldCollisionLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val tiles = scene.getEntities {
            it is Tile
        } as List<Tile>

        scene.getEntities {
            it !in tiles && it.hasComponent<Point2D>() && it.hasComponent<Dimension2D>()
        }.forEach {
            val position = it.getComponent<Point2D>()!!
            val dimension = it.getComponent<Dimension2D>()!!
            val nextCollidingTiles =
                getCollidingTiles(
                    tiles,
                    position.x,
                    position.y,
                    dimension.width,
                    dimension.height
                )
            val nextNotPassableCollidingTiles = nextCollidingTiles.filter { !it.tileType.isPassable }

            nextNotPassableCollidingTiles.forEach {
                val tilePosition = it.getComponent<Point2D>()!!
                val tileDimension = it.getComponent<Dimension2D>()!!
                val mtv = CollisionUtils2D.getMinimumTranslationVector(
                    position.x,
                    position.y,
                    dimension.width,
                    dimension.height,
                    tilePosition.x,
                    tilePosition.y,
                    tileDimension.width,
                    tileDimension.height
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
        width: Double,
        height: Double,
    ): List<Tile> {
        return tiles
            .filter {
                val position = it.getComponent<Point2D>()!!
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