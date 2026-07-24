import com.huanshankeji.gitversioning.devCommitOrReleaseVersionProvider
import com.huanshankeji.team.ShreckYe
import com.huanshankeji.team.setUpPomForTeamDefaultOpenSource
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    kotlin("jvm")
    `java-library`
    id("com.huanshankeji.team.with-group")
    id("com.huanshankeji.team.gitversioning.opensourceconvention.githubpackages.publish")
}

kotlin {
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation()
}

kotlin.jvmToolchain(8)

version = providers.devCommitOrReleaseVersionProvider(projectBaseVersion, isRelease).get()

gitVersioningOpenSourceConventionGithubPackagesPublish {
    signAllPublicationsIfRelease(isRelease)
}

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
