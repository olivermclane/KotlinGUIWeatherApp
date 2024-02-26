/**
 * Documentation: https://docs.gradle.org/current/samples/sample_building_kotlin_applications.html
 */

plugins {
    kotlin("jvm") version "1.9.0"
    id("application") // Make sure the application plugin is applied correctly
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.9.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain {
        // Specify the desired JVM version
        languageVersion = JavaLanguageVersion.of(8)
    }
}

application {
    // Set the main class name here
    mainClass.set("MainKt")
    }