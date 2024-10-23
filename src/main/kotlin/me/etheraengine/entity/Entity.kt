package me.etheraengine.entity

/**
 * Base entity class in the ECS pattern, entities contain Components that store individual values like position, movement, speed, attack, etc.
 */
open class Entity {
    private val components = mutableMapOf<Class<*>, Any>()

    fun <T : Any> addComponents(vararg components: T) {
        components.forEach {
            this.components[it::class.java] = it
        }
    }

    @PublishedApi
    @Suppress("UNCHECKED_CAST")
    internal fun <T : Any> getComponent(type: Class<T>) =
        components.entries.find { type == it.key || type.isAssignableFrom(it.key) }?.value as? T

    inline fun <reified T : Any> getComponent(): T? = getComponent(T::class.java)

    @PublishedApi
    @Suppress("UNCHECKED_CAST")
    internal fun <T : Any> getComponents(type: Class<T>) =
        components.entries.filter { type == it.key || type.isAssignableFrom(it.key) } as List<T>

    inline fun <reified T : Any> getComponents() = getComponents(T::class.java)

    @PublishedApi
    internal fun <T : Any> hasComponent(type: Class<T>) =
        components.filter { type == it.key || type.isAssignableFrom(it.key) }.any()

    inline fun <reified T : Any> hasComponent() = hasComponent(T::class.java)
}