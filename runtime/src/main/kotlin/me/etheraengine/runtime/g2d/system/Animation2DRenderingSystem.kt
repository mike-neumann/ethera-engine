package me.etheraengine.runtime.g2d.system

import me.etheraengine.runtime.g2d.entity.component.Animations2D
import me.etheraengine.runtime.g2d.entity.component.Position2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics

/**
 * prebuilt system to handle 2D animation graphics
 */
@Component
class Animation2DRenderingSystem : RenderingSystem {
    override fun render(scene: Scene, g: Graphics, now: Long, deltaTime: Long) {
        val entities = scene.getFilteredEntities { scene.camera2D.canSee(it) && it.hasComponent<Animations2D>() && it.hasComponent<Position2D>() }

        for (entity in entities) {
            val animations = entity.getComponent<Animations2D>()!!
            val currentAnimation = animations.animations[animations.currentAnimation] ?: continue
            // stop last animation
            animations.animations[animations.lastAnimation]?.isPlaying = false

            if (!currentAnimation.isPlaying) currentAnimation.isPlaying = true

            if (currentAnimation.currentSpriteIndex == currentAnimation.spritesheet.sprites.size) {
                if (currentAnimation.shouldLoop) {
                    currentAnimation.currentSpriteIndex = 0
                } else {
                    currentAnimation.currentSpriteIndex -= 1
                }
            }
            val position = entity.getComponent<Position2D>()!!
            // render current frame now...
            val currentSprite = currentAnimation.spritesheet.sprites[currentAnimation.currentSpriteIndex]

            g.drawImage(
                currentSprite,
                position.x.toInt() + animations.renderOffsetX,
                position.y.toInt() + animations.renderOffsetY,
                animations.renderWidth,
                animations.renderHeight,
                null
            )
            val frameTime = System.currentTimeMillis() - currentAnimation.lastFrameTime

            if (frameTime >= currentAnimation.frameDuration) currentAnimation.currentSpriteIndex++
        }
    }
}