package me.etheraengine.g2d.graphics

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

data class Spritesheet2D(
    val file: File,
    val gridWidth: Int,
    val gridHeight: Int,
    val rows: Int,
    val offset: Int = 0
) {
    val image: BufferedImage = ImageIO.read(file)
    val sprites: Array<BufferedImage>

    init {
        val subImages = mutableListOf<BufferedImage>()

        for (row in 0 until rows) {
            for (x in 0..image.width - gridWidth step gridWidth) {
                subImages.add(
                    image.getSubimage(
                        x + offset,
                        (gridHeight * row) + offset,
                        gridWidth - (offset * 2),
                        gridHeight - (offset * 2)
                    )
                )
            }
        }

        this.sprites = subImages.toTypedArray()
    }
}