rootProject.name = "ethera"

include("gui")
include("runtime")
include("example")
include("flappy-bird")

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("kapt") version kotlinVersion
    }
}
