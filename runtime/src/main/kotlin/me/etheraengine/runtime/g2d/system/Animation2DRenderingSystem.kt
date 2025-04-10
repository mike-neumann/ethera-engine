package me.etheraengine.runtime.g2d.system

import me.etheraengine.runtime.g2d.entity.component.Animations2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics2D

/**
 * prebuilt system to handle 2D animation graphics
 */
@Component
class Animation2DRenderingSystem : RenderingSystem {
    override fun render(scene: Scene, g: Graphics2D, now: Long, deltaTime: Long) {
        val entities = scene.getFilteredEntities { scene.camera2D.canSee(it) && it.hasComponent<Animations2D>() }

        for (entity in entities) {
            val animations = entity.getComponent<Animations2D>()!!
            val currentAnimation = animations.animations[animations.currentAnimation] ?: continue
            // stop last animation
            animations.animations[animations.lastAnimation]?.isPlaying = false

            if (!currentAnimation.isPlaying) {
                currentAnimation.isPlaying = true
            }

            if (currentAnimation.currentSpriteIndex == currentAnimation.spritesheet.sprites.size) {
                if (currentAnimation.shouldLoop) {
                    currentAnimation.currentSpriteIndex = 0
                } else {
                    currentAnimation.currentSpriteIndex -= 1
                }
            }
            // render current frame now...
            val currentSprite = currentAnimation.spritesheet.sprites[currentAnimation.currentSpriteIndex]

            g.drawImage(
                currentSprite,
                entity.x.toInt() + animations.renderOffsetX,
                entity.y.toInt() + animations.renderOffsetY,
                animations.renderWidth,
                animations.renderHeight,
                null
            )
            val frameTime = System.currentTimeMillis() - currentAnimation.lastFrameAt

            if (frameTime >= currentAnimation.frameDuration) {
                currentAnimation.currentSpriteIndex++
            }
        }
    }
}