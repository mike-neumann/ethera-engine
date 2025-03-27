package me.etheraengine.runtime.scene

import jakarta.annotation.PostConstruct
import me.etheraengine.runtime.entity.Cursor
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.g2d.system.Animation2DRenderingSystem
import me.etheraengine.runtime.g2d.system.Sprite2DRenderingSystem
import me.etheraengine.runtime.g2d.world.Camera2D
import me.etheraengine.runtime.logger
import me.etheraengine.runtime.scene.Scene.EntityFilter
import me.etheraengine.runtime.system.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.awt.Graphics
import java.awt.event.*

/**
 * Basic scene, used to register entities which are handled by registered systems in the ECS pattern
 */
@Component
open class Scene {
    private val log = logger<Scene>()
    var isInitialized = false
        set(value) {
            field = value

            if (field) {
                onInitialize()
            } else {
                cleanup()
            }
        }
    var camera2D: Camera2D? = null

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
    private val renderingSystems = mutableListOf<RenderingSystem>()
    private val logicSystems = mutableListOf<LogicSystem>()
    private val entities = mutableListOf<Entity>()
    private val keyListeners = mutableListOf<KeyListener>()
    private val mouseListeners = mutableListOf<MouseListener>()
    private val mouseWheelListeners = mutableListOf<MouseWheelListener>()
    private val mouseMotionListeners = mutableListOf<MouseMotionListener>()
    private val focusListeners = mutableListOf<FocusListener>()
    var cachedEntities = 0
    val cachedFilterResults = mutableMapOf<EntityFilter, List<Entity>>()

    @PostConstruct
    fun init() {
        addKeyListeners(uiFocusLogicSystem, uiEventLogicSystem)
        addMouseMotionListeners(uiEventLogicSystem)
        addMouseListeners(uiEventLogicSystem)

        addLogicSystems(uiFocusLogicSystem, uiEventLogicSystem, uiSliderValueLogicSystem)
        // register default rendering systems (developers can later remove them if necessary)
        addRenderingSystems(sprite2DRenderingSystem, animation2DRenderingSystem)

        addEntities(cursor)
    }

    /**
     * Called once, when the scene is initialized, or multiple times when reInitialize() is used
     */
    open fun onInitialize() {}

    /**
     * Called every time this scene is switched to
     */
    open fun onEnable() {}

    /**
     * Called every time this scene is switched from
     */
    open fun onDisable() {}

    /**
     * Called every frame that is requested by the rendering engine
     */
    open fun onRender(g: Graphics, now: Long, deltaTime: Long) {}

    /**
     * Called every logic tick that is requested by the logic engine
     */
    open fun onUpdate(now: Long, deltaTime: Long) {}

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

    @Synchronized
    fun addRenderingSystems(vararg renderingSystems: RenderingSystem) {
        log.info("Registering {} rendering systems", renderingSystems.size)
        log.debug("{}", renderingSystems)
        this.renderingSystems.addAll(renderingSystems)
    }

    @Synchronized
    fun removeRenderingSystems(vararg renderingSystems: RenderingSystem) {
        log.info("Unregistering {} rendering systems", renderingSystems.size)
        log.debug("{}", renderingSystems)
        this.renderingSystems.removeAll(renderingSystems)
    }

    @Synchronized
    fun addLogicSystems(vararg logicSystems: LogicSystem) {
        log.info("Registering {} logic systems", logicSystems.size)
        log.debug("{}", logicSystems)
        this.logicSystems.addAll(logicSystems)
    }

    @Synchronized
    fun removeLogicSystems(vararg logicSystems: LogicSystem) {
        log.info("Unregistering {} logic systems", logicSystems.size)
        log.debug("{}", logicSystems)
        this.logicSystems.removeAll(logicSystems)
    }

    @Synchronized
    fun addEntities(vararg entities: Entity) {
        log.info("Registering {} entities", entities.size)
        log.debug("{}", entities)
        this.entities.addAll(entities)
    }

    @Synchronized
    fun removeEntities(vararg entities: Entity) {
        log.info("Unregistering {} entities", entities.size)
        log.debug("{}", entities)
        this.entities.removeAll(entities)
    }

    @Synchronized
    fun addKeyListeners(vararg keyListeners: KeyListener) {
        log.info("Registering {} key listeners", keyListeners.size)
        log.debug("{}", keyListeners)
        this.keyListeners.addAll(keyListeners)
    }

    @Synchronized
    fun removeKeyListeners(vararg keyListeners: KeyListener) {
        log.info("Unregistering {} key listeners", keyListeners.size)
        log.debug("{}", keyListeners)
        this.keyListeners.removeAll(keyListeners)
    }

    @Synchronized
    fun addMouseListeners(vararg mouseListeners: MouseListener) {
        log.info("Registering {} mouse listeners", mouseListeners.size)
        log.debug("{}", mouseListeners)
        this.mouseListeners.addAll(mouseListeners)
    }

    @Synchronized
    fun removeMouseListeners(vararg mouseListeners: MouseListener) {
        log.info("Unregistering {} mouse listeners", mouseListeners.size)
        log.debug("{}", mouseListeners)
        this.mouseListeners.removeAll(mouseListeners)
    }

    @Synchronized
    fun addMouseMotionListeners(vararg mouseMotionListeners: MouseMotionListener) {
        log.info("Registering {} mouse motion listeners", mouseMotionListeners.size)
        log.debug("{}", mouseMotionListeners)
        this.mouseMotionListeners.addAll(mouseMotionListeners)
    }

    @Synchronized
    fun removeMouseMotionListeners(vararg mouseMotionListeners: MouseMotionListener) {
        log.info("Unregistering {} mouse motion listeners", mouseMotionListeners.size)
        log.debug("{}", mouseMotionListeners)
        this.mouseMotionListeners.removeAll(mouseMotionListeners)
    }

    @Synchronized
    fun render(g: Graphics, now: Long, deltaTime: Long) {
        for (renderingSystem in renderingSystems) {
            renderingSystem.render(this, g, now, deltaTime)
        }

        onRender(g, now, deltaTime)
    }

    @Synchronized
    fun update(now: Long, deltaTime: Long) {
        for (logicSystem in logicSystems) {
            logicSystem.update(this, now, deltaTime)
        }

        onUpdate(now, deltaTime)
    }

    @Synchronized
    fun keyTyped(e: KeyEvent) {
        for (keyListener in keyListeners) {
            keyListener.keyTyped(e)
        }
    }

    @Synchronized
    fun keyPressed(e: KeyEvent) {
        for (keyListener in keyListeners) {
            keyListener.keyPressed(e)
        }
    }

    @Synchronized
    fun keyReleased(e: KeyEvent) {
        for (keyListener in keyListeners) {
            keyListener.keyReleased(e)
        }
    }

    @Synchronized
    fun mouseClicked(e: MouseEvent) {
        for (mouseListener in mouseListeners) {
            mouseListener.mouseClicked(e)
        }
    }

    @Synchronized
    fun mousePressed(e: MouseEvent) {
        for (mouseListener in mouseListeners) {
            mouseListener.mousePressed(e)
        }
    }

    @Synchronized
    fun mouseReleased(e: MouseEvent) {
        for (mouseListener in mouseListeners) {
            mouseListener.mouseReleased(e)
        }
    }

    @Synchronized
    fun mouseEntered(e: MouseEvent) {
        for (mouseListener in mouseListeners) {
            mouseListener.mouseEntered(e)
        }
    }

    @Synchronized
    fun mouseExited(e: MouseEvent) {
        for (mouseListener in mouseListeners) {
            mouseListener.mouseExited(e)
        }
    }

    @Synchronized
    fun mouseWheelMoved(e: MouseWheelEvent) {
        for (mouseWheelListener in mouseWheelListeners) {
            mouseWheelListener.mouseWheelMoved(e)
        }
    }

    @Synchronized
    fun mouseDragged(e: MouseEvent) {
        for (mouseMotionListener in mouseMotionListeners) {
            mouseMotionListener.mouseDragged(e)
        }
    }

    @Synchronized
    fun mouseMoved(e: MouseEvent) {
        for (mouseMotionListener in mouseMotionListeners) {
            mouseMotionListener.mouseMoved(e)
        }
    }

    @Synchronized
    fun focusGained(e: FocusEvent) {
        for (focusListener in focusListeners) {
            focusListener.focusGained(e)
        }
    }

    @Synchronized
    fun focusLost(e: FocusEvent) {
        for (focusListener in focusListeners) {
            focusListener.focusLost(e)
        }
    }

    /**
     * Grabs all entities that adhere to the specified filter.
     * This method uses caching to reduce cpu load while fetching entities in running systems
     */
    @Synchronized
    @JvmOverloads
    fun getEntities(filter: EntityFilter = EntityFilter { true }): List<Entity> {
        if (cachedEntities != entities.size) {
            // entities have changed, clear cache
            cachedFilterResults.clear()
            cachedEntities = entities.size
        }
        // cache and return filter results
        return cachedFilterResults.computeIfAbsent(filter) { entities.filter(filter::filter) }
    }

    fun interface EntityFilter {
        fun filter(entity: Entity): Boolean
    }
}