package me.etheraengine.example.listener

import me.etheraengine.example.entity.Player
import me.etheraengine.example.entity.component.Attack
import me.etheraengine.example.entity.component.Health
import me.etheraengine.example.entity.component.PlayerMovement
import org.springframework.stereotype.Component
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

@Component
class PlayerKeyListener(
    private val player: Player
) : KeyListener {
    override fun keyTyped(e: KeyEvent) {

    }

    override fun keyPressed(e: KeyEvent) {
        val attack = player.getComponent<Attack>()!!
        val playerMovement = player.getComponent<PlayerMovement>()!!

        when (e.keyCode) {
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
}