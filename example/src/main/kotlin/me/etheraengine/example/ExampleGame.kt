package me.etheraengine.example

import me.etheraengine.Ethera
import me.etheraengine.example.scene.PauseScene
import me.etheraengine.service.SceneService
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["me.etheraengine", "me.etheraengine.example"])
open class ExampleGame(
    val sceneService: SceneService
)

fun main() {
    // Here we actually run Ethera
    val exampleGame = Ethera.run<ExampleGame>("testgame")

    // After running Ethera, all classes annotated as a Component (See Spring docs) will be initialized
    // During initialization we set the active scene
    // TODO: uncomment this
    // exampleGame.sceneService.switchScene<ExampleScene>()

    // TODO: remove this
    exampleGame.sceneService.switchScene<PauseScene>()
}