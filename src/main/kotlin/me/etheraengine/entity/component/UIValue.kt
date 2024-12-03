package me.etheraengine.entity.component

import me.etheraengine.entity.Entity

open class UIValue<T>(
    var value: T,
    val maxValue: T = value,
    val onChange: (it: Entity, oldValue: T, newValue: T) -> Unit = { _, _, _ -> }
) {
    var lastValue = value
}