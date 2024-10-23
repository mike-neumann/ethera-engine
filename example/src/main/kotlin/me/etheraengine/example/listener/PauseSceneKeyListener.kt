package me.etheraengine.example.listener

import me.etheraengine.example.scene.ExampleScene
import me.etheraengine.service.SceneService
import org.springframework.stereotype.Component
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

@Component
class PauseSceneKeyListener(
    private val sceneService: SceneService
) : KeyListener {
    override fun keyTyped(e: KeyEvent?) {

    }

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_ESCAPE -> {
                sceneService.switchScene<ExampleScene>()
            }
        }
    }

    override fun keyReleased(e: KeyEvent?) {

    }
}