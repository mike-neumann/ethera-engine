package me.etheraengine.example.entity

import me.etheraengine.entity.Entity
import me.etheraengine.entity.component.State
import me.etheraengine.example.entity.component.*
import me.etheraengine.example.graphics.entity.*
import me.etheraengine.g2d.entity.component.Animations2D
import me.etheraengine.g2d.entity.component.Movement2D

class Enemy(
    x: Float,
    y: Float
) : Entity() {
    init {
        addComponents(
            State(EntityState.IDLE),
            Movement2D(300f),
            Position(x, y, 50, 35),
            Attack(1f, 40f, 400, 10, 40f),
            Health(5f, 100),
            EnemyAI(null),
            Collideable(),
            Animations2D(
                EntityAnimation.IDLE_LEFT,
                mapOf(
                    EntityAnimation.IDLE_LEFT to PlayerIdleLeftAnimation(),
                    EntityAnimation.IDLE_RIGHT to PlayerIdleRightAnimation(),
                    EntityAnimation.WALK_LEFT to PlayerWalkLeftAnimation(),
                    EntityAnimation.WALK_RIGHT to PlayerWalkRightAnimation(),
                    EntityAnimation.ATTACK_LEFT to PlayerAttackLeftAnimation(),
                    EntityAnimation.ATTACK_RIGHT to PlayerAttackRightAnimation(),
                    EntityAnimation.DAMAGE_LEFT to PlayerDamageLeftAnimation(),
                    EntityAnimation.DAMAGE_RIGHT to PlayerDamageRightAnimation(),
                    EntityAnimation.DIE_LEFT to PlayerDieLeftAnimation(),
                    EntityAnimation.DIE_RIGHT to PlayerDieRightAnimation(),
                    EntityAnimation.DEAD to EntityDespawnAnimation()
                ),
                100,
                100,
                -25,
                -40
            )
        )
    }
}