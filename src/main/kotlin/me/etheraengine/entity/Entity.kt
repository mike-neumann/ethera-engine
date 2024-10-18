package me.etheraengine.entity

/**
 * Base entity class in the ECS pattern, entities contain Components that store individual values like position, movement, speed, attack, etc.
 */
open class Entity {
    /**
     * Holds all registered components in the ECS pattern for this entity
     */
    val components = mutableMapOf<Class<*>, Any>()

    inline fun <reified T : Any> addComponents(vararg components: T) {
        components.forEach {
            this.components[it::class.java] = it
        }
    }

    inline fun <reified T> getComponent(): T? =
        components.entries.find { T::class.java == it.key || T::class.java.isAssignableFrom(it.key) }?.value as? T

    inline fun <reified T> hasComponent() =
        components.filter { T::class.java == it.key || T::class.java.isAssignableFrom(it.key) }.any()
}