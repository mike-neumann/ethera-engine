package me.etheraengine.g2d.system

import me.etheraengine.g2d.entity.component.Animations2D
import me.etheraengine.g2d.entity.component.Position2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.RenderingSystem
import org.springframework.stereotype.Component
import java.awt.Graphics

/**
 * prebuilt system to handle 2D animation graphics
 */
@Component
class Animation2DRenderingSystem : RenderingSystem {
    override fun render(
        scene: Scene,
        g: Graphics,
        now: Long,
        deltaTime: Long,
    ) {
        scene.getEntities {
            it.hasComponent<Animations2D>() && it.hasComponent<Position2D>()
        }.forEach {
            val animations = it.getComponent<Animations2D>()!!
            val currentAnimation = animations.animations[animations.currentAnimation]
                ?: return@forEach

            // stop last animation
            animations.animations[animations.lastAnimation]?.isPlaying = false

            if (!currentAnimation.isPlaying) {
                currentAnimation.isPlaying = true
            }

            if (currentAnimation.currentSpriteIndex == currentAnimation.spritesheet.sprites.size) {
                when (currentAnimation.shouldLoop) {
                    true -> currentAnimation.currentSpriteIndex = 0
                    false -> currentAnimation.currentSpriteIndex -= 1
                }
            }

            val position = it.getComponent<Position2D>()!!

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

            if (frameTime >= currentAnimation.frameDuration) {
                currentAnimation.currentSpriteIndex++
            }
        }
    }
}