pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("public-open-source-dependency-repositories") version
        "0.12.0-dev-commit-e2dcb9d3d327edd99d80a6dfa041fc47c2db7bbb-dirty-SNAPSHOT"
}

publicOpenSourceDependencyRepositories {
    huanshankejiMavenLocal()
    githubPackages("exposed-gadt-mapping")
    mavenCentralExcludingHuanshankejiNonStable()
}

rootProject.name = "exposed-gadt-mapping"
include("lib")
project(":lib").name = rootProject.name
