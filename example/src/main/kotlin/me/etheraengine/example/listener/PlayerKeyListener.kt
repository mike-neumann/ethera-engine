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
            KeyEvent.VK_W -> {
                val health = player.getComponent<Health>()!!

                health.health -= 1
            }

            KeyEvent.VK_SPACE -> {
                attack.isAttacking = true
            }

            KeyEvent.VK_UP -> {
                playerMovement.isMovingUp = true
            }

            KeyEvent.VK_DOWN -> {
                playerMovement.isMovingDown = true
            }

            KeyEvent.VK_LEFT -> {
                playerMovement.isMovingLeft = true
            }

            KeyEvent.VK_RIGHT -> {
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

            KeyEvent.VK_UP -> {
                playerMovement.isMovingUp = false
            }

            KeyEvent.VK_DOWN -> {
                playerMovement.isMovingDown = false
            }

            KeyEvent.VK_LEFT -> {
                playerMovement.isMovingLeft = false
            }

            KeyEvent.VK_RIGHT -> {
                playerMovement.isMovingRight = false
            }
        }
    }
}