plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.20"
    application
    id("io.ktor.plugin") version "3.1.2"
}

group = "io.github.MH321Productions"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("io.github.pdvrieze.xmlutil:core:0.91.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("io.github.irgaly.kfswatch:kfswatch:1.4.0")

    implementation("com.miglayout:miglayout-swing:11.4.2")
    implementation("com.formdev:flatlaf:3.6.1")
    implementation("com.formdev:flatlaf-extras:3.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.2")

    implementation("com.github.Dansoftowner:jSystemThemeDetector:3.9.1")
    implementation("org.slf4j:slf4j-jdk14:2.0.17")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "io.github.mh321Productions.jellyfinCustomShowCreator.MainKt"
}

ktor {
    fatJar {
        archiveFileName = "JellyfinCustomShowCreator.jar"
    }
}