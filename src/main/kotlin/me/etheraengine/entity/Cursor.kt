package me.etheraengine.entity

import org.springframework.stereotype.Component
import java.awt.Dimension
import java.awt.geom.Point2D

/**
 * Represents the users 2d screen cursor, acts as an entity which also has a bounding box
 */
@Component
open class Cursor : Entity() {
    init {
        addComponents(
            Point2D.Double(0.0, 0.0),
            Dimension(1, 1)
        )
    }
}