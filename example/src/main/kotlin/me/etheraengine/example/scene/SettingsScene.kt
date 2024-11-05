package me.etheraengine.example.scene

import me.etheraengine.entity.Entity
import me.etheraengine.entity.UIButton
import me.etheraengine.entity.UILabel
import me.etheraengine.entity.UISlider
import me.etheraengine.example.system.UIRenderingSystem
import me.etheraengine.scene.Scene
import me.etheraengine.service.SceneService
import me.etheraengine.service.SoundService
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.util.concurrent.ConcurrentLinkedQueue

@Component
class SettingsScene(
    private val sceneService: SceneService,

    private val uiRenderingSystem: UIRenderingSystem,
    private val soundService: SoundService
) : Scene() {
    private fun apply() {
        // TODO
    }

    override fun onInitialize() {
        addRenderingSystems(uiRenderingSystem)

        val xRenderOffset = 100.0
        val settingsLabel = UILabel(
            xRenderOffset,
            100.0,
            "SETTINGS",
            100f,
            Color.BLACK,
            Font.BOLD
        )
        val soundSlider = UISlider(
            xRenderOffset,
            400.0,
            150,
            50,
            "SOUND",
            30f,
            Color.BLACK,
            Font.PLAIN,
            100.0,
            offClick = {
                soundService.playSound("select.wav")
            },
            onChange = { _, _, newValue ->
                soundService.volume = newValue.toFloat() / 100.0f
            }
        )

        val applyButton = UIButton(
            xRenderOffset,
            460.0,
            150,
            50,
            "APPLY",
            30f,
            Color.BLACK,
            Font.PLAIN,
            offClick = {
                apply()
                soundService.playSound("select.wav")
            }
        )

        val backButton = UIButton(
            xRenderOffset,
            520.0,
            150,
            50,
            "BACK",
            30f,
            Color.BLACK,
            Font.PLAIN,
            offClick = {
                soundService.playSound("select.wav")
                sceneService.switchToPreviousScene()
            }
        )

        addEntities(
            settingsLabel,
            soundSlider,
            applyButton,
            backButton
        )
    }

    override fun onEnable() {}
    override fun onDisable() {}
    override fun onRender(entities: ConcurrentLinkedQueue<Entity>, g: Graphics) {}
    override fun onUpdate(entities: ConcurrentLinkedQueue<Entity>, deltaTime: Long) {}
}