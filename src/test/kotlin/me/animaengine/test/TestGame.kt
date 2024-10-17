package me.animaengine.test

import me.animaengine.Anima
import me.animaengine.service.SceneService
import me.animaengine.test.scene.TestScene
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["me.animaengine", "me.animaengine.test"])
open class TestGame(
    sceneService: SceneService,
    testScene: TestScene
) {
    init {
        // After running Anima, all classes annotated as a Component (See Spring docs) will be initialized
        // During initialization we set the active scene
        sceneService.currentScene = testScene
    }
}

fun main() {
    // Here we actually run Anima
    Anima.run(TestGame::class.java, "testgame")
}