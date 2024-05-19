val pkgPath = "https://maven.pkg.github.com/FlauschigDings/eucalyptus"

plugins {
    kotlin("jvm") version "1.9.23"
    `maven-publish`
}

group = "de.flauschig.eucalyptus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri(pkgPath)
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri(pkgPath)
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
