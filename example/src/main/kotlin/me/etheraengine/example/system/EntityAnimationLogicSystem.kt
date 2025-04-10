package me.etheraengine.example.system

import me.etheraengine.example.entity.EntityAnimation
import me.etheraengine.example.entity.EntityState
import me.etheraengine.example.entity.component.Direction
import me.etheraengine.example.entity.component.MovementDirection
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.entity.component.StateHolder
import me.etheraengine.runtime.g2d.entity.component.Animations2D
import me.etheraengine.runtime.scene.Scene
import me.etheraengine.runtime.system.LogicSystem
import org.springframework.stereotype.Component

@Component
class EntityAnimationLogicSystem : LogicSystem {
    override fun update(entity: Entity, scene: Scene, now: Long, deltaTime: Long) {
        if (!entity.hasComponent<MovementDirection>()) return
        val stateHolder = entity.getComponent<StateHolder>()!!
        val animations = entity.getComponent<Animations2D>()!!
        val movementDirection = entity.getComponent<MovementDirection>()!!

        when (stateHolder.state) {
            EntityState.IDLE -> {
                animations.currentAnimation = when (movementDirection.direction) {
                    Direction.LEFT -> EntityAnimation.IDLE_LEFT
                    Direction.RIGHT -> EntityAnimation.IDLE_RIGHT
                }
            }

            EntityState.WALK -> {
                animations.currentAnimation = when (movementDirection.direction) {
                    Direction.LEFT -> EntityAnimation.WALK_LEFT
                    Direction.RIGHT -> EntityAnimation.WALK_RIGHT
                }
            }

            EntityState.ATTACK -> {
                animations.currentAnimation = when (movementDirection.direction) {
                    Direction.LEFT -> EntityAnimation.ATTACK_LEFT
                    Direction.RIGHT -> EntityAnimation.ATTACK_RIGHT
                }
            }

            EntityState.DAMAGE -> {
                animations.currentAnimation = when (movementDirection.direction) {
                    Direction.LEFT -> EntityAnimation.DAMAGE_LEFT
                    Direction.RIGHT -> EntityAnimation.DAMAGE_RIGHT
                }
            }

            EntityState.DYING -> {
                animations.currentAnimation = when (movementDirection.direction) {
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