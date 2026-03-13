# Copilot Instructions for exposed-gadt-mapping

## Repository Overview

This is a **Kotlin/JVM library** that provides mappings between data entities and database tables with support for **GADT (Generalized Algebraic Data Types)**. It's built on top of [JetBrains Exposed DSL](https://github.com/JetBrains/Exposed) and serves as an alternative to Exposed DAO with advanced functional programming features like nested properties, type parameters with inference, and sealed classes.

**Repository Stats:**
- Size: ~11MB (excluding build artifacts)
- Primary language: Kotlin
- Build system: Gradle 9.1.0 with Kotlin DSL
- JVM toolchain: JDK 8 (compilation target), with JDK 17 also used in CI
- Source files: ~6 main Kotlin files, 1 test file
- Framework: Exposed v1.0.0-rc-2
- Status: Highly experimental, APIs subject to change

## Key Build & Validation Commands

### Prerequisites
- JDK 8 and JDK 17 must be installed (CI uses Temurin distributions)
- The project uses Gradle Wrapper - **always use `./gradlew`**, never a system Gradle installation
- Gradle configuration cache is enabled (`org.gradle.configuration-cache=true`)

### Essential Commands (in order of frequency of use)

**1. Run all checks (verification):**
```bash
./gradlew check
```
- Duration: ~4-5 seconds (cached), ~25-30 seconds (clean build)
- Runs: compilation, tests, binary compatibility validation (apiCheck)
- **Always run this before committing code changes**

**2. Clean build:**
```bash
./gradlew clean build
```
- Duration: ~4-5 seconds with warm cache, ~40 seconds with cold cache (--refresh-dependencies)
- Use after making dependency changes or when build state seems corrupted

**3. Run tests only:**
```bash
./gradlew test
```
- Note: Currently configured with `failOnNoDiscoveredTests = false` because there are limited tests
- The test file `lib/src/test/kotlin/com/huanshankeji/exposed/datamapping/classproperty/Examples.kt` contains example usage code but has a TODO comment about connecting to a database with Testcontainers

**4. Binary API compatibility check:**
```bash
./gradlew apiCheck
```
- Duration: ~1-2 seconds
- This project uses Kotlin Binary Compatibility Validator
- The API surface is tracked in `lib/api/exposed-gadt-mapping.api`
- **Critical:** If you modify public APIs, `apiCheck` (and therefore `check`) will fail because the `.api` file is out of date — see **API Change Policy** below for how to handle this

**5. Publish to Maven Local (for testing in dependent projects):**
```bash
./gradlew publishToMavenLocal
```
- Duration: ~17-18 seconds
- Publishes the library to `~/.m2/repository`
- Note: Dokka may show warnings about failing to download package-list from external sites - this is expected and harmless

**6. Generate API documentation:**
```bash
./gradlew :dokkaGeneratePublicationHtml
```
- Duration: ~12 seconds
- Output: `build/dokka/html/`
- Expected warnings about package-list downloads are harmless

### API Change Policy

If `./gradlew check` fails **solely** due to `apiCheck` failures (because public APIs have changed and the `.api` file is out of date), **do NOT run `apiDump` automatically**. Instead, validate using:

```bash
./gradlew test
./gradlew publishToMavenLocal
```

> **Note:** This repo currently has no real tests (`failOnNoDiscoveredTests = false`), so `./gradlew test` will pass trivially. The key fallback validation is `./gradlew publishToMavenLocal`.

Then leave the `apiDump` step to the human developer to perform after they have reviewed the API changes. Running `apiDump` automatically generates unnecessary Git-tracked churn before the developer has had a chance to review the API surface.

**Only run `apiDump` if you are very confident you have completely and correctly finished all the task goals and no further API edits from the developer will be needed.**

To update the API dump file (human-initiated only):
```bash
./gradlew apiDump
```

### Common Build Issues & Solutions

**Issue: Gradle daemon issues or stale build state**
- Solution: `./gradlew clean build --no-configuration-cache`

**Issue: Dependency resolution problems**
- Solution: `./gradlew clean build --refresh-dependencies`

**Issue: "Could not parse POM" errors with Kotlin 2.0.20**
- This is noted in `buildSrc/build.gradle.kts` - the project uses Kotlin 2.2.21 to avoid this issue

## Project Structure & Architecture

### Directory Layout

```
/
├── .github/
│   └── workflows/              # CI/CD workflows
│       ├── kotlin-jvm-ci.yml   # Main CI: runs check with JDK 8 & 17
│       └── dokka-gh-pages.yml  # Deploys docs to GitHub Pages (on 'release' branch)
├── buildSrc/                   # Build configuration and convention plugins
│   ├── build.gradle.kts        # Kotlin 2.2.21, common-gradle-dependencies
│   └── src/main/kotlin/
│       └── conventions.gradle.kts  # Shared build conventions, sets JVM toolchain to 8
├── lib/                        # Main library module (named "exposed-gadt-mapping")
│   ├── api/
│   │   └── exposed-gadt-mapping.api  # Binary compatibility baseline
│   ├── build.gradle.kts        # Library dependencies and configuration
│   └── src/
│       ├── main/kotlin/com/huanshankeji/exposed/datamapping/
│       │   ├── DataMapperInterfaces.kt           # Core mapper interfaces
│       │   ├── Table.kt                          # Table extension utilities
│       │   └── classproperty/
│       │       ├── ClassPropertyMapping.kt       # Main mapping implementation (~36KB, core logic)
│       │       ├── SimpleClassPropertyMapping.kt # Simplified mapping utilities
│       │       ├── ReflectionFunctionInvocation.kt
│       │       └── UpdateBuilder.kt              # UpdateBuilder extensions
│       └── test/kotlin/com/huanshankeji/exposed/datamapping/classproperty/
│           └── Examples.kt                       # Example usage (not a real test)
├── build.gradle.kts            # Root project config, Dokka setup
├── settings.gradle.kts         # Project name and structure
└── gradle.properties           # Gradle configuration (configuration-cache enabled)
```

### Key Files to Know

**Build & Configuration:**
- `buildSrc/src/main/kotlin/conventions.gradle.kts` - Controls JVM toolchain (set to 8), Maven publishing, versioning
- `lib/build.gradle.kts` - Main dependencies: exposed-core, exposed-jdbc, kotlin-common libraries
- `gradle.properties` - Enables Gradle configuration cache (critical for performance)

**Source Code:**
- `ClassPropertyMapping.kt` - The largest file (~36KB), contains core reflection-based mapping logic with many TODOs
- `DataMapperInterfaces.kt` - Defines the mapper contract: `DataMapper<T>`, `DataQueryMapper<T>`, etc.
- Code uses heavy reflection and has several unchecked cast warnings (expected for this type of library)

**Documentation:**
- `README.md` - Contains usage examples and API documentation link
- `CONTRIBUTING.md` - References GitHub Actions workflow files for JDK version requirements
- `CHANGELOG.md` - Recently renamed from "exposed-adt-mapping" to "exposed-gadt-mapping" (v0.4.0)

### Dependencies

**Main runtime dependencies:**
- `org.jetbrains.exposed:exposed-core:1.0.0-rc-2`
- `org.jetbrains.exposed:exposed-jdbc:1.0.0-rc-2`
- `com.huanshankeji:kotlin-common-reflect:0.7.0`
- `com.huanshankeji:kotlin-common-core:0.7.0`
- Transitive: kotlin-stdlib 2.2.21, kotlin-reflect 2.1.0, kotlinx-coroutines-core, slf4j-api

**Important:** The README notes that users should stick to Exposed v0.56.0 (though the codebase now uses v1.0.0-rc-2 as of v0.4.0) because Exposed has frequent incompatible changes.

## CI/CD Workflows

### Main CI Workflow (`.github/workflows/kotlin-jvm-ci.yml`)

**Trigger:** Push to any branch (pull_request commented out)

**Jobs:**
1. **test-and-check:**
   - Runs on: `ubuntu-latest`
   - Uses custom action: `huanshankeji/.github/actions/gradle-test-and-check@v0.2.0`
   - JDK versions: 8-temurin, 17-temurin
   - Implicitly runs: `./gradlew check` (test + apiCheck)

2. **dependency-submission:**
   - Runs on: `ubuntu-latest`
   - Submits dependency graph to GitHub
   - JDK versions: 8-temurin, 17-temurin

**To replicate CI locally:**
```bash
# Ensure JDK 8 and 17 are available
./gradlew check
```

### Documentation Deployment (`.github/workflows/dokka-gh-pages.yml`)

**Trigger:** Push or PR to `release` branch, or manual workflow_dispatch

**What it does:**
- Generates Dokka HTML documentation with `./gradlew :dokkaGeneratePublicationHtml`
- Deploys to GitHub Pages at `https://huanshankeji.github.io/exposed-gadt-mapping/`

## Common Patterns & Conventions

### Code Style
- No specific linter configuration (no ktlint, detekt, or .editorconfig found)
- Follow existing Kotlin conventions visible in the codebase
- Extensive use of generics, type parameters, and reflection

### Testing Approach
- **Critical:** There are currently NO real tests - `test { failOnNoDiscoveredTests = false }`
- The test file `Examples.kt` is sample usage code with a TODO about Testcontainers
- When adding code, consider adding tests but note the existing testing infrastructure is minimal

### Known Code Patterns
- Many TODOs in `ClassPropertyMapping.kt` about refactoring ADT normalization, supporting open/abstract classes
- Unchecked cast warnings are expected (2-3 warnings during compilation)
- Heavy use of Kotlin reflection (`KProperty`, `KClass`, `KFunction`)

### Binary Compatibility
- The project uses `kotlinx.binary-compatibility-validator` version 0.18.1
- Public API changes will cause `apiCheck` to fail; the API surface is tracked in `lib/api/exposed-gadt-mapping.api`
- **Do NOT run `apiDump` automatically** — leave it for the human developer to run after reviewing API changes (see **API Change Policy** above)

## Making Changes

### Workflow for Code Changes
1. Make your changes to Kotlin source files
2. Run `./gradlew check` to verify compilation and API compatibility
3. If `check` fails solely due to `apiCheck` (public APIs changed), do **not** run `apiDump` — instead validate with `./gradlew test` and `./gradlew publishToMavenLocal`, then leave `apiDump` for the human developer to run after reviewing API changes (see **API Change Policy**)
4. If adding dependencies, verify they're compatible with Exposed v1.0.0-rc-2
5. Test in a dependent project using `./gradlew publishToMavenLocal`

### Dependency Changes
- Edit `lib/build.gradle.kts` for main dependencies
- Edit `buildSrc/build.gradle.kts` for build plugin dependencies
- Be cautious with Exposed version changes - document compatibility

### Documentation Changes
- Update `README.md` if changing public APIs or usage patterns
- Update `CHANGELOG.md` following existing format
- Regenerate Dokka docs if needed: `./gradlew :dokkaGeneratePublicationHtml`

## Critical Notes

1. **Always use `./gradlew`** - The wrapper ensures Gradle 9.1.0 is used
2. **JVM Toolchain is 8** - Despite using Kotlin 2.2.21, the compilation target is Java 8
3. **No real tests exist** - Your changes won't be validated by comprehensive tests
4. **API stability matters** - Run `apiCheck` before committing; if it fails due to API changes, follow the **API Change Policy** — do **not** run `apiDump` automatically
5. **Exposed version coupling** - Be very careful changing the Exposed dependency version
6. **Configuration cache is enabled** - If you see issues, try `--no-configuration-cache`
7. **Limited team resources** - Per CONTRIBUTING.md, this is a small team with limited capacity
8. **Experimental status** - README warns that APIs are subject to change and users should expect bugs

## Performance Notes
- Initial Gradle sync: ~12-15 seconds (downloading Gradle, building buildSrc)
- Incremental builds with configuration cache: ~4-5 seconds
- Clean build: ~4-5 seconds (cached dependencies), ~25-40 seconds (cold cache)
- Publishing to Maven Local: ~17-18 seconds
- Dokka documentation generation: ~12 seconds

## Trust These Instructions
These instructions are comprehensive and validated by running actual build commands. Only search for additional information if:
- You encounter a specific error not covered here
- You need to understand implementation details of the mapping logic
- The instructions appear outdated (check git history for recent changes)
