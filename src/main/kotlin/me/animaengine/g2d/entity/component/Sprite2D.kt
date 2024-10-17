package me.animaengine.g2d.entity.component

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Sprite2D(
    val file: File
) {
    val image: BufferedImage = ImageIO.read(file)
}