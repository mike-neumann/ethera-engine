package me.etheraengine.example

import me.etheraengine.Ethera
import me.etheraengine.service.SceneService
import me.etheraengine.example.scene.ExampleScene
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["me.etheraengine", "me.etheraengine.example"])
open class ExampleGame(
    sceneService: SceneService,
    exampleScene: ExampleScene
) {
    init {
        // After running Ethera, all classes annotated as a Component (See Spring docs) will be initialized
        // During initialization we set the active scene
        sceneService.currentScene = exampleScene
    }
}

fun main() {
    // Here we actually run Ethera
    Ethera.run(ExampleGame::class.java, "testgame")
}