package me.etheraengine.example.entity

import me.etheraengine.entity.Entity
import me.etheraengine.entity.component.State
import me.etheraengine.example.entity.component.*
import me.etheraengine.g2d.entity.component.Animations2D
import me.etheraengine.g2d.entity.component.Movement2D
import me.etheraengine.g2d.graphics.Animation2D
import me.etheraengine.g2d.graphics.Spritesheet2D
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.awt.Dimension

@Component
class Player : Entity() {
    class Animation {
        class IdleLeft : Animation2D(
            Spritesheet2D(
                ResourceUtils.getFile("classpath:assets/animations/player/idle-left.png"),
                100,
                100,
                1,
                30
            ),
            200,
            true
        )

        class IdleRight : Animation2D(
            Spritesheet2D(
                ResourceUtils.getFile("classpath:assets/animations/player/idle-right.png"),
                100,
                100,
                1,
                30
            ),
            200,
            true
        )

        class WalkLeft : Animation2D(
            Spritesheet2D(
                ResourceUtils.getFile("classpath:assets/animations/player/walk-left.png"),
                100,
                100,
                1,
                30
            ),
            75,
            true
        )

        class WalkRight : Animation2D(
            Spritesheet2D(
                ResourceUtils.getFile("classpath:assets/animations/player/walk-right.png"),
                100,
                100,
                1,
                30
            ),
            75,
            true
        )

        class AttackLeft : Animation2D(
            Spritesheet2D(
                ResourceUtils.getFile("classpath:assets/animations/player/attack-left.png"),
                100,
                100,
                1,
                30
            ),
            75,
            false
        )

        class AttackRight : Animation2D(
            Spritesheet2D(
                ResourceUtils.getFile("classpath:assets/animations/player/attack-right.png"),
                100,
                100,
                1,
                30
            ),
            75,
            false
        )

        class DamageLeft : Animation2D(
            Spritesheet2D(
                ResourceUtils.getFile("classpath:assets/animations/player/damage-left.png"),
                100,
                100,
                1,
                30
            ),
            200,
            false
        )

        class DamageRight : Animation2D(
            Spritesheet2D(
                ResourceUtils.getFile("classpath:assets/animations/player/damage-right.png"),
                100,
                100,
                1,
                30
            ),
            200,
            false
        )

        class DieLeft : Animation2D(
            Spritesheet2D(
                ResourceUtils.getFile("classpath:assets/animations/player/die-left.png"),
                100,
                100,
                1,
                30
            ),
            300,
            false
        )

        class DieRight : Animation2D(
            Spritesheet2D(
                ResourceUtils.getFile("classpath:assets/animations/player/die-right.png"),
                100,
                100,
                1,
                30
            ),
            300,
            false
        )

        class Despawn : Animation2D(
            Spritesheet2D(
                ResourceUtils.getFile("classpath:assets/animations/despawn.png"),
                128,
                128,
                1,
                0
            ),
            100,
            false
        )
    }

    init {
        addComponents(
            State(EntityState.IDLE),
            Movement2D(250.0),
            PlayerMovement(),
            Position(400.0, 400.0),
            Dimension(50, 35),
            Attack(1.0, 40.0, 250, 100, 40.0),
            Health(10.0, 500),
            Collideable(true),
            Animations2D(
                EntityAnimation.IDLE_LEFT,
                mapOf(
                    EntityAnimation.IDLE_LEFT to Animation.IdleLeft(),
                    EntityAnimation.IDLE_RIGHT to Animation.IdleRight(),
                    EntityAnimation.WALK_LEFT to Animation.WalkLeft(),
                    EntityAnimation.WALK_RIGHT to Animation.WalkRight(),
                    EntityAnimation.ATTACK_LEFT to Animation.AttackLeft(),
                    EntityAnimation.ATTACK_RIGHT to Animation.AttackRight(),
                    EntityAnimation.DAMAGE_LEFT to Animation.DamageLeft(),
                    EntityAnimation.DAMAGE_RIGHT to Animation.DamageRight(),
                    EntityAnimation.DIE_LEFT to Animation.DieLeft(),
                    EntityAnimation.DIE_RIGHT to Animation.DieRight(),
                    EntityAnimation.DEAD to Animation.Despawn()
                ),
                100,
                100,
                -25,
                -40
            )
        )
    }
}