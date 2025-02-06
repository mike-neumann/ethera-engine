package me.etheraengine.runtime.entity

/**
 * Base entity class in the ECS pattern, entities contain Components that store individual values like position, movement, speed, attack, etc.
 */
open class Entity {
    private val components = mutableMapOf<Class<*>, Any>()
    private var cachedComponents = 0
    private val cachedComponentFilterResults = mutableMapOf<Class<*>, Any>()

    fun <T : Any> addComponents(vararg components: T) {
        for (component in components) {
            this.components[component::class.java] = component
        }
    }

    @PublishedApi
    @Suppress("UNCHECKED_CAST")
    internal fun <T : Any> getComponent(type: Class<T>): T? {
        if (components.size != cachedComponents) {
            // components have changed, clear all filters and recalculate cumulative calls
            cachedComponentFilterResults.clear()
            cachedComponents = components.size
        }

        if (!cachedComponentFilterResults.containsKey(type)) {
            val component =
                components.entries.find { type == it.key || type.isAssignableFrom(it.key) }?.value as? T
                    ?: return null

            cachedComponentFilterResults[type] = component
        }

        return cachedComponentFilterResults[type] as T?
    }

    inline fun <reified T : Any> getComponent(): T? = getComponent(T::class.java)

    @PublishedApi
    internal fun <T : Any> hasComponent(type: Class<T>) = getComponent(type) != null

    inline fun <reified T : Any> hasComponent() = hasComponent(T::class.java)
}