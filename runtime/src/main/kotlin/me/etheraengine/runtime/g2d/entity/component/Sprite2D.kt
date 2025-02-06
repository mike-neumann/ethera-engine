package me.etheraengine.runtime.g2d.entity.component

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Component in the ECS pattern that contains information about the rendering process of a sprite
 */
open class Sprite2D(val file: File, var renderOffsetX: Int = 0, var renderOffsetY: Int = 0) {
    val image: BufferedImage = ImageIO.read(file)
}