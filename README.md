# Ethera-Engine

## TechStack

- JDK 21
- Kotlin
- Gradle (Kotlin DSL)
- SpringBoot 3
- Swing (Graphics)

## General

Ethera is a lightweight code based game engine designed to develop 2D gaming experiences.  
With SpringBoot 3 at its core, Ethera supports all mayor Java developing patterns used in enterprise solutions.

## How to implement

To implement the Ethera core into your projects, you must follow these simple steps:

1. Clone the Ethera project via git
2. Publish the project locally using gradle  
   via the gradle wrapper: `./gradlew publishToMavenLocal`  
   or using the IDEA run configuration: `publishToMavenLocal`
3. Implement Ethera as a dependency in your target project  
   Gradle (Kotlin DSL)

```kotlin
repositories {
    // Needed to fetch dependency from local m2
    mavenLocal()
    // Only needed if you also implement external dependencies through m2 center repo
    mavenCentral()
}

dependencies {
    implementation("me.etheraengine:ethera:1.0-SNAPSHOT")
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
    implementation "me.etheraengine:ethera:1.0-SNAPSHOT"
}
```

Maven

```xml

<dependencies>
    <dependency>
        <groupId>me.etheraengine</groupId>
        <artifactId>ethera</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

4. Use Ethera!

## How to use

To use Ethera you simply run Etheras main core using

```kotlin
Ethera.run(YourMainClassAnnotatedWithSpringBootApplicationAnnotation, "Window title")
```

And your good to go, now ethera is running, and you can start coding up your game.  
Please refer to the wiki for detailed information on how to use each Ethera component.

## Examples

If your looking for an example project, you can take a look into the `example` module, there you can find a fully
working example on how you might implement Ethera!

Before you start, make sure your environment is set to support JDK 21, and build one time using gradle!  
If startup fails, and you are using IDEA, you can also manually start the game by clicking the play button in the
example game class `ExampleGame`

If you're using IDEA you can simply use the provided run configuration `run example` to run the test game and see for
yourself.

## Endnote

Keep in mind that Ethera is still under development and may change from time to time.