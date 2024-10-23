package me.etheraengine.example.scene

import me.etheraengine.entity.Entity
import me.etheraengine.entity.Label
import me.etheraengine.entity.component.State
import me.etheraengine.example.entity.Enemy
import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.EnemyAI
import me.etheraengine.example.listener.ExampleSceneKeyListener
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

    private val exampleSceneKeyListener: ExampleSceneKeyListener,

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
    private val uiRenderingSystem: UIRenderingSystem,
    private val soundService: SoundService
) : Scene() {
    override fun onInitialize() {
        addKeyListeners(exampleSceneKeyListener)
        // Uncomment bounds2DRenderingSystem, if you want to see each entity's bounds / hitbox
        addRenderingSystems(bounds2DRenderingSystem, entityHealthHudRenderingSystem, uiRenderingSystem)
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

                Tile(tileType, tileX.toDouble(), tileY.toDouble())
            }
        }.flatten().toTypedArray()
        val enemies = (1..1).map {
            val enemy = Enemy(1700.0, 400.0)
            val enemyAi = enemy.getComponent<EnemyAI>()!!

            enemyAi.target = player
            enemy
        }.toTypedArray()
        val labels = listOf(
            Label(
                0.0,
                40.0,
                "WASD or Arrow keys to move",
                40f,
                Color.WHITE,
                Font.PLAIN
            ),
            Label(
                0.0,
                85.0,
                "Space to attack",
                40f,
                Color.WHITE,
                Font.PLAIN
            ),
            Label(
                0.0,
                135.0,
                "Q to take damage",
                40f,
                Color.WHITE,
                Font.PLAIN
            ),
            Label(
                0.0,
                185.0,
                "ESC to pause",
                40f,
                Color.WHITE,
                Font.PLAIN
            )
        ).toTypedArray()

        // player needs to be added last (render layer principal)
        addEntities(*tiles, *enemies, player, *labels)

        soundService.playSound("main_loop.wav", loop = true)
    }

    override fun onRender(entities: List<Entity>, g: Graphics) {

    }

    override fun onEnable() {
        soundService.playSound("unpause.wav")
    }

    override fun onDisable() {
        soundService.playSound("pause.wav")
    }

    override fun onUpdate(entities: List<Entity>, deltaTime: Long) {
        val state = player.getComponent<State>()!!

        when (state.state) {
            EntityState.DYING -> {
                soundService.stopSound("main_loop.wav")

                if (!soundService.isPlaying("game_over.wav")) {
                    soundService.playSound("game_over.wav")
                }
            }

            EntityState.DESPAWN -> {
                exitProcess(0)
            }
        }
    }
}