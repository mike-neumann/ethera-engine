plugins {
    `java-library`
    `maven-publish`
    application
    kotlin("jvm")
    kotlin("kapt")
}

subprojects {
    group = "me.etheraengine"
    version = "1.0-SNAPSHOT"

    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "application")
    apply(plugin = "kotlin")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        api("org.springframework.boot:spring-boot-starter:3.3.1")
//        implementation("org.lwjgl:lwjgl:3.3.1")
//        implementation("org.lwjgl:lwjgl-opengl:3.3.1")
//        implementation("org.lwjgl:lwjgl-glfw:3.3.1")
//        implementation("me.xra1ny.essentia:essentia-configure:1.0")
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