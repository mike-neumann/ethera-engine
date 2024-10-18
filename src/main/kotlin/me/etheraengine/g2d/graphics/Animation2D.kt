package me.etheraengine.g2d.graphics

open class Animation2D(
    val spritesheet: Spritesheet2D,
    var frameDuration: Int,
    var shouldLoop: Boolean
) {
    var lastFrameTime: Long = 0
        private set
    var currentSpriteIndex = 0
        set(value) {
            lastFrameTime = System.currentTimeMillis()
            field = value
        }
    var isPlaying = false
        set(value) {
            currentSpriteIndex = 0
            lastFrameTime = 0
            field = value
        }
}
