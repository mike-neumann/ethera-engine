package me.animaengine.test

import me.animaengine.Anima
import me.animaengine.service.SceneService
import me.animaengine.test.scene.ExampleScene
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["me.animaengine", "me.animaengine.test"])
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
    Anima.run(me.animaengine.test.ExampleGame::class.java, "testgame")
}