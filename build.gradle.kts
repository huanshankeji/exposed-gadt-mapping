tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}

plugins {
    id("org.jetbrains.dokka")
    id("com.huanshankeji.team.root-project-conventions")
}

dependencies {
    dokka(project(":exposed-gadt-mapping"))
}
