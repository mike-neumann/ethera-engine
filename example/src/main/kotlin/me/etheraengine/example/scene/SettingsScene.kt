package me.etheraengine.example.scene

import me.etheraengine.example.system.UIRenderingSystem
import me.etheraengine.runtime.entity.*
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.service.SceneService
import me.etheraengine.runtime.service.SoundService
import org.springframework.stereotype.Component
import java.awt.*

@Component
class SettingsScene(
    private val sceneService: SceneService,
    private val uiRenderingSystem: UIRenderingSystem,
    private val soundService: SoundService,
) : Scene() {
    private fun apply() {
        // TODO
    }

    override fun onInitialize() {
        addRenderingSystems(uiRenderingSystem)
        val xRenderOffset = 100.0
        val settingsLabel = UILabel(xRenderOffset, 100.0, "SETTINGS", 100f, Color.BLACK, Font.BOLD)
        val soundSlider = UISlider(xRenderOffset, 400.0, 150, 50, "SOUND", 30f, Color.BLACK, Font.PLAIN, 100.0, offClick = {
            soundService.playSound("select.wav")
        }, onChange = { _, _, newValue -> soundService.volume = newValue.toFloat() / 100.0f })
        val testCheckbox = UICheckbox(xRenderOffset, 460.0, 25, 25, "TEST", 30f, Color.BLACK, Font.PLAIN, false, onChange = { _, _, _ ->
            soundService.playSound("select.wav")
        })
        val applyButton = UIButton(xRenderOffset, 520.0, 150, 50, "APPLY", 30f, Color.BLACK, Font.PLAIN, offClick = {
            apply()
            soundService.playSound("select.wav")
        })
        val backButton = UIButton(xRenderOffset, 580.0, 150, 50, "BACK", 30f, Color.BLACK, Font.PLAIN, offClick = {
            soundService.playSound("select.wav")
            sceneService.switchToPreviousScene()
        })

        addEntities(settingsLabel, soundSlider, testCheckbox, applyButton, backButton)
    }

    override fun onEnable() {}
    override fun onDisable() {}
    override fun onRender(g: Graphics2D, now: Long, deltaTime: Long) {}
    override fun onUpdate(now: Long, deltaTime: Long) {}
}