package me.etheraengine.flappybird.scene

import me.etheraengine.flappybird.entity.Pipe
import me.etheraengine.flappybird.entity.Player
import me.etheraengine.flappybird.listener.PlayerKeyListener
import me.etheraengine.flappybird.service.FlappyConfigurationService
import me.etheraengine.flappybird.system.*
import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.entity.component.State
import me.etheraengine.runtime.scene.Scene
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class IngameScene(
    private val flappyConfigurationService: FlappyConfigurationService,
    private val player: Player,
    private val obstacleCollisionSystem: ObstacleCollisionSystem,
    private val playerStateLogicSystem: PlayerStateLogicSystem,
    private val playerMovementSystem: PlayerMovementSystem,
    private val playerKeyListener: PlayerKeyListener,
) : Scene() {
    override fun onInitialize() {
        player.y = (Ethera.frame.height / 2).toDouble()
        camera2D.y = player.y

        addKeyListeners(playerKeyListener)
        addLogicSystems(playerMovementSystem, playerStateLogicSystem, obstacleCollisionSystem)
        addEntities(player)
    }

    @Scheduled(initialDelay = 1_500, fixedRate = 1_500)
    fun generatePipes() {
        val playerState = player.getComponent<State>()!!

        if (playerState.state == Player.State.DEAD) return
        val newPipeX = player.x + (Ethera.frame.width.toDouble() / 2)
        val randomPipeCenterY = (200..(Ethera.frame.height - 350)).random()
        // top pipe
        val newTopPipeY = randomPipeCenterY - (flappyConfigurationService.pipeGap / 2)
        val topPipe = Pipe(Pipe.Type.FILL, newPipeX, 0.0, 100, newTopPipeY)
        val topPipePart = Pipe(Pipe.Type.PART, newPipeX - 5, newTopPipeY.toDouble() - 50, 110, 50)
        // bottom pipe
        val bottomPipeY = randomPipeCenterY + (flappyConfigurationService.pipeGap / 2)
        val bottomPipe = Pipe(Pipe.Type.FILL, newPipeX, bottomPipeY.toDouble(), 100, Ethera.frame.height)
        val bottomPipePart = Pipe(Pipe.Type.PART, newPipeX - 5, bottomPipeY.toDouble(), 110, 50)

        addEntities(topPipe, topPipePart, bottomPipe, bottomPipePart)
    }

    override fun onUpdate(now: Long, deltaTime: Long) {
        val playerState = player.getComponent<State>()!!

        if (playerState.state == Player.State.DEAD) return

        player.x += 2.5
        camera2D.x = player.x
        // fix camera x offset to center player
        camera2D.offsetX = ((Ethera.frame.width / 2) - player.width / 2).toDouble()
        // fix camera y to screen
        camera2D.offsetY = camera2D.y
        // mark every off-camera entity for removal
        getFilteredEntities { it != player && !camera2D.canSee(it) }.forEach { it.markedForRemoval = true }
    }
}