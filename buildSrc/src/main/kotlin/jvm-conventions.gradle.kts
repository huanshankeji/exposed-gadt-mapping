plugins {
    id("common-conventions")
    kotlin("jvm")
    `java-library`
    id("com.huanshankeji.kotlin-abi-validation-conventions")
}

kotlin.jvmToolchain(8)
