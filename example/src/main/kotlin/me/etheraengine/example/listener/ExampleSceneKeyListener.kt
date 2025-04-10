package me.etheraengine.example.listener

import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.*
import me.etheraengine.example.scene.PauseScene
import me.etheraengine.runtime.service.SceneService
import org.springframework.stereotype.Component
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

@Component
class ExampleSceneKeyListener(private val player: Player, private val sceneService: SceneService) : KeyAdapter() {
    override fun keyPressed(e: KeyEvent) {
        val attackHolder = player.getComponent<AttackHolder>()!!
        val playerMovement = player.getComponent<PlayerMovement>()!!

        when (e.keyCode) {
            KeyEvent.VK_ESCAPE -> sceneService.switchScene<PauseScene>()
            KeyEvent.VK_Q -> player.getComponent<Health>()!!.health -= 1
            KeyEvent.VK_SPACE -> attackHolder.isAttacking = true
            KeyEvent.VK_UP, KeyEvent.VK_W -> playerMovement.isMovingUp = true
            KeyEvent.VK_DOWN, KeyEvent.VK_S -> playerMovement.isMovingDown = true
            KeyEvent.VK_LEFT, KeyEvent.VK_A -> playerMovement.isMovingLeft = true
            KeyEvent.VK_RIGHT, KeyEvent.VK_D -> playerMovement.isMovingRight = true
        }
    }

    override fun keyReleased(e: KeyEvent) {
        val attackHolder = player.getComponent<AttackHolder>()!!
        val playerMovement = player.getComponent<PlayerMovement>()!!

        when (e.keyCode) {
            KeyEvent.VK_SPACE -> attackHolder.isAttacking = false
            KeyEvent.VK_UP, KeyEvent.VK_W -> playerMovement.isMovingUp = false
            KeyEvent.VK_DOWN, KeyEvent.VK_S -> playerMovement.isMovingDown = false
            KeyEvent.VK_LEFT, KeyEvent.VK_A -> playerMovement.isMovingLeft = false
            KeyEvent.VK_RIGHT, KeyEvent.VK_D -> playerMovement.isMovingRight = false
        }
    }
}