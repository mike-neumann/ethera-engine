package me.etheraengine.example.system

import me.etheraengine.entity.Entity
import me.etheraengine.entity.component.State
import me.etheraengine.example.entity.EntityAnimation
import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.Direction
import me.etheraengine.example.entity.component.Position
import me.etheraengine.g2d.entity.component.Animations2D
import me.etheraengine.scene.Scene
import me.etheraengine.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityAnimationSystem : LogicSystem {
    override fun update(scene: Scene, entities: List<Entity>, now: Long, deltaTime: Long) {
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

                    EntityState.DEAD -> {
                        animations.currentAnimation = EntityAnimation.DEAD
                    }
                }
            }
    }
}