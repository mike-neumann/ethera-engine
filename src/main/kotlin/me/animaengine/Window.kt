package me.animaengine

import me.animaengine.service.SceneService
import org.springframework.stereotype.Component
import java.awt.Graphics
import java.awt.Toolkit
import java.awt.event.*
import javax.swing.JPanel

/**
 * The actual window which is rendered and shown to the user, handles basic rendering and events, and passes them to the currently active scene for further processing
 */
@Component
class Window(
    private val sceneService: SceneService
) : JPanel(), FocusListener, KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {
    init {
        isDoubleBuffered = true
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        sceneService.render(g)

        Toolkit.getDefaultToolkit().sync()
    }

    override fun keyTyped(e: KeyEvent) {
        sceneService.currentScene?.keyTyped(e)
    }

    override fun keyPressed(e: KeyEvent) {
        sceneService.currentScene?.keyPressed(e)
    }

    override fun keyReleased(e: KeyEvent) {
        sceneService.currentScene?.keyReleased(e)
    }

    override fun mouseClicked(e: MouseEvent) {
        sceneService.currentScene?.mouseClicked(e)
    }

    override fun mousePressed(e: MouseEvent) {
        sceneService.currentScene?.mousePressed(e)
    }

    override fun mouseReleased(e: MouseEvent) {
        sceneService.currentScene?.mouseReleased(e)
    }

    override fun mouseEntered(e: MouseEvent) {
        sceneService.currentScene?.mouseEntered(e)
    }

    override fun mouseExited(e: MouseEvent) {
        sceneService.currentScene?.mouseExited(e)
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        sceneService.currentScene?.mouseWheelMoved(e)
    }

    override fun mouseDragged(e: MouseEvent) {
        sceneService.currentScene?.mouseDragged(e)
    }

    override fun mouseMoved(e: MouseEvent) {
        sceneService.currentScene?.mouseMoved(e)
    }

    override fun focusGained(e: FocusEvent) {
        sceneService.currentScene?.focusGained(e)
    }

    override fun focusLost(e: FocusEvent) {
        sceneService.currentScene?.focusLost(e)
    }
}