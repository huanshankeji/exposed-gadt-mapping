pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("public-open-source-dependency-repositories") version
        "0.12.0-dev-commit-0bca1ba009f63b52ae3d5fe1b4cc5a466b8b61b5"
}

publicOpenSourceDependencyRepositories {
    huanshankejiMavenLocal()
    githubPackages("exposed-gadt-mapping")
    mavenCentralExcludingHuanshankejiNonStable()
}

rootProject.name = "exposed-gadt-mapping"
include("lib")
project(":lib").name = rootProject.name
