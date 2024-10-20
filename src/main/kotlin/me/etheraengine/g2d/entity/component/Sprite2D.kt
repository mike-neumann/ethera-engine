package me.etheraengine.g2d.entity.component

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Sprite2D(
    val file: File,
    var renderOffsetX: Int = 0,
    var renderOffsetY: Int = 0
) {
    val image: BufferedImage = ImageIO.read(file)
}