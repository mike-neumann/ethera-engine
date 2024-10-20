package me.etheraengine.example.scene

import me.etheraengine.Ethera
import me.etheraengine.entity.component.State
import me.etheraengine.example.entity.Enemy
import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.EnemyAI
import me.etheraengine.example.listener.PlayerKeyListener
import me.etheraengine.example.system.*
import me.etheraengine.example.world.ExampleWorld
import me.etheraengine.example.world.Tile
import me.etheraengine.g2d.system.Bounds2DRenderingSystem
import me.etheraengine.scene.Scene
import me.etheraengine.service.SoundService
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import kotlin.system.exitProcess

@Component
class ExampleScene(
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
    private val entityHealthHudRenderingSystem: EntityHealthHudRenderingSystem,
    private val soundService: SoundService
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

        soundService.playSound("main_loop.wav", true)
    }

    override fun onDisable() {

    }

    override fun onRender(g: Graphics) {
        g.font = FONT
        g.color = Color.WHITE
        g.drawString("WASD or Arrow keys to move", 0, 30)
        g.drawString("Space to attack", 0, 75)
        g.drawString("Q to take damage", 0, 125)

        val state = player.getComponent<State>()!!

        if (state.state == EntityState.DYING) {
            val text = "You died!"

            g.color = Color.RED
            g.drawString(
                text,
                (Ethera.frame.width / 2) - (g.fontMetrics.stringWidth(text) / 2),
                (Ethera.frame.height / 2) - (g.fontMetrics.height / 2)
            )

        }
    }

    override fun onUpdate(deltaTime: Long) {
        val state = player.getComponent<State>()!!

        when (state.state) {
            EntityState.DYING -> {
                soundService.stopSound("main_loop.wav")

                if (!soundService.isPlaying("game_over.wav")) {
                    soundService.playSound("game_over.wav", false)
                }
            }

            EntityState.DESPAWN -> {
                exitProcess(0)
            }
        }
    }
}