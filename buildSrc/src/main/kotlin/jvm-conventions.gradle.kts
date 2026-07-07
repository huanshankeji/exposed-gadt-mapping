import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    id("common-conventions")
    kotlin("jvm")
    `java-library`
}

kotlin {
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation()
}

kotlin.jvmToolchain(8)
