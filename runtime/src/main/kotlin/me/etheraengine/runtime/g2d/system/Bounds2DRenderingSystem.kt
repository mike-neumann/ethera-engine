package me.etheraengine.runtime.g2d.system

import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics2D

/**
 * Prebuilt system to render the bounds of every registered entity.
 *
 * Register this rendering system if you want every entity to render their bounds
 */
@Component
class Bounds2DRenderingSystem : RenderingSystem {
    override fun render(entity: Entity, scene: Scene, g: Graphics2D, now: Long, deltaTime: Long) {
        if (!scene.camera2D.canSee(entity)) return
        g.drawRect(entity.x.toInt(), entity.y.toInt(), entity.width, entity.height)
    }
}