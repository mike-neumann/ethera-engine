package me.etheraengine.runtime

import me.etheraengine.runtime.service.SceneService
import org.springframework.stereotype.Component
import java.awt.*
import java.awt.event.*
import javax.swing.JPanel

/**
 * The actual window which is rendered and shown to the user, handles basic rendering and events, and passes them to the currently active scene for further processing
 */
@Component
class Screen(val sceneService: SceneService) : JPanel(), FocusListener, KeyListener, MouseMotionListener, MouseListener,
    MouseWheelListener {
    private var lastFrameTime = 0L

    init {
        isDoubleBuffered = true
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val now = System.currentTimeMillis()

        sceneService.render(g as Graphics2D, now, now - lastFrameTime)

        Toolkit.getDefaultToolkit().sync()
        lastFrameTime = System.currentTimeMillis()
    }

    override fun keyTyped(e: KeyEvent): Unit = run { sceneService.currentScene?.typeKey(e) }
    override fun keyPressed(e: KeyEvent): Unit = run { sceneService.currentScene?.pressKey(e) }
    override fun keyReleased(e: KeyEvent): Unit = run { sceneService.currentScene?.releaseKey(e) }
    override fun mouseClicked(e: MouseEvent): Unit = run { sceneService.currentScene?.clickMouse(e) }
    override fun mousePressed(e: MouseEvent): Unit = run { sceneService.currentScene?.pressMouse(e) }
    override fun mouseReleased(e: MouseEvent): Unit = run { sceneService.currentScene?.releaseMouse(e) }
    override fun mouseEntered(e: MouseEvent): Unit = run { sceneService.currentScene?.enterMouse(e) }
    override fun mouseExited(e: MouseEvent): Unit = run { sceneService.currentScene?.exitMouse(e) }
    override fun mouseWheelMoved(e: MouseWheelEvent): Unit = run { sceneService.currentScene?.moveMouseWheel(e) }
    override fun mouseDragged(e: MouseEvent): Unit = run { sceneService.currentScene?.dragMouse(e) }
    override fun mouseMoved(e: MouseEvent): Unit = run { sceneService.currentScene?.moveMouse(e) }
    override fun focusGained(e: FocusEvent): Unit = run { sceneService.currentScene?.gainFocus(e) }
    override fun focusLost(e: FocusEvent): Unit = run { sceneService.currentScene?.loseFocus(e) }
}