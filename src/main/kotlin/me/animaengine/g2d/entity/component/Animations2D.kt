package me.animaengine.g2d.entity.component

import me.animaengine.g2d.graphics.Animation2D

open class Animations2D(
    currentAnimation: String,
    var animations: Map<String, Animation2D>,
    var renderWidth: Int,
    var renderHeight: Int,
    var renderOffset: Int = 0
) {
    var lastAnimation = ""
        private set
    var currentAnimation = currentAnimation
        set(value) {
            if (value != field) {
                lastAnimation = field
                field = value
            }
        }
}