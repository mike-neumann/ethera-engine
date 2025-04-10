package me.etheraengine.example.scene

import me.etheraengine.example.listener.PauseSceneKeyListener
import me.etheraengine.example.system.UIRenderingSystem
import me.etheraengine.runtime.entity.UIButton
import me.etheraengine.runtime.entity.UILabel
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.service.SceneService
import me.etheraengine.runtime.service.SoundService
import org.springframework.stereotype.Component
import java.awt.*
import kotlin.system.exitProcess

@Component
class PauseScene(
    private val sceneService: SceneService,
    private val pauseSceneKeyListener: PauseSceneKeyListener,
    private val uiRenderingSystem: UIRenderingSystem,
    private val soundService: SoundService,
) : Scene() {
    override fun onInitialize() {
        addKeyListeners(pauseSceneKeyListener)
        addRenderingSystems(uiRenderingSystem)
        val xRenderOffset = 100.0
        val elements = arrayOf(
            UILabel(xRenderOffset, 100.0, "PAUSE", 100f, Color.BLACK, Font.BOLD),
            UIButton(xRenderOffset, 400.0, 150, 50, "RESUME", 30f, Color.BLACK, Font.PLAIN, offClick = {
                sceneService.switchScene<ExampleScene>()
            }),
            UIButton(xRenderOffset, 460.0, 150, 50, "SETTINGS", 30f, Color.BLACK, Font.PLAIN, offClick = {
                soundService.playSound("select.wav")
                sceneService.switchScene<SettingsScene>()
            }),
            UIButton(xRenderOffset, 520.0, 150, 50, "QUIT", 30f, Color.BLACK, Font.PLAIN, offClick = {
                soundService.stopSound("main_loop.wav")
                soundService.playSound("shutdown.wav", isBlocking = true)
                exitProcess(0)
            })
        )

        addEntities(*elements)
    }

    override fun onEnable() {}
    override fun onDisable() {}
    override fun onRender(g: Graphics2D, now: Long, deltaTime: Long) {}
    override fun onUpdate(now: Long, deltaTime: Long) {}
}