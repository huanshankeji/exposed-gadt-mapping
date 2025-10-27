import com.huanshankeji.team.ShreckYe
import com.huanshankeji.team.pomForTeamDefaultOpenSource

plugins {
    id("com.huanshankeji.kotlin-jvm-library-sonatype-ossrh-publish-conventions")
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

publishing.publications.withType<MavenPublication> {
    pomForTeamDefaultOpenSource(
        project,
        "Exposed GADT mapping",
        "mappings between data entities and tables with support for generalized algebraic data types based on Exposed DSL",
        "2023"
    ) {
        ShreckYe()
    }
}
