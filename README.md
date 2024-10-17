# Anima

## TechStack

- JDK 21
- Kotlin
- Gradle (Kotlin DSL)
- SpringBoot 3
- Swing (Graphics)

## General

Anima is a lightweight code based game engine designed to develop 2D gaming experiences.  
With SpringBoot 3 at its core, Anima supports all mayor Java developing patterns used in enterprise solutions.

## How to implement

To implement the Anima core into your projects, you must follow these simple steps:

1. Clone the Anima project via git
2. Publish the project locally using gradle  
   via the gradle wrapper: `./gradlew publishToMavenLocal`  
   or using the IDEA run configuration: `publishToMavenLocal`
3. Implement Anima as a dependency in your target project  
   Gradle (Kotlin DSL)

```kotlin
repositories {
    // Needed to fetch dependency from local m2
    mavenLocal()
    // Only needed if you also implement external dependencies through m2 center repo
    mavenCentral()
}

dependencies {
    implementation("me.animaengine:anima:1.0")
}
```

Gradle (Groovy DSL)

```groovy
repositories {
    // Needed to fetch dependency from local m2
    mavenLocal()
    // Only needed if you also implement external dependencies through m2 center repo
    mavenCentral()
}

dependencies {
    implementation "me.animaengine:anima:1.0"
}
```

Maven

```xml

<dependencies>
    <dependency>
        <groupId>me.animaengine</groupId>
        <artifactId>anima</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>
```

4. Use Anima!

## How to use

To use Anima you simply run Animas main core using

```kotlin
Anima.run(YourMainClassAnnotatedWithSpringBootApplicationAnnotation, "Window title")
```

And your good to go, now anima is running, and you can start coding up your game.  
Please refer to the wiki for detailed information on how to use each Anima component.

## Examples

If your looking for an example project, you can take a look into the `/tests` directory, there you can find a fully
working example on how you might implement Anima!

Before you start, make sure your environment is set to support JDK 21!  

If you're using IDEA you can simply use the provided run configuration `run test` to run the test game and see for
yourself.

## Endnote

Keep in mind that Anima is still under development and may change from time to time.