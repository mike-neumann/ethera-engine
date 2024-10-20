package me.etheraengine.example.entity.component

data class Attack(
    var damage: Float,
    var range: Float,
    var damageDelay: Long,
    var damageTimeRange: Long,
    var knockback: Float
) {
    var lastAttackTime: Long = 0
    var isAttacking = false
}