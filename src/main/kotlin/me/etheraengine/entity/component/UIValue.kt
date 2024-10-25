package me.etheraengine.entity.component

open class UIValue<T : Number>(
    var value: T,
    val maxValue: T = value
)