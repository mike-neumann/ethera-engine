package me.etheraengine.example.scene

import me.etheraengine.example.entity.*
import me.etheraengine.example.entity.component.EnemyAI
import me.etheraengine.example.listener.ExampleSceneKeyListener
import me.etheraengine.example.system.*
import me.etheraengine.example.world.ExampleWorld
import me.etheraengine.example.world.Tile
import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.entity.UILabel
import me.etheraengine.runtime.entity.component.StateHolder
import me.etheraengine.runtime.g2d.system.Bounds2DRenderingSystem
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.service.SoundService
import org.springframework.stereotype.Component
import java.awt.*
import java.util.*
import kotlin.system.exitProcess

@Component
class ExampleScene(
    private val player: Player,
    private val exampleSceneKeyListener: ExampleSceneKeyListener,
    private val entityStateLogicSystem: EntityStateLogicSystem,
    private val entityAnimationLogicSystem: EntityAnimationLogicSystem,
    private val entityAttackLogicSystem: EntityAttackLogicSystem,
    private val entityPositionMovementLogicSystem: EntityPositionMovementLogicSystem,
    private val playerMovementLogicSystem: PlayerMovementLogicSystem,
    private val entityWorldCollisionLogicSystem: EntityWorldCollisionLogicSystem,
    private val enemyAIMovementLogicSystem: EnemyAIMovementLogicSystem,
    private val enemyAIAttackLogicSystem: EnemyAIAttackLogicSystem,
    private val entityCollisionLogicSystem: EntityCollisionLogicSystem,
    private val entityHealthHudRenderingSystem: EntityHealthHudRenderingSystem,
    private val uiRenderingSystem: UIRenderingSystem,
    private val soundService: SoundService,
    private val bounds2DRenderingSystem: Bounds2DRenderingSystem,
) : Scene() {
    override fun onInitialize() {
        addKeyListeners(exampleSceneKeyListener)
        // Uncomment bounds2DRenderingSystem, if you want to see each entity's bounds / hitbox
        addRenderingSystems(/* bounds2DRenderingSystem , */entityHealthHudRenderingSystem, uiRenderingSystem)
        addLogicSystems(
            entityAttackLogicSystem,
            entityPositionMovementLogicSystem,
            playerMovementLogicSystem,
            entityStateLogicSystem,
            entityWorldCollisionLogicSystem,
            enemyAIMovementLogicSystem,
            enemyAIAttackLogicSystem,
            entityCollisionLogicSystem,
            entityAnimationLogicSystem
        )
        val tiles = ExampleWorld.tileTypeMask.mapIndexed { yIndex, tileTypes ->
            tileTypes.mapIndexed { xIndex, tileType ->
                val tileX = xIndex * ExampleWorld.tileSize
                val tileY = yIndex * ExampleWorld.tileSize

                Tile(tileType, tileX.toDouble(), tileY.toDouble())
            }
        }.flatten().toTypedArray()
        val enemies = (1..1).map {
            val enemy = Enemy(1600.0 + (Random().nextInt(100, 400)), 400.0)
            val enemyAi = enemy.getComponent<EnemyAI>()!!

            enemyAi.target = player
            enemy
        }.toTypedArray()
        val hud = arrayOf(
            UILabel(100.0, 40.0, "WASD or Arrow keys to move", 40f, Color.WHITE, Font.PLAIN),
            UILabel(100.0, 85.0, "Space to attack", 40f, Color.WHITE, Font.PLAIN),
            UILabel(100.0, 135.0, "Q to take damage", 40f, Color.WHITE, Font.PLAIN),
            UILabel(100.0, 185.0, "ESC to pause", 40f, Color.WHITE, Font.PLAIN)
        )
        // player needs to be added last (render layer principal)
        addEntities(*tiles, *enemies, player, *hud)
        soundService.playSound("main_loop.wav", loop = true)
    }

    override fun onEnable() = soundService.playSound("unpause.wav")
    override fun onDisable() = soundService.playSound("pause.wav")

    override fun onUpdate(now: Long, deltaTime: Long) {
        val stateHolder = player.getComponent<StateHolder>()!!

        when (stateHolder.state) {
            EntityState.DYING -> {
                soundService.stopSound("main_loop.wav")

                if (!soundService.isPlaying("game_over.wav")) {
                    soundService.playSound("game_over.wav")
                }
            }

            EntityState.DESPAWN -> exitProcess(0)
        }

        camera2D.x = player.x
        camera2D.y = player.y
        // set camera offset so that the player is always in the middle
        camera2D.offsetX = (Ethera.frame.width / 2.0) - player.width / 2
        camera2D.offsetY = (Ethera.frame.height / 2.0) - player.height / 2
    }
}