# Repository Guidelines

Before working in this repository, **load and read** the [Huanshankeji general agent instructions](https://github.com/huanshankeji/.github/blob/main/docs/general-agent-instructions.md) as the required organization-wide baseline. A skipped or failed load, or an unread document, is a blocking error until the document is in context and has been read.

## Project Structure

Exposed GADT mapping is a Kotlin/JVM library that maps data entities to tables using the Exposed DSL. The `lib/` directory is the single library module, registered as `:exposed-gadt-mapping` in Gradle. Production code lives in `lib/src/main/kotlin/com/huanshankeji/exposed/datamapping/`, with reflection-based mappers in `classproperty/`. Usage examples live in the matching package under `lib/src/test/kotlin/`. Build conventions and dependency versions live in `buildSrc/`, and GitHub Actions workflows live in `.github/workflows/`.

## Build and Development

Use the checked-in Gradle wrapper from the repository root. CI uses Temurin 17 to run Gradle and Temurin 8 for the library toolchain.

- `./gradlew build` compiles, packages, and verifies the library.
- `./gradlew check` runs the Gradle verification lifecycle. `./gradlew :exposed-gadt-mapping:test` runs the module's test task on its own.
- Run `./gradlew checkKotlinAbi` to compare the current ABI with the tracked reference snapshots. For intentional ABI changes, run `./gradlew updateKotlinAbi` and review the generated diff before including the updated snapshots. See [Kotlin’s ABI validation guide](https://kotlinlang.org/docs/gradle-binary-compatibility-validation.html).
- `./gradlew publishToMavenLocal` publishes the library for use in a local consumer project.

The library’s reference snapshot is `lib/api/exposed-gadt-mapping.api`.

To resolve `*-dev-commit-*` Huanshankeji dependencies from GitHub Packages, configure `gpr.user` and `gpr.key` in `~/.gradle/gradle.properties` with a token that has `read:packages`. See [CONTRIBUTING.md](CONTRIBUTING.md) for setup details.

## Kotlin Style

Match surrounding Kotlin and Gradle Kotlin DSL code. Use four spaces for indentation, `PascalCase` for types, and `camelCase` for functions and properties. Keep package names lowercase and aligned with directory paths.

## Testing and Examples

`Examples.kt` demonstrates nested mappings and CRUD with Exposed. The build compiles the examples and allows zero discovered tests. The example's `main` needs a database connection before it can run. For mapping changes, exercise the affected behavior in a consumer or adapt the sample, then record what you verified.

## Commits and Pull Requests

Use short imperative commit subjects, such as `Upgrade Gradle wrapper to 9.9.1`. Describe the change and verification in the pull request, link any related issue or discussion, and call out intentional compatibility changes. For larger proposals, open an issue or discussion first.
