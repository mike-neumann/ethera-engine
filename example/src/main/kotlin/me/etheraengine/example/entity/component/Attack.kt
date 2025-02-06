package me.etheraengine.example.entity.component

data class Attack(
    var damage: Double,
    var range: Double,
    var damageDelay: Long,
    var damageTimeRange: Long,
    var knockback: Double,
) {
    var lastAttackTime: Long = 0
    var isAttacking = false
}