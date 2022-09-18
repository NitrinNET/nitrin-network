import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("maven-publish")
}

group = "net.nitrin"
version = "0.0.1"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    api("io.netty:netty-all:4.1.81.Final")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}



publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.nitrin"
            artifactId = "nitrin-network"
            version = "0.0.1"

            from(components["kotlin"])
        }
    }
}