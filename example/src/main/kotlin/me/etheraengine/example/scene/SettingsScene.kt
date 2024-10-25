package me.etheraengine.example.scene

import me.etheraengine.entity.Entity
import me.etheraengine.entity.UIButton
import me.etheraengine.entity.UILabel
import me.etheraengine.entity.UISlider
import me.etheraengine.entity.component.UIClickable
import me.etheraengine.entity.component.UIDraggable
import me.etheraengine.entity.component.UIHoverable
import me.etheraengine.example.system.UIRenderingSystem
import me.etheraengine.scene.Scene
import me.etheraengine.service.SceneService
import me.etheraengine.service.SoundService
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Font
import java.awt.Graphics

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
            20f,
            Color.BLACK,
            Font.PLAIN,
            100.0
        ).apply {
            addComponents(
                UIHoverable(),
                UIDraggable(
                    offDrag = { _, _, _, _, _ ->
                        soundService.playSound("select.wav")
                    }
                )
            )
        }

        val applyButton = UIButton(
            xRenderOffset,
            460.0,
            150,
            50,
            "APPLY",
            20f,
            Color.BLACK,
            Font.PLAIN
        ).apply {
            addComponents(
                UIHoverable(),
                UIClickable(
                    offClick = {
                        apply()
                        soundService.playSound("select.wav")
                    }
                )
            )
        }

        val backButton = UIButton(
            xRenderOffset,
            520.0,
            150,
            50,
            "BACK",
            20f,
            Color.BLACK,
            Font.PLAIN
        ).apply {
            addComponents(
                UIHoverable(),
                UIClickable(
                    offClick = {
                        soundService.playSound("select.wav")
                        sceneService.switchToPreviousScene()
                    }
                )
            )
        }

        addEntities(
            settingsLabel,
            soundSlider,
            applyButton,
            backButton
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