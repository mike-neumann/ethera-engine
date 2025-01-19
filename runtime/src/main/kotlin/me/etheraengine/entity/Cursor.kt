package me.etheraengine.entity

import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Position2D
import org.springframework.stereotype.Component

/**
 * Represents the users 2d screen cursor, acts as an entity which also has a bounding box
 */
@Component
open class Cursor : Entity() {
    init {
        addComponents(
            Position2D(0.0, 0.0),
            Dimensions2D(1, 1)
        )
    }
}