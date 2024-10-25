package me.etheraengine.example.scene

import me.etheraengine.entity.Entity
import me.etheraengine.entity.UIButton
import me.etheraengine.entity.UILabel
import me.etheraengine.entity.component.UIClickable
import me.etheraengine.entity.component.UIHoverable
import me.etheraengine.example.listener.PauseSceneKeyListener
import me.etheraengine.example.system.UIRenderingSystem
import me.etheraengine.scene.Scene
import me.etheraengine.service.SceneService
import me.etheraengine.service.SoundService
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import kotlin.system.exitProcess

@Component
class PauseScene(
    private val sceneService: SceneService,

    private val pauseSceneKeyListener: PauseSceneKeyListener,

    private val uiRenderingSystem: UIRenderingSystem,
    private val soundService: SoundService
) : Scene() {
    override fun onInitialize() {
        addKeyListeners(pauseSceneKeyListener)
        addRenderingSystems(uiRenderingSystem)

        val xRenderOffset = 100.0
        val pauseLabel = UILabel(
            xRenderOffset,
            100.0,
            "PAUSE",
            100f,
            Color.BLACK,
            Font.BOLD
        )
        val resumeButton = UIButton(
            xRenderOffset,
            400.0,
            150,
            50,
            "RESUME",
            20f,
            Color.BLACK,
            Font.PLAIN
        ).apply {
            addComponents(
                UIHoverable(),
                UIClickable(
                    offClick = {
                        sceneService.switchScene<ExampleScene>()
                    }
                )
            )
        }
        val settingsButton = UIButton(
            xRenderOffset,
            460.0,
            150,
            50,
            "SETTINGS",
            20f,
            Color.BLACK,
            Font.PLAIN
        ).apply {
            addComponents(
                UIHoverable(),
                UIClickable(
                    offClick = {
                        soundService.playSound("select.wav")
                        sceneService.switchScene<SettingsScene>()
                    }
                )
            )
        }
        val quitButton = UIButton(
            xRenderOffset,
            520.0,
            150,
            50,
            "QUIT",
            20f,
            Color.BLACK,
            Font.PLAIN
        ).apply {
            addComponents(
                UIHoverable(),
                UIClickable(
                    offClick = {
                        soundService.stopSound("main_loop.wav")
                        soundService.playSound("shutdown.wav", isBlocking = true)
                        exitProcess(0)
                    }
                )
            )
        }

        addEntities(
            pauseLabel,
            resumeButton,
            settingsButton,
            quitButton
        )
    }

    override fun onEnable() {

    }

    override fun onDisable() {

    }

    override fun onRender(entities: List<Entity>, g: Graphics) {

    }

    override fun onUpdate(entities: List<Entity>, deltaTime: Long) {

    }
}