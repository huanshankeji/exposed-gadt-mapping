plugins {
    conventions
    id("com.huanshankeji.team.dokka.github-dokka-convention")
}

dependencies {
    implementation(commonDependencies.exposed.core())
    implementation(commonDependencies.exposed.module("jdbc"))
    api(commonDependencies.kotlinCommon.exposed())
    implementation(commonDependencies.kotlinCommon.reflect())
    implementation(commonDependencies.kotlinCommon.core())
}

// remove when there are tests
tasks.test {
    failOnNoDiscoveredTests = false
}
