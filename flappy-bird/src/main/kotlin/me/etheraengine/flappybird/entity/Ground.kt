package me.etheraengine.flappybird.entity

import me.etheraengine.flappybird.entity.component.Obstacle
import me.etheraengine.runtime.entity.Entity
import java.awt.Color

class Ground(x: Double, y: Double, width: Int, height: Int) : Entity(x, y, width, height) {
    init {
        addComponents(Color.YELLOW, Obstacle())
    }
}