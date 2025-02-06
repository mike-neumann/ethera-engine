package me.etheraengine.example

import me.etheraengine.example.scene.ExampleScene
import me.etheraengine.runtime.Ethera
import me.etheraengine.runtime.service.SceneService
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["me.etheraengine"])
open class ExampleGame(val sceneService: SceneService)

fun main() {
    // Here we actually run Ethera
    val exampleGame = Ethera.run<ExampleGame>("testgame")
    // After running Ethera, all classes annotated as a Component (See Spring docs) will be initialized
    // During initialization we set the active scene
    exampleGame.sceneService.switchScene<ExampleScene>()
}