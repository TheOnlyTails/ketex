import com.vanniktech.maven.publish.JavadocJar.*
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jetbrains.dokka") version "latest.release" // dokka
    id("com.vanniktech.maven.publish.base") version "latest.release"
    `java-library`
    idea
}

val groupId = "com.theonlytails"
val libVersion = "1.1.1"

group = groupId
version = libVersion

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
    kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
}

tasks.dokkaHtml.configure {
    outputDirectory.set(projectDir.resolve("docs"))
}

// Publishing to maven central
plugins.withId("com.vanniktech.maven.publish.base") {
    group = groupId
    version = libVersion

    @Suppress("UnstableApiUsage")
    configure<MavenPublishBaseExtension> {
        configure(KotlinJvm(Dokka("dokkaHtml"), true))
        publishToMavenCentral(SonatypeHost.S01)
        signAllPublications()

        pom {
            name.set("Ketex")
            description.set("An idiomatic Kotlin DSL for creating regular expressions. ")
            url.set("https://github.com/theonlytails/ketex")
            inceptionYear.set("2022")

            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://www.opensource.org/licenses/mit-license.php")
                    distribution.set("repo")
                }
            }

            scm {
                connection.set("scm:git:git://github.com/theonlytails/ketex.git")
                developerConnection.set("scm:git:ssh://git@github.com/theonlytails/ketex.git")
                url.set("https://github.com/theonlytails/ketex/")
            }

            developers {
                developer {
                    name.set("TheOnlyTails")
                    id.set("theonlytails")
                    url.set("https://github.com/theonlytails/")
                }
            }
        }
    }
}
