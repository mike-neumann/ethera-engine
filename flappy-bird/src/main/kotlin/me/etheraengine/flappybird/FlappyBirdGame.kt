package me.etheraengine.flappybird

import me.etheraengine.flappybird.scene.IngameScene
import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.service.SceneService
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["me.etheraengine"])
open class FlappyBirdGame(val sceneService: SceneService)

fun main() {
    val flappyBirdGame = Ethera.run<FlappyBirdGame>("Flappy Bird")
    flappyBirdGame.sceneService.switchScene<IngameScene>()
}
