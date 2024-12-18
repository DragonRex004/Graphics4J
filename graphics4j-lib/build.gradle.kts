plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version ("7.1.2")
}

group = "de.dragonrex.lib"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":graphics4j-api"))
    implementation("io.github.libsdl4j:libsdl4j:2.28.4-1.6")
}


tasks {
    build {
        dependsOn(shadowJar)
    }
}
