package me.etheraengine.flappybird.listener

import me.etheraengine.flappybird.entity.Player
import me.etheraengine.flappybird.entity.component.PlayerMovement
import org.springframework.stereotype.Component
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

@Component
class PlayerKeyListener(private val player: Player) : KeyAdapter() {
    private var upKeyDown = false

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_UP -> {
                if (!upKeyDown) {
                    upKeyDown = true
                    player.getComponent<PlayerMovement>()!!.jumping = true
                }
            }
        }
    }

    override fun keyReleased(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_UP -> {
                if (upKeyDown) {
                    upKeyDown = false
                    player.getComponent<PlayerMovement>()!!.jumping = false
                }
            }
        }
    }
}