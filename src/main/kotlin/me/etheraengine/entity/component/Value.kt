package me.etheraengine.entity.component

open class Value<T>(
    var value: T,
    val maxValue: T = value
)