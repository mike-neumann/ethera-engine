package me.etheraengine.example.entity.component

data class Health(var maxHealth: Double, val cooldown: Long) {
    var lastHealth = maxHealth
    var lastDamageTime: Long = 0
        private set
    var health = maxHealth
        set(value) {
            if (value != field) {
                if (value < field) {
                    lastDamageTime = System.currentTimeMillis()
                }

                lastHealth = field
                field = value.coerceAtLeast(0.0)
            }
        }
}