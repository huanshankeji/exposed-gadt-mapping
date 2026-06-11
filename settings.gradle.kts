pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("public-open-source-dependency-repositories") version
        "0.13.0-dev-commit-dcac1d6c7871d46082c1fc71b411077daa199c6f"
}

publicOpenSourceDependencyRepositories {
    huanshankejiMavenLocal()
    githubPackages("exposed-gadt-mapping")
    mavenCentralExcludingHuanshankejiNonStable()
}

rootProject.name = "exposed-gadt-mapping"
include("lib")
project(":lib").name = rootProject.name
