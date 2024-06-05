package wizard.dependencies

import wizard.Dependency
import wizard.ProjectPlatform.*

val KotlinxDateTime = Dependency(
    title = "Kotlinx DateTime",
    description = "A multiplatform Kotlin library for working with date and time.",
    url = "https://github.com/Kotlin/kotlinx-datetime",
    group = "org.jetbrains.kotlinx",
    id = "kotlinx-datetime",
    version = "0.6.0",
    catalogVersionName = "kotlinx-datetime",
    catalogName = "kotlinx-datetime",
    platforms = emptySet()
)

val KtorCore = Dependency(
    title = "Ktor client",
    description = "A multiplatform asynchronous HTTP client, which allows you to make requests and handle responses.",
    url = "https://github.com/ktorio/ktor",
    group = "io.ktor",
    id = "ktor-client-core",
    version = "3.0.0-wasm2",
    catalogVersionName = "ktor",
    catalogName = "ktor-core",
    platforms = emptySet()
)

val KtorClientDarwin = KtorCore.copy(
    id = "ktor-client-darwin",
    catalogName = "ktor-client-darwin",
    platforms = setOf(Ios, Macos)
)

val KtorClientOkhttp = KtorCore.copy(
    id = "ktor-client-okhttp",
    catalogName = "ktor-client-okhttp",
    platforms = setOf(Android, Jvm)
)

val KtorClientJs = KtorCore.copy(
    id = "ktor-client-js",
    catalogName = "ktor-client-js",
    platforms = setOf(Js)
)

val KtorClientLinux = KtorCore.copy(
    id = "ktor-client-curl",
    catalogName = "ktor-client-curl",
    platforms = setOf(Linux)
)

val KtorClientMingw = KtorCore.copy(
    id = "ktor-client-winhttp",
    catalogName = "ktor-client-winhttp",
    platforms = setOf(Mingw)
)

val KotlinxCoroutinesCore = Dependency(
    title = "Kotlinx Coroutines",
    description = "Library support for Kotlin coroutines with multiplatform support.",
    url = "https://github.com/Kotlin/kotlinx.coroutines",
    group = "org.jetbrains.kotlinx",
    id = "kotlinx-coroutines-core",
    version = "1.9.0-RC",
    catalogVersionName = "kotlinx-coroutines",
    catalogName = "kotlinx-coroutines-core",
    platforms = emptySet()
)

val KotlinxCoroutinesAndroid = KotlinxCoroutinesCore.copy(
    id = "kotlinx-coroutines-android",
    catalogName = "kotlinx-coroutines-android",
    platforms = setOf(Android)
)

val KotlinxCoroutinesJvm = KotlinxCoroutinesCore.copy(
    id = "kotlinx-coroutines-swing",
    catalogName = "kotlinx-coroutines-swing",
    platforms = setOf(Jvm)
)

val KotlinxCoroutinesTest = KotlinxCoroutinesCore.copy(
    id = "kotlinx-coroutines-test",
    catalogName = "kotlinx-coroutines-test",
    platforms = emptySet(),
    isTestDependency = true
)

val KotlinxSerializationPlugin = Dependency(
    title = "Kotlinx Serialization",
    description = "Kotlin serialization consists of a compiler plugin.",
    url = "https://github.com/Kotlin/kotlinx.serialization",
    group = "org.jetbrains.kotlin.plugin.serialization",
    id = "gradle-plugin",
    version = "[the same as Kotlin version!]",
    catalogVersionName = "kotlin",
    catalogName = "kotlinx-serialization",
    platforms = emptySet()
)

val KotlinxSerializationJson = KotlinxSerializationPlugin.copy(
    description = "A multiplatform JSON serialization library.",
    group = "org.jetbrains.kotlinx",
    id = "kotlinx-serialization-json",
    version = "1.7.0-RC",
    catalogVersionName = "kotlinx-serialization",
    catalogName = "kotlinx-serialization-json",
    platforms = emptySet()
)
