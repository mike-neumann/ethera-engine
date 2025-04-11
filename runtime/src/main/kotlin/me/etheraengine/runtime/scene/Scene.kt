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
import java.awt.Graphics2D
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
    val camera2D = Camera2D()
    private val _renderingSystems = mutableListOf<RenderingSystem>()
    private val _logicSystems = mutableListOf<LogicSystem>()
    private val _entities = mutableListOf<Entity>()
    private val _keyListeners = mutableListOf<KeyListener>()
    private val _mouseListeners = mutableListOf<MouseListener>()
    private val _mouseWheelListeners = mutableListOf<MouseWheelListener>()
    private val _mouseMotionListeners = mutableListOf<MouseMotionListener>()
    private val _focusListeners = mutableListOf<FocusListener>()
    val renderingSystems: List<RenderingSystem> get() = _renderingSystems
    val logicSystems: List<LogicSystem> get() = _logicSystems
    val entities: List<Entity> get() = _entities
    val keyListeners: List<KeyListener> get() = _keyListeners
    val mouseListeners: List<MouseListener> get() = _mouseListeners
    val mouseWheelListeners: List<MouseWheelListener> get() = _mouseWheelListeners
    val mouseMotionListeners: List<MouseMotionListener> get() = _mouseMotionListeners
    val focusListeners: List<FocusListener> get() = _focusListeners
    private var cachedEntities = 0
    private val cachedFilterResults = mutableMapOf<EntityFilter, List<Entity>>()

    @PostConstruct
    fun init() {
        addKeyListeners(uiFocusLogicSystem, uiEventLogicSystem, uiSliderValueLogicSystem)
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
    open fun onRender(g: Graphics2D, now: Long, deltaTime: Long) {}

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
        _renderingSystems.clear()
        _logicSystems.clear()
        _entities.clear()
        _keyListeners.clear()
        _mouseListeners.clear()
        _mouseWheelListeners.clear()
        _mouseMotionListeners.clear()
        _focusListeners.clear()
    }

    @Synchronized
    fun addRenderingSystems(vararg renderingSystems: RenderingSystem) {
        log.debug("Registering {} rendering systems", renderingSystems.size)
        log.debug("{}", renderingSystems)
        this._renderingSystems.addAll(renderingSystems)
    }

    @Synchronized
    fun removeRenderingSystems(vararg renderingSystems: RenderingSystem) {
        log.debug("Unregistering {} rendering systems", renderingSystems.size)
        log.debug("{}", renderingSystems)
        this._renderingSystems.removeAll(renderingSystems.toSet())
    }

    @Synchronized
    fun addLogicSystems(vararg logicSystems: LogicSystem) {
        log.debug("Registering {} logic systems", logicSystems.size)
        log.debug("{}", logicSystems)
        this._logicSystems.addAll(logicSystems)
    }

    @Synchronized
    fun removeLogicSystems(vararg logicSystems: LogicSystem) {
        log.debug("Unregistering {} logic systems", logicSystems.size)
        log.debug("{}", logicSystems)
        this._logicSystems.removeAll(logicSystems.toSet())
    }

    @Synchronized
    fun addEntities(vararg entities: Entity) {
        log.debug("Registering {} entities", entities.size)
        log.debug("{}", entities)
        this._entities.addAll(entities)
    }

    @Synchronized
    fun removeEntities(vararg entities: Entity) {
        log.debug("Unregistering {} entities", entities.size)
        log.debug("{}", entities)
        this._entities.removeAll(entities.toSet())
    }

    @Synchronized
    fun addKeyListeners(vararg keyListeners: KeyListener) {
        log.debug("Registering {} key listeners", keyListeners.size)
        log.debug("{}", keyListeners)
        this._keyListeners.addAll(keyListeners)
    }

    @Synchronized
    fun removeKeyListeners(vararg keyListeners: KeyListener) {
        log.debug("Unregistering {} key listeners", keyListeners.size)
        log.debug("{}", keyListeners)
        this._keyListeners.removeAll(keyListeners.toSet())
    }

    @Synchronized
    fun addMouseListeners(vararg mouseListeners: MouseListener) {
        log.debug("Registering {} mouse listeners", mouseListeners.size)
        log.debug("{}", mouseListeners)
        this._mouseListeners.addAll(mouseListeners)
    }

    @Synchronized
    fun removeMouseListeners(vararg mouseListeners: MouseListener) {
        log.debug("Unregistering {} mouse listeners", mouseListeners.size)
        log.debug("{}", mouseListeners)
        this._mouseListeners.removeAll(mouseListeners.toSet())
    }

    @Synchronized
    fun addMouseMotionListeners(vararg mouseMotionListeners: MouseMotionListener) {
        log.debug("Registering {} mouse motion listeners", mouseMotionListeners.size)
        log.debug("{}", mouseMotionListeners)
        this._mouseMotionListeners.addAll(mouseMotionListeners)
    }

    @Synchronized
    fun removeMouseMotionListeners(vararg mouseMotionListeners: MouseMotionListener) {
        log.debug("Unregistering {} mouse motion listeners", mouseMotionListeners.size)
        log.debug("{}", mouseMotionListeners)
        this._mouseMotionListeners.removeAll(mouseMotionListeners.toSet())
    }

    @Synchronized
    fun typeKey(e: KeyEvent) {
        for (keyListener in _keyListeners) {
            keyListener.keyTyped(e)
        }
    }

    @Synchronized
    fun pressKey(e: KeyEvent) {
        for (keyListener in _keyListeners) {
            keyListener.keyPressed(e)
        }
    }

    @Synchronized
    fun releaseKey(e: KeyEvent) {
        for (keyListener in _keyListeners) {
            keyListener.keyReleased(e)
        }
    }

    @Synchronized
    fun clickMouse(e: MouseEvent) {
        for (mouseListener in _mouseListeners) {
            mouseListener.mouseClicked(e)
        }
    }

    @Synchronized
    fun pressMouse(e: MouseEvent) {
        for (mouseListener in _mouseListeners) {
            mouseListener.mousePressed(e)
        }
    }

    @Synchronized
    fun releaseMouse(e: MouseEvent) {
        for (mouseListener in _mouseListeners) {
            mouseListener.mouseReleased(e)
        }
    }

    @Synchronized
    fun enterMouse(e: MouseEvent) {
        for (mouseListener in _mouseListeners) {
            mouseListener.mouseEntered(e)
        }
    }

    @Synchronized
    fun exitMouse(e: MouseEvent) {
        for (mouseListener in _mouseListeners) {
            mouseListener.mouseExited(e)
        }
    }

    @Synchronized
    fun moveMouseWheel(e: MouseWheelEvent) {
        for (mouseWheelListener in _mouseWheelListeners) {
            mouseWheelListener.mouseWheelMoved(e)
        }
    }

    @Synchronized
    fun dragMouse(e: MouseEvent) {
        for (mouseMotionListener in _mouseMotionListeners) {
            mouseMotionListener.mouseDragged(e)
        }
    }

    @Synchronized
    fun moveMouse(e: MouseEvent) {
        for (mouseMotionListener in _mouseMotionListeners) {
            mouseMotionListener.mouseMoved(e)
        }
    }

    @Synchronized
    fun gainFocus(e: FocusEvent) {
        for (focusListener in _focusListeners) {
            focusListener.focusGained(e)
        }
    }

    @Synchronized
    fun loseFocus(e: FocusEvent) {
        for (focusListener in _focusListeners) {
            focusListener.focusLost(e)
        }
    }

    @Synchronized
    fun render(g: Graphics2D, now: Long, deltaTime: Long) {
        camera2D.translate(g)
        for (renderingSystem in _renderingSystems) {
            for (entity in _entities) {
                renderingSystem.render(entity, this, g, now, deltaTime)
            }
            renderingSystem.render(this, g, now, deltaTime)
        }
        camera2D.closeTranslation(g)
        onRender(g, now, deltaTime)
    }

    @Synchronized
    fun update(now: Long, deltaTime: Long) {
        _entities.removeAll { it.markedForRemoval }

        for (logicSystem in _logicSystems) {
            for (entity in _entities) {
                logicSystem.update(entity, this, now, deltaTime)
            }
            logicSystem.update(this, now, deltaTime)
        }

        onUpdate(now, deltaTime)
    }

    /**
     * Grabs all entities that adhere to the specified filter.
     * This method uses caching to reduce cpu load while fetching entities in running systems
     */
    @Synchronized
    @JvmOverloads
    fun getFilteredEntities(filter: EntityFilter = EntityFilter { true }): List<Entity> {
        if (cachedEntities != _entities.size) {
            // entities have changed, clear cache
            cachedFilterResults.clear()
            cachedEntities = _entities.size
        }
        // cache and return filter results
        return cachedFilterResults.computeIfAbsent(filter) { _entities.filter(filter::filter) }
    }

    fun interface EntityFilter {
        fun filter(entity: Entity): Boolean
    }
}