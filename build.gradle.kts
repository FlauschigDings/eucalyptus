plugins {
    kotlin("jvm") version "1.9.23"
    `maven-publish` // Apply the maven-publish plugin
}

group = "de.flauschig.eucalyptus"
version = System.getenv("VERSION")?: "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
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
