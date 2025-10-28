import com.huanshankeji.team.ShreckYe
import com.huanshankeji.team.setUpPomForTeamDefaultOpenSource

plugins {
    kotlin("jvm")
    `java-library`
    id("com.huanshankeji.maven-central-publish-conventions")
    id("com.huanshankeji.team.with-group")
    id("com.huanshankeji.team.default-github-packages-maven-publish")
}

repositories {
    mavenLocal()
    mavenCentral()
}
// commented out as it may slow down the build, especially when the GitHub token is incorrect and authentication fails
//repositoriesAddTeamGithubPackagesMavenRegistry("kotlin-common")

kotlin.jvmToolchain(8)

version = projectVersion

mavenPublishing.pom {
    setUpPomForTeamDefaultOpenSource(
        project,
        "Exposed GADT mapping",
        "mappings between data entities and tables with support for generalized algebraic data types based on Exposed DSL",
        "2023"
    ) {
        ShreckYe()
    }
}
