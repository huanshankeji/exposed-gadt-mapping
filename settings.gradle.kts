@file:OptIn(com.huanshankeji.GradleCommonExperimentalApi::class)

import com.huanshankeji.artifacts.mavenRepositoryHandlerContext
import com.huanshankeji.team.artifacts.mavenCentralExcludingHuanshankeji
import com.huanshankeji.team.gitversioning.opensourcemavenconvention.githubpackages.huanshankejiGithubPackagesOpenSourceMavenConventionProjectRepositories

pluginManagement {
    // Must apply inside this block: Kotlin DSL runs pluginManagement before top-level statements.
    apply(from = "gradle/classpath-bootstrap.gradle.kts")
    @Suppress("UNCHECKED_CAST")
    (extra["repositories"] as RepositoryHandler.() -> Unit)(repositories)
}

buildscript {
    dependencies {
        classpath("com.huanshankeji.team:settings-gradle-plugins:${settings.extra["gradleCommonPluginsVersion"]}")
    }
}

plugins {
    id("com.huanshankeji.base-settings-conventions") version (extra["gradleCommonPluginsVersion"] as String)
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenCentralExcludingHuanshankeji()
        mavenRepositoryHandlerContext(providers, ::uri) {
            huanshankejiGithubPackagesOpenSourceMavenConventionProjectRepositories("kotlin-common")
        }
    }
}

rootProject.name = "exposed-gadt-mapping"
include("lib")
project(":lib").name = rootProject.name
