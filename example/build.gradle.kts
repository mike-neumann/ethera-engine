plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("kapt") version "1.9.24"
}

group = "me.etheraengine.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("me.etheraengine:runtime:1.0-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}