package me.etheraengine.example.entity

import me.etheraengine.entity.Entity
import me.etheraengine.entity.component.State
import me.etheraengine.example.entity.component.*
import me.etheraengine.g2d.entity.component.Animations2D
import me.etheraengine.g2d.entity.component.Dimensions2D
import me.etheraengine.g2d.entity.component.Movement2D

class Enemy(
    x: Double,
    y: Double,
) : Entity() {
    init {
        addComponents(
            State(EntityState.IDLE),
            Movement2D(300.0, 20.0, 20.0),
            Position(x, y),
            Dimensions2D(50, 35),
            Attack(1.0, 60.0, 250, 100, 40.0),
            Health(5.0, 100),
            EnemyAI(null),
            Collideable(true),
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