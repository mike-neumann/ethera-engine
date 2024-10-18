plugins {
    kotlin("jvm")
}

group = "me.animaengine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("me.animaengine:anima:1.0-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}