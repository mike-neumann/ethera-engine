package me.animaengine.g2d.system

import me.animaengine.entity.Entity
import me.animaengine.g2d.entity.component.Animations2D
import me.animaengine.g2d.entity.component.Position2D
import me.animaengine.scene.Scene
import me.animaengine.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics

/**
 * prebuilt system to handle 2D animation graphics
 */
@Component
class Animation2DRenderingSystem : RenderingSystem {
    override fun render(g: Graphics, scene: Scene, entities: List<Entity>) {
        entities
            .filter { it.hasComponent<Animations2D>() }
            .filter { it.hasComponent<Position2D>() }
            .forEach {
                val animations = it.getComponent<Animations2D>()!!
                val currentAnimation = animations.animations[animations.currentAnimation]
                    ?: return@forEach

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

                val position = it.getComponent<Position2D>()!!

                // render current frame now...
                val currentSprite = currentAnimation.spritesheet.sprites[currentAnimation.currentSpriteIndex]

                g.drawImage(
                    currentSprite,
                    position.x.toInt() + animations.renderOffset,
                    position.y.toInt() + animations.renderOffset,
                    animations.renderWidth,
                    animations.renderHeight,
                    null
                )

                val frameTime = System.currentTimeMillis() - currentAnimation.lastFrameTime

                if (frameTime >= currentAnimation.frameDuration) {
                    currentAnimation.currentSpriteIndex++
                }
            }
    }
}