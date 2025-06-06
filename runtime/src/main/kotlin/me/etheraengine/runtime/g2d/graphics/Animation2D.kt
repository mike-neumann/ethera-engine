package me.etheraengine.runtime.g2d.graphics

/**
 * Class to load a 2D animation via the provided spritesheet instance, containing meta information for animation rendering
 */
open class Animation2D(val spritesheet: Spritesheet2D, var frameDuration: Int, var shouldLoop: Boolean) {
    var lastFrameAt: Long = 0
        private set
    var currentSpriteIndex = 0
        set(value) {
            lastFrameAt = System.currentTimeMillis()
            field = value
        }
    var isPlaying = false
        set(value) {
            currentSpriteIndex = 0
            lastFrameAt = 0
            field = value
        }
}
