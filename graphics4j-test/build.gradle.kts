plugins {
    id("java")
}

group = "de.dragonrex.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":graphics4j-api"))
    implementation(project(":graphics4j-lib"))
    implementation("io.github.libsdl4j:libsdl4j:2.28.4-1.6")
}