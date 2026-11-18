# Repository Guidelines

## Project Structure

Exposed GADT mapping is a Kotlin/JVM library that maps data entities to tables using the Exposed DSL. The `lib/` directory is the single library module, registered as `:exposed-gadt-mapping` in Gradle. Production code lives in `lib/src/main/kotlin/com/huanshankeji/exposed/datamapping/`, with reflection-based mappers in `classproperty/`. Usage examples live in the matching package under `lib/src/test/kotlin/`. Build conventions and dependency versions live in `buildSrc/`, and GitHub Actions workflows live in `.github/workflows/`.

## Build and Development

Use the checked-in Gradle wrapper from the repository root. CI uses Temurin 17 to run Gradle and Temurin 8 for the library toolchain.

- `./gradlew build` compiles, packages, and verifies the library.
- `./gradlew check` runs the Gradle verification lifecycle. `./gradlew :exposed-gadt-mapping:test` runs the module's test task on its own.
- `./gradlew :exposed-gadt-mapping:checkKotlinAbi` compares the public ABI with `lib/api/exposed-gadt-mapping.api`. Run `./gradlew :exposed-gadt-mapping:updateKotlinAbi` for intentional ABI changes and review the resulting diff.
- `./gradlew publishToMavenLocal` publishes the library for use in a local consumer project.

To resolve `*-dev-commit-*` Huanshankeji dependencies from GitHub Packages, configure `gpr.user` and `gpr.key` in `~/.gradle/gradle.properties` with a token that has `read:packages`. See [CONTRIBUTING.md](CONTRIBUTING.md) for setup details.

## Kotlin Style

Match surrounding Kotlin and Gradle Kotlin DSL code. Use four spaces for indentation, `PascalCase` for types, and `camelCase` for functions and properties. Keep package names lowercase and aligned with directory paths.

## Testing and Examples

`Examples.kt` demonstrates nested mappings and CRUD with Exposed. The build compiles the examples and allows zero discovered tests. The example's `main` needs a database connection before it can run. For mapping changes, exercise the affected behavior in a consumer or adapt the sample, then record what you verified.

## Commits and Pull Requests

Use short imperative commit subjects, such as `Upgrade Gradle wrapper to 9.9.1`. Describe the change and verification in the pull request, link any related issue or discussion, and call out intentional compatibility changes. For larger proposals, open an issue or discussion first.
