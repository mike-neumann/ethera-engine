package me.etheraengine.runtime.entity

import org.springframework.stereotype.Component

/**
 * Represents the users 2d screen cursor, acts as an entity which also has a bounding box
 */
@Component
open class Cursor : Entity(width = 1, height = 1)