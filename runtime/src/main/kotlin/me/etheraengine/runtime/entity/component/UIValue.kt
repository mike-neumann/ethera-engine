package me.etheraengine.runtime.entity.component

import me.etheraengine.runtime.entity.Entity

typealias ChangeEvent<T> = (it: Entity, oldValue: T, newValue: T) -> Unit

open class UIValue<T>(
    var value: T,
    val maxValue: T = value,
    val onChange: ChangeEvent<T> = { _, _, _ -> },
) {
    var lastValue = value
}