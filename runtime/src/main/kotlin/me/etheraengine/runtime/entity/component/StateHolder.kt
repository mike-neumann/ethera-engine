package me.etheraengine.runtime.entity.component

open class StateHolder(state: String) {
    var lastState = state
        private set
    var lockedAt = 0L
        private set
    var lockedFor = 0L
        private set
    val isLocked get() = System.currentTimeMillis() - lockedAt <= lockedFor
    var state = state
        set(value) {
            if (value != field) {
                lastState = field
                field = value
            }
        }

    /**
     * Locks this state for the amount of millis provided
     *
     * NOTE: State changes may only occur when state is unlocked, locked states will remain for the amount of millis provided after calling this function
     */
    fun lock(millis: Long) {
        lockedAt = System.currentTimeMillis()
        lockedFor = millis
    }

    /**
     * Unlocks this state
     */
    fun unlock() {
        lockedAt = 0
        lockedFor = 0
    }
}