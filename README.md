[![Maven metadata URL](https://img.shields.io/maven-central/v/com.theonlytails/ketex?color=blue&style=for-the-badge)](https://search.maven.org/artifact/com.theonlytails/lootgoblin)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/TheOnlyTails/ketex/Java%20CI%20with%20Gradle?label=gradle%20build&logo=github&style=for-the-badge)
![Kotlin](https://img.shields.io/badge/kotlin-%238052ff.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Gradle](https://img.shields.io/badge/gradle-%2302303A.svg?style=for-the-badge&logo=gradle&logoColor=white)
![GitHub License](https://img.shields.io/github/license/theonlytails/ketex?style=for-the-badge&logo=key)

---

# Ketex

An idiomatic Kotlin DSL for creating regular expressions. 
 
For documentation and usage instructions, please take a look at
the [docs](https://ketex.theonlytails.com/).

Here's
the [`maven-metadata.xml`](https://s01.oss.sonatype.org/service/local/repositories/releases/content/com/theonlytails/ketex/maven-metadata.xml)
of this library.

## Installation

_Don't forget to replace the VERSION key with the version in the top with the Maven Central badge at the top!_

**WARNING**: this library requires you to enable context receivers, a new experminental feature available in Kotlin/JVM since v1.6.20.

#### Gradle/Kotlin
```kotlin
repositories {
    mavenCentral()
}

dependencies {
	implementation(group = "com.theonlytails", name = "ketex", version = "VERSION")
}
```

#### Gradle/Groovy

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation fg.deobf("com.theonlytails:ketex:VERSION")
}
```
