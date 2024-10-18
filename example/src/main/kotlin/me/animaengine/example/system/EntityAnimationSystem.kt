package me.animaengine.example.system

import me.animaengine.entity.Entity
import me.animaengine.entity.component.State
import me.animaengine.g2d.entity.component.Animations2D
import me.animaengine.scene.Scene
import me.animaengine.system.LogicSystem
import me.animaengine.example.entity.EntityAnimation
import me.animaengine.example.entity.EntityState
import me.animaengine.example.entity.component.Direction
import me.animaengine.example.entity.component.Position
import org.springframework.stereotype.Component

@Component
class EntityAnimationSystem : LogicSystem {
    override fun update(deltaTime: Long, scene: Scene, entities: List<Entity>) {
        entities
            .filter { it.hasComponent<State>() }
            .filter { it.hasComponent<Position>() }
            .filter { it.hasComponent<Animations2D>() }
            .forEach {
                val state = it.getComponent<State>()!!
                val position = it.getComponent<Position>()!!
                val animations = it.getComponent<Animations2D>()!!

                when (state.state) {
                    EntityState.IDLE -> {
                        animations.currentAnimation =
                            when (position.direction) {
                                Direction.LEFT -> EntityAnimation.IDLE_LEFT
                                Direction.RIGHT -> EntityAnimation.IDLE_RIGHT
                            }
                    }

                    EntityState.WALK -> {
                        animations.currentAnimation =
                            when (position.direction) {
                                Direction.LEFT -> EntityAnimation.WALK_LEFT
                                Direction.RIGHT -> EntityAnimation.WALK_RIGHT
                            }
                    }

                    EntityState.ATTACK -> {
                        animations.currentAnimation =
                            when (position.direction) {
                                Direction.LEFT -> EntityAnimation.ATTACK_LEFT
                                Direction.RIGHT -> EntityAnimation.ATTACK_RIGHT
                            }
                    }

                    EntityState.DAMAGE -> {
                        animations.currentAnimation =
                            when (position.direction) {
                                Direction.LEFT -> EntityAnimation.DAMAGE_LEFT
                                Direction.RIGHT -> EntityAnimation.DAMAGE_RIGHT
                            }
                    }

                    EntityState.DYING -> {
                        animations.currentAnimation =
                            when (position.direction) {
                                Direction.LEFT -> EntityAnimation.DIE_LEFT
                                Direction.RIGHT -> EntityAnimation.DIE_RIGHT
                            }
                    }
                }
            }
    }
}