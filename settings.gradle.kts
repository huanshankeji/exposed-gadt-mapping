@file:OptIn(com.huanshankeji.GradleCommonExperimentalApi::class)

import com.huanshankeji.artifacts.mavenRepositoryHandlerContext
import com.huanshankeji.team.artifacts.mavenCentralExcludingHuanshankeji
import com.huanshankeji.team.gitversioning.opensourcemavenconvention.githubpackages.huanshankejiGithubPackagesOpenSourceMavenConventionProjectRepositories

pluginManagement {
    repositories {
        gradlePluginPortal()
        exclusiveContent {
            forRepository {
                mavenLocal()
            }
            forRepository {
                maven {
                    // Resolves the gradle-common settings plugin when it is not in mavenLocal().
                    // Mirrors gradle-common credential resolution; its APIs cannot be called from settings.gradle.kts:
                    // https://github.com/huanshankeji/gradle-common/blob/main/kotlin-common/gradle-library/src/main/kotlin/com/huanshankeji/github/packages/maven/GithubPackagesMavenRegistry.kt
                    url = uri("https://maven.pkg.github.com/huanshankeji/gradle-common")
                    credentials {
                        with(providers) {
                            username = gradleProperty("gpr.user").orElse(gradleProperty("gprUser")).getOrNull()
                            password = gradleProperty("gpr.key").orElse(gradleProperty("gprKey")).getOrNull()
                        }
                    }
                }
            }
            filter {
                includeVersionByRegex("""com\.huanshankeji(\..+)?""", ".*", """.*-dev-commit-[0-9a-f]+.*""")
            }
        }
    }
}

buildscript {
    val gradleCommonPluginsVersion =
        "0.12.0-dev-commit-656d3d5f54d76c571b79f96ecc236cb54b013f50"
    dependencies {
        classpath("com.huanshankeji.team:settings-gradle-plugins:$gradleCommonPluginsVersion")
    }
}

plugins {
    val gradleCommonPluginsVersion =
        "0.12.0-dev-commit-656d3d5f54d76c571b79f96ecc236cb54b013f50"
    id("com.huanshankeji.base-settings-conventions") version gradleCommonPluginsVersion
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
