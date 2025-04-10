package me.etheraengine.example.entity

import me.etheraengine.example.entity.component.*
import me.etheraengine.runtime.entity.Entity
import me.etheraengine.runtime.entity.component.StateHolder
import me.etheraengine.runtime.g2d.entity.component.Animations2D
import me.etheraengine.runtime.g2d.entity.component.Movement2D

class Enemy(x: Double, y: Double) : Entity(x, y, 50, 35) {
    init {
        addComponents(
            StateHolder(EntityState.IDLE),
            MovementDirection(Direction.LEFT),
            Movement2D(.7),
            AttackHolder(1.0, 60.0, 250, 100, 40.0),
            Health(5.0, 100),
            EnemyAI(null),
            Collidable(),
            Animations2D(
                EntityAnimation.IDLE_LEFT,
                mapOf(
                    EntityAnimation.IDLE_LEFT to Player.Animation.IdleLeft(),
                    EntityAnimation.IDLE_RIGHT to Player.Animation.IdleRight(),
                    EntityAnimation.WALK_LEFT to Player.Animation.WalkLeft(),
                    EntityAnimation.WALK_RIGHT to Player.Animation.WalkRight(),
                    EntityAnimation.ATTACK_LEFT to Player.Animation.AttackLeft(),
                    EntityAnimation.ATTACK_RIGHT to Player.Animation.AttackRight(),
                    EntityAnimation.DAMAGE_LEFT to Player.Animation.DamageLeft(),
                    EntityAnimation.DAMAGE_RIGHT to Player.Animation.DamageRight(),
                    EntityAnimation.DIE_LEFT to Player.Animation.DieLeft(),
                    EntityAnimation.DIE_RIGHT to Player.Animation.DieRight(),
                    EntityAnimation.DEAD to Player.Animation.Despawn()
                ),
                100,
                100,
                -25,
                -40
            )
        )
    }
}