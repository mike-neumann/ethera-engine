package me.etheraengine.example.entity.component

data class Attack(
    var damage: Float,
    var range: Float,
    var damageDelay: Long,
    var knockback: Float
) {
    var lastAttackHitTime: Long = 0
    var lastAttackTime: Long = 0
        private set
    var isAttacking = false
        set(value) {
            if (value != field) {
                if (value) {
                    lastAttackTime = System.currentTimeMillis()
                }

                field = value
            }
        }
}