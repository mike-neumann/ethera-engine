package me.animaengine.example

import me.animaengine.Anima
import me.animaengine.service.SceneService
import me.animaengine.example.scene.ExampleScene
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["me.animaengine", "me.animaengine.example"])
open class ExampleGame(
    sceneService: SceneService,
    exampleScene: ExampleScene
) {
    init {
        // After running Anima, all classes annotated as a Component (See Spring docs) will be initialized
        // During initialization we set the active scene
        sceneService.currentScene = exampleScene
    }
}

fun main() {
    // Here we actually run Anima
    Anima.run(me.animaengine.example.ExampleGame::class.java, "testgame")
}