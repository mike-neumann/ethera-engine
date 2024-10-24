package me.etheraengine.entity.component

open class Value<T : Number>(
    var value: T,
    val maxValue: T = value
)