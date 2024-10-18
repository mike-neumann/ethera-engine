package me.animaengine.test.scene

import me.animaengine.Anima
import me.animaengine.config.AnimaConfig
import me.animaengine.entity.component.State
import me.animaengine.g2d.system.Bounds2DRenderingSystem
import me.animaengine.scene.Scene
import me.animaengine.test.entity.Enemy
import me.animaengine.test.entity.EntityState
import me.animaengine.test.entity.Player
import me.animaengine.test.entity.component.EnemyAI
import me.animaengine.test.listener.PlayerKeyListener
import me.animaengine.test.system.*
import me.animaengine.test.world.ExampleWorld
import me.animaengine.test.world.Tile
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import kotlin.system.exitProcess

@Component
class ExampleScene(
    private val animaConfig: AnimaConfig,

    private val player: Player,

    private val playerKeyListener: PlayerKeyListener,

    private val entityStateSystem: EntityStateSystem,
    private val entityAnimationSystem: EntityAnimationSystem,
    private val playerAttackSystem: PlayerAttackSystem,

    private val entityPositionMovementSystem: EntityPositionMovementSystem,
    private val playerMovementSystem: PlayerMovementSystem,
    private val entityWorldCollisionSystem: EntityWorldCollisionSystem,
    private val enemyAIMovementSystem: EnemyAIMovementSystem,
    private val entityCollisionSystem: EntityCollisionSystem,

    private val bounds2DRenderingSystem: Bounds2DRenderingSystem,
    private val entityHealthHudRenderingSystem: EntityHealthHudRenderingSystem
) : Scene() {
    companion object {
        private val FONT = Font(Font.SERIF, Font.BOLD, 50)
    }

    override fun onEnable() {
        addKeyListeners(playerKeyListener)
        // Uncomment bounds2DRenderingSystem, if you want to see each entity's bounds / hitbox
        addRenderingSystems(/*bounds2DRenderingSystem, */entityHealthHudRenderingSystem)
        addLogicSystems(
            playerAttackSystem,
            entityPositionMovementSystem,
            playerMovementSystem,
            entityStateSystem,
            entityWorldCollisionSystem,
            enemyAIMovementSystem,
            entityCollisionSystem,
            entityAnimationSystem
        )

        val tiles = ExampleWorld.tileTypeMask.mapIndexed { yIndex, tileTypes ->
            tileTypes.mapIndexed { xIndex, tileType ->
                val tileX = xIndex * ExampleWorld.tileSize
                val tileY = yIndex * ExampleWorld.tileSize

                Tile(tileType, tileX.toFloat(), tileY.toFloat())
            }
        }.flatten().toTypedArray()
        val enemies = (1..1).map {
            val enemy = Enemy(1700f, 400f)
            val enemyAi = enemy.getComponent<EnemyAI>()!!

            enemyAi.target = player
            enemy
        }.toTypedArray()


        // player needs to be added last (render layer principal)
        addEntities(*tiles, *enemies, player)
    }

    override fun onDisable() {

    }

    override fun onRender(g: Graphics) {
        g.font = FONT
        g.color = Color.WHITE
        g.drawString("Arrow keys to move", 0, 30)
        g.drawString("Space to attack", 0, 75)
        g.drawString("W to take damage", 0, 125)

        val state = player.getComponent<State>()!!

        if (state.state == EntityState.DYING) {
            val text = "You died!"

            g.color = Color.RED
            g.drawString(
                text,
                (Anima.frame.width / 2) - (g.fontMetrics.stringWidth(text) / 2),
                (Anima.frame.height / 2) - (g.fontMetrics.height / 2)
            )

        }
    }

    override fun onUpdate(deltaTime: Long) {
        val state = player.getComponent<State>()!!

        if (state.state == EntityState.DEAD) {
            exitProcess(0)
        }
    }
}