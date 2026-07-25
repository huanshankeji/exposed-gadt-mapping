plugins {
    id("com.huanshankeji.root-project-conventions")
    id("org.jetbrains.dokka")
}

dependencies {
    dokka(project(":exposed-gadt-mapping"))
}
