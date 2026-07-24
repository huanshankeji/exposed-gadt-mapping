plugins {
    id("org.jetbrains.dokka")
    id("com.huanshankeji.root-project-conventions")
}

dependencies {
    dokka(project(":exposed-gadt-mapping"))
}
