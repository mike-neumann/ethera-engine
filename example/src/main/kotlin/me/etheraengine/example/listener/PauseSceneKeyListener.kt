package me.etheraengine.example.listener

import me.etheraengine.example.scene.ExampleScene
import me.etheraengine.runtime.service.SceneService
import org.springframework.stereotype.Component
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

@Component
class PauseSceneKeyListener(private val sceneService: SceneService) : KeyAdapter() {
    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_ESCAPE -> sceneService.switchScene<ExampleScene>()
        }
    }
}