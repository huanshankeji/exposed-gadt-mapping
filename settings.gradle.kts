pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("public-open-source-dependency-repositories") version
        "0.12.0-dev-commit-b1d2ff00e3ad91229d65136b8735ad189f2b0262"
}

publicOpenSourceDependencyRepositories {
    huanshankejiMavenLocal()
    githubPackages("exposed-gadt-mapping")
    mavenCentralExcludingHuanshankejiNonStable()
}

rootProject.name = "exposed-gadt-mapping"
include("lib")
project(":lib").name = rootProject.name
