plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.kapt)
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.dependencyManagement)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.javaLibrary)
    alias(libs.plugins.application)
    alias(libs.plugins.mavenPublish)
}

repositories {
    mavenLocal()
    mavenCentral()
}

subprojects {
    group = "me.etheraengine"
    version = "1.0-SNAPSHOT"

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        api(rootProject.libs.bundles.api)
//        implementation("org.lwjgl:lwjgl:3.3.1")
//        implementation("org.lwjgl:lwjgl-opengl:3.3.1")
//        implementation("org.lwjgl:lwjgl-glfw:3.3.1")
//        implementation("com.badlogicgames.gdx:gdx-box2d:1.11.0")
//
//        // LWJGL Natives (Ensure proper OpenGL support across platforms)
//        runtimeOnly("org.lwjgl:lwjgl:3.3.1:natives-windows")
//        runtimeOnly("org.lwjgl:lwjgl-opengl:3.3.1:natives-windows")
//        runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.1:natives-windows")
//
//        runtimeOnly("org.lwjgl:lwjgl:3.3.1:natives-linux")
//        runtimeOnly("org.lwjgl:lwjgl-opengl:3.3.1:natives-linux")
//        runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.1:natives-linux")
//
//        runtimeOnly("org.lwjgl:lwjgl:3.3.1:natives-macos")
//        runtimeOnly("org.lwjgl:lwjgl-opengl:3.3.1:natives-macos")
//        runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.1:natives-macos")
//
//        runtimeOnly("org.lwjgl:lwjgl:3.3.1:natives-macos-arm64")
//        runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.1:natives-macos-arm64")
//        runtimeOnly("org.lwjgl:lwjgl-opengl:3.3.1:natives-macos-arm64")
    }

    kotlin {
        jvmToolchain(21)
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = group.toString()
                artifactId = project.name
                version = version
                from(components["java"])
            }
        }
    }
}