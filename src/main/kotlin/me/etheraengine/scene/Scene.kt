package me.etheraengine.scene

import jakarta.annotation.PostConstruct
import me.etheraengine.entity.Cursor
import me.etheraengine.entity.Entity
import me.etheraengine.g2d.system.Animation2DRenderingSystem
import me.etheraengine.g2d.system.Sprite2DRenderingSystem
import me.etheraengine.logger
import me.etheraengine.system.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.awt.Graphics
import java.awt.event.*
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Basic scene, used to register entities which are handled by registered systems in the ECS pattern
 */
@Component
abstract class Scene {
    private val log = logger<Scene>()

    var isInitialized = false
        set(value) {
            field = value

            when (field) {
                true -> onInitialize()
                false -> cleanup()
            }
        }

    @Autowired
    lateinit var cursor: Cursor

    @Autowired
    lateinit var uiFocusLogicSystem: UIFocusLogicSystem

    @Autowired
    lateinit var uiEventLogicSystem: UIEventLogicSystem

    @Autowired
    lateinit var uiSliderValueLogicSystem: UISliderValueLogicSystem

    @Autowired
    lateinit var sprite2DRenderingSystem: Sprite2DRenderingSystem

    @Autowired
    lateinit var animation2DRenderingSystem: Animation2DRenderingSystem

    private val renderingSystems = ConcurrentLinkedQueue<RenderingSystem>()
    private val logicSystems = ConcurrentLinkedQueue<LogicSystem>()

    private val entities = ConcurrentLinkedQueue<Entity>()

    private val keyListeners = ConcurrentLinkedQueue<KeyListener>()
    private val mouseListeners = ConcurrentLinkedQueue<MouseListener>()
    private val mouseWheelListeners = ConcurrentLinkedQueue<MouseWheelListener>()
    private val mouseMotionListeners = ConcurrentLinkedQueue<MouseMotionListener>()
    private val focusListeners = ConcurrentLinkedQueue<FocusListener>()

    @PostConstruct
    fun init() {
        addKeyListeners(uiFocusLogicSystem, uiEventLogicSystem)
        addMouseMotionListeners(uiEventLogicSystem)
        addMouseListeners(uiEventLogicSystem)

        addLogicSystems(
            uiFocusLogicSystem,
            uiEventLogicSystem,
            uiSliderValueLogicSystem
        )

        // register default rendering systems (developers can later remove them if necessary)
        addRenderingSystems(
            sprite2DRenderingSystem,
            animation2DRenderingSystem
        )

        addEntities(cursor)
    }

    /**
     * Called when this scene is registered as a bean on spring level
     */
    abstract fun onInitialize()
    abstract fun onEnable()
    abstract fun onDisable()
    abstract fun onRender(entities: ConcurrentLinkedQueue<Entity>, g: Graphics)
    abstract fun onUpdate(entities: ConcurrentLinkedQueue<Entity>, deltaTime: Long)

    /**
     * Unregisters all known systems and reinitializes them
     */
    fun reInitialize() {
        isInitialized = false
        init()
        isInitialized = true
    }

    fun cleanup() {
        renderingSystems.clear()
        logicSystems.clear()
        entities.clear()
        keyListeners.clear()
        mouseListeners.clear()
        mouseWheelListeners.clear()
        mouseMotionListeners.clear()
        focusListeners.clear()
    }

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

    fun addMouseMotionListeners(vararg mouseMotionListeners: MouseMotionListener) {
        log.info("Registering {} mouse motion listeners", mouseMotionListeners.size)
        log.debug("{}", mouseMotionListeners)
        this.mouseMotionListeners.addAll(mouseMotionListeners)
    }

    fun removeMouseMotionListeners(vararg mouseMotionListeners: MouseMotionListener) {
        log.info("Unregistering {} mouse motion listeners", mouseMotionListeners.size)
        log.debug("{}", mouseMotionListeners)
        this.mouseMotionListeners.removeAll(mouseMotionListeners.toSet())
    }

    fun render(g: Graphics, deltaTime: Long) {
        onRender(entities, g)
        renderingSystems.toSet()
            .forEach {
                it.render(this, entities, g, System.currentTimeMillis(), deltaTime)
            }
    }

    fun update(deltaTime: Long) {
        onUpdate(entities, deltaTime)
        logicSystems.toSet()
            .forEach {
                it.update(this, entities, System.currentTimeMillis(), deltaTime)
            }
    }

    fun keyTyped(e: KeyEvent) {
        keyListeners.toSet()
            .forEach {
                it.keyTyped(e)
            }
    }

    fun keyPressed(e: KeyEvent) {
        keyListeners.toSet()
            .forEach {
                it.keyPressed(e)
            }
    }

    fun keyReleased(e: KeyEvent) {
        keyListeners.toSet()
            .forEach {
                it.keyReleased(e)
            }
    }

    fun mouseClicked(e: MouseEvent) {
        mouseListeners.toSet()
            .forEach {
                it.mouseClicked(e)
            }
    }

    fun mousePressed(e: MouseEvent) {
        mouseListeners.toSet()
            .forEach {
                it.mousePressed(e)
            }
    }

    fun mouseReleased(e: MouseEvent) {
        mouseListeners.toSet()
            .forEach {
                it.mouseReleased(e)
            }
    }

    fun mouseEntered(e: MouseEvent) {
        mouseListeners.toSet()
            .forEach {
                it.mouseEntered(e)
            }
    }

    fun mouseExited(e: MouseEvent) {
        mouseListeners.toSet()
            .forEach {
                it.mouseExited(e)
            }
    }

    fun mouseWheelMoved(e: MouseWheelEvent) {
        mouseWheelListeners.toSet()
            .forEach {
                it.mouseWheelMoved(e)
            }
    }

    fun mouseDragged(e: MouseEvent) {
        mouseMotionListeners.toSet()
            .forEach {
                it.mouseDragged(e)
            }
    }

    fun mouseMoved(e: MouseEvent) {
        mouseMotionListeners.toSet()
            .forEach {
                it.mouseMoved(e)
            }
    }

    fun focusGained(e: FocusEvent) {
        focusListeners.toSet()
            .forEach {
                it.focusGained(e)
            }
    }

    fun focusLost(e: FocusEvent) {
        focusListeners.toSet()
            .forEach {
                it.focusLost(e)
            }
    }
}