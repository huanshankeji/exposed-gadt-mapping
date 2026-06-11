plugins {
    `jvm-conventions`
}

dependencies {
    implementation(commonDependencies.exposed.core())
    implementation(commonDependencies.exposed.module("jdbc"))
    //implementation(commonDependencies.kotlinCommon.exposed())
    implementation(commonDependencies.kotlinCommon.reflect())
    implementation(commonDependencies.kotlinCommon.core())
}

// remove when there are tests
tasks.test {
    failOnNoDiscoveredTests = false
}
