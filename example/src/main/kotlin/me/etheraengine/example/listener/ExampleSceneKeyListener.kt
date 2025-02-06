package me.etheraengine.example.listener

import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.*
import me.etheraengine.example.scene.PauseScene
import me.etheraengine.runtime.service.SceneService
import org.springframework.stereotype.Component
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

@Component
class ExampleSceneKeyListener(private val player: Player, private val sceneService: SceneService) :
    KeyListener {
    override fun keyPressed(e: KeyEvent) {
        val attack = player.getComponent<Attack>()!!
        val playerMovement = player.getComponent<PlayerMovement>()!!

        when (e.keyCode) {
            KeyEvent.VK_ESCAPE -> {
                sceneService.switchScene<PauseScene>()
            }

            KeyEvent.VK_Q -> {
                val health = player.getComponent<Health>()!!

                health.health -= 1
            }

            KeyEvent.VK_SPACE -> {
                attack.isAttacking = true
            }

            KeyEvent.VK_UP, KeyEvent.VK_W -> {
                playerMovement.isMovingUp = true
            }

            KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                playerMovement.isMovingDown = true
            }

            KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                playerMovement.isMovingLeft = true
            }

            KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                playerMovement.isMovingRight = true
            }
        }
    }

    override fun keyReleased(e: KeyEvent) {
        val attack = player.getComponent<Attack>()!!
        val playerMovement = player.getComponent<PlayerMovement>()!!

        when (e.keyCode) {
            KeyEvent.VK_SPACE -> {
                attack.isAttacking = false
            }

            KeyEvent.VK_UP, KeyEvent.VK_W -> {
                playerMovement.isMovingUp = false
            }

            KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                playerMovement.isMovingDown = false
            }

            KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                playerMovement.isMovingLeft = false
            }

            KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                playerMovement.isMovingRight = false
            }
        }
    }

    override fun keyTyped(e: KeyEvent) {}
}