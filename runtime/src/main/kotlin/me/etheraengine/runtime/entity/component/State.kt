package me.etheraengine.runtime.entity.component

open class State(state: String) {
    var lastState = state
        private set
    var lockTime: Long = 0
        private set
    var lockedTime: Long = 0
        private set
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
        lockTime = System.currentTimeMillis()
        lockedTime = millis
    }

    /**
     * Unlocks this state
     */
    fun unlock() {
        lockTime = 0
        lockedTime = 0
    }
}