rootProject.name = "ethera"

include("gui")
include("runtime")
include("example")

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("kapt") version kotlinVersion
    }
}