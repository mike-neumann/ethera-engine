package me.etheraengine.example.system

import me.etheraengine.example.entity.EntityAnimation
import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.Direction
import me.etheraengine.example.entity.component.Position
import me.etheraengine.runtime.entity.component.State
import me.etheraengine.runtime.g2d.entity.component.Animations2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityAnimationLogicSystem : LogicSystem {
    override fun update(scene: Scene, now: Long, deltaTime: Long) {
        val entities =
            scene.getFilteredEntities { it.hasComponent<State>() && it.hasComponent<Position>() && it.hasComponent<Animations2D>() }

        for (entity in entities) {
            val state = entity.getComponent<State>()!!
            val position = entity.getComponent<Position>()!!
            val animations = entity.getComponent<Animations2D>()!!

            when (state.state) {
                EntityState.IDLE -> {
                    animations.currentAnimation = when (position.direction) {
                        Direction.LEFT -> EntityAnimation.IDLE_LEFT
                        Direction.RIGHT -> EntityAnimation.IDLE_RIGHT
                    }
                }

                EntityState.WALK -> {
                    animations.currentAnimation = when (position.direction) {
                        Direction.LEFT -> EntityAnimation.WALK_LEFT
                        Direction.RIGHT -> EntityAnimation.WALK_RIGHT
                    }
                }

                EntityState.ATTACK -> {
                    animations.currentAnimation = when (position.direction) {
                        Direction.LEFT -> EntityAnimation.ATTACK_LEFT
                        Direction.RIGHT -> EntityAnimation.ATTACK_RIGHT
                    }
                }

                EntityState.DAMAGE -> {
                    animations.currentAnimation = when (position.direction) {
                        Direction.LEFT -> EntityAnimation.DAMAGE_LEFT
                        Direction.RIGHT -> EntityAnimation.DAMAGE_RIGHT
                    }
                }

                EntityState.DYING -> {
                    animations.currentAnimation = when (position.direction) {
                        Direction.LEFT -> EntityAnimation.DIE_LEFT
                        Direction.RIGHT -> EntityAnimation.DIE_RIGHT
                    }
                }

                EntityState.DEAD -> {
                    animations.currentAnimation = EntityAnimation.DEAD
                }
            }
        }
    }
}