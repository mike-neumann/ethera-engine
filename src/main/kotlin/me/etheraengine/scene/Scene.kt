package me.etheraengine.scene

import jakarta.annotation.PostConstruct
import me.etheraengine.Ethera
import me.etheraengine.entity.Entity
import me.etheraengine.g2d.system.Animation2DRenderingSystem
import me.etheraengine.g2d.system.Sprite2DRenderingSystem
import me.etheraengine.logger
import me.etheraengine.system.LogicSystem
import me.etheraengine.system.RenderingSystem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.awt.Graphics
import java.awt.event.*

/**
 * Basic scene, used to register entities which are handled by registered systems
 */
@Component
abstract class Scene {
    private val log = logger<Scene>()

    @Autowired
    private lateinit var sprite2DRenderingSystem: Sprite2DRenderingSystem

    @Autowired
    private lateinit var animation2DRenderingSystem: Animation2DRenderingSystem

    private val renderingSystems = mutableListOf<RenderingSystem>()
    private val logicSystems = mutableListOf<LogicSystem>()

    private val entities = mutableListOf<Entity>()

    private val keyListeners = mutableListOf<KeyListener>()
    private val mouseListeners = mutableListOf<MouseListener>()
    private val mouseWheelListeners = mutableListOf<MouseWheelListener>()
    private val mouseMotionListeners = mutableListOf<MouseMotionListener>()
    private val focusListeners = mutableListOf<FocusListener>()

    @PostConstruct
    fun init() {
        // register default rendering systems (developers can later remove them if necessary)
        addRenderingSystems(
            sprite2DRenderingSystem,
            animation2DRenderingSystem
        )
    }

    abstract fun onEnable()
    abstract fun onDisable()
    abstract fun onRender(g: Graphics)
    abstract fun onUpdate(deltaTime: Long)

    fun addRenderingSystems(vararg renderingSystems: RenderingSystem) {
        log.info("Registering {} rendering systems", renderingSystems.size)
        log.debug("{}", renderingSystems)
        this.renderingSystems.addAll(renderingSystems)
    }

    fun removeRenderingSystems(vararg renderingSystems: RenderingSystem) {
        log.info("Unregistering {} rendering systems", renderingSystems.size)
        log.debug("{}", renderingSystems)
        this.renderingSystems.removeAll(renderingSystems.toSet())
    }

    fun addLogicSystems(vararg logicSystems: LogicSystem) {
        log.info("Registering {} logic systems", logicSystems.size)
        log.debug("{}", logicSystems)
        this.logicSystems.addAll(logicSystems)
    }

    fun removeLogicSystems(vararg logicSystems: LogicSystem) {
        log.info("Unregistering {} logic systems", logicSystems.size)
        log.debug("{}", logicSystems)
        this.logicSystems.removeAll(logicSystems.toSet())
    }

    fun addEntities(vararg entities: Entity) {
        log.info("Registering {} entities", entities.size)
        log.debug("{}", entities)
        this.entities.addAll(entities)
    }

    fun removeEntities(vararg entities: Entity) {
        log.info("Unregistering {} entities", entities.size)
        log.debug("{}", entities)
        this.entities.removeAll(entities.toSet())
    }

    fun addKeyListeners(vararg keyListeners: KeyListener) {
        log.info("Registering {} key listeners", keyListeners.size)
        log.debug("{}", keyListeners)
        this.keyListeners.addAll(keyListeners)
    }

    fun removeKeyListeners(vararg keyListeners: KeyListener) {
        log.info("Unregistering {} key listeners", keyListeners.size)
        log.debug("{}", keyListeners)
        this.keyListeners.removeAll(keyListeners.toSet())
    }

    fun addMouseListeners(vararg mouseListeners: MouseListener) {
        log.info("Registering {} mouse listeners", mouseListeners.size)
        log.debug("{}", mouseListeners)
        this.mouseListeners.addAll(mouseListeners)
    }

    fun removeMouseListeners(vararg mouseListeners: MouseListener) {
        log.info("Unregistering {} mouse listeners", mouseListeners.size)
        log.debug("{}", mouseListeners)
        this.mouseListeners.removeAll(mouseListeners.toSet())
    }

    fun render(g: Graphics, deltaTime: Long) {
        renderingSystems.forEach {
            it.render(this, entities, g, System.currentTimeMillis(), deltaTime)
        }
        onRender(g)
    }

    fun update(deltaTime: Long) {
        logicSystems.forEach {
            it.update(this, entities, System.currentTimeMillis(), deltaTime)
        }
        onUpdate(deltaTime)
    }

    fun keyTyped(e: KeyEvent) {
        keyListeners.forEach {
            it.keyTyped(e)
        }
    }

    fun keyPressed(e: KeyEvent) {
        keyListeners.forEach {
            it.keyPressed(e)
        }
    }

    fun keyReleased(e: KeyEvent) {
        keyListeners.forEach {
            it.keyReleased(e)
        }
    }

    fun mouseClicked(e: MouseEvent) {
        mouseListeners.forEach {
            it.mouseClicked(e)
        }
    }

    fun mousePressed(e: MouseEvent) {
        mouseListeners.forEach {
            it.mousePressed(e)
        }
    }

    fun mouseReleased(e: MouseEvent) {
        mouseListeners.forEach {
            it.mouseReleased(e)
        }
    }

    fun mouseEntered(e: MouseEvent) {
        mouseListeners.forEach {
            it.mouseEntered(e)
        }
    }

    fun mouseExited(e: MouseEvent) {
        mouseListeners.forEach {
            it.mouseExited(e)
        }
    }

    fun mouseWheelMoved(e: MouseWheelEvent) {
        mouseWheelListeners.forEach {
            it.mouseWheelMoved(e)
        }
    }

    fun mouseDragged(e: MouseEvent) {
        mouseMotionListeners.forEach {
            it.mouseDragged(e)
        }
    }

    fun mouseMoved(e: MouseEvent) {
        mouseMotionListeners.forEach {
            it.mouseMoved(e)
        }
    }

    fun focusGained(e: FocusEvent) {
        focusListeners.forEach {
            it.focusGained(e)
        }
    }

    fun focusLost(e: FocusEvent) {
        focusListeners.forEach {
            it.focusLost(e)
        }
    }
}