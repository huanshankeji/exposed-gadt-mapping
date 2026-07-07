import com.huanshankeji.gitversioning.projectVersionFromGitProvider
import com.huanshankeji.team.ShreckYe
import com.huanshankeji.team.setUpPomForTeamDefaultOpenSource

plugins {
    id("com.huanshankeji.team.with-group")
    id("maven-central")
    id("com.huanshankeji.team.github.packages.maven.publish")
    id("dokka-convention")
    id("com.huanshankeji.maven-central-publish-conventions")
}

version = projectVersionFromGitProvider(projectBaseVersion).get()

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
