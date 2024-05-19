plugins {
    kotlin("jvm") version "1.9.23"
    `maven-publish` // Apply the maven-publish plugin
}

group = "de.flauschig.eucalyptus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}
/*
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/FlauschigDings/eucalyptus")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getProperty("USERNAME")
                password = project.findProperty("gpr.password") as String? ?: System.getProperty("PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
 */
publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
        }
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/FlauschigDings/eucalyptus")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
