plugins {
    kotlin("jvm") version "1.9.23"
    id("me.champeau.jmh") version "0.7.0"
    `maven-publish` // Apply the maven-publish plugin
}

group = "de.flauschig.eucalyptus"
version = System.getenv("VERSION")?: "1.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    runtimeOnly(kotlin("reflect"))
    testImplementation(kotlin("test"))
}
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/FlauschigDings/eucalyptus")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            artifactId = "core"
            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}