package wizard.files.app

import wizard.*

class ModuleBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/build.gradle.kts"
    override val content = buildString {
        val withComposeResources = !info.hasCustomResources

        val plugins = mutableSetOf<Dependency>()
        val commonDeps = mutableSetOf<Dependency>()
        val otherDeps = mutableSetOf<Dependency>()
        info.dependencies.forEach { dep ->
            when {
                dep.isPlugin() -> plugins.add(dep)
                dep.isCommon() -> commonDeps.add(dep)
                else -> otherDeps.add(dep)
            }
        }


        if (info.hasDesktop) {
            appendLine("import org.jetbrains.compose.desktop.application.dsl.TargetFormat")
            appendLine("")
        }
        appendLine("plugins {")
        appendLine("    alias(libs.plugins.multiplatform)")
        appendLine("    alias(libs.plugins.compose)")
        if (info.hasIos) {
            appendLine("    alias(libs.plugins.cocoapods)")
        }
        if (info.hasAndroid) {
            appendLine("    alias(libs.plugins.android.application)")
        }
        plugins.forEach { dep ->
            appendLine("    ${dep.pluginNotation}")
        }
        appendLine("}")
        appendLine("")
        appendLine("@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)")
        appendLine("kotlin {")
        appendLine("    targetHierarchy.default()")
        if (info.hasAndroid) {
            appendLine("    androidTarget {")
            appendLine("        compilations.all {")
            appendLine("            kotlinOptions {")
            appendLine("                jvmTarget = \"1.8\"")
            appendLine("            }")
            appendLine("        }")
            appendLine("    }")
            appendLine("")
        }
        if (info.hasDesktop) {
            appendLine("    jvm(\"desktop\")")
            appendLine("")
        }
        if (info.hasBrowser) {
            appendLine("    js {")
            appendLine("        browser()")
            appendLine("        binaries.executable()")
            appendLine("    }")
            appendLine("")
        }
        if (info.hasIos) {
            appendLine("    iosX64()")
            appendLine("    iosArm64()")
            appendLine("    iosSimulatorArm64()")
            appendLine("")
            appendLine("    cocoapods {")
            appendLine("        version = \"1.0.0\"")
            appendLine("        summary = \"Compose application framework\"")
            appendLine("        homepage = \"empty\"")
            appendLine("        ios.deploymentTarget = \"11.0\"")
            appendLine("        podfile = project.file(\"../iosApp/Podfile\")")
            appendLine("        framework {")
            appendLine("            baseName = \"ComposeApp\"")
            appendLine("            isStatic = true")
            appendLine("        }")

            if (withComposeResources) {
                appendLine("        extraSpecAttributes[\"resources\"] = \"['src/commonMain/resources/**']\"")
            }

            appendLine("    }")
            appendLine("")
        }
        appendLine("    sourceSets {")

        if (withComposeResources) {
            appendLine("        all {")
            appendLine("            languageSettings {")
            appendLine("                optIn(\"org.jetbrains.compose.resources.ExperimentalResourceApi\")")
            appendLine("            }")
            appendLine("        }")
        }

        appendLine("        val commonMain by getting {")
        appendLine("            dependencies {")
        appendLine("                implementation(compose.runtime)")
        appendLine("                implementation(compose.material3)")

        if (withComposeResources) {
            appendLine("                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)")
            appendLine("                implementation(compose.components.resources)")
        }

        commonDeps.forEach { dep ->
            appendLine("                ${dep.libraryNotation}")
        }

        appendLine("            }")
        appendLine("        }")
        appendLine("")
        appendLine("        val commonTest by getting {")
        appendLine("            dependencies {")
        appendLine("                implementation(kotlin(\"test\"))")
        appendLine("            }")
        appendLine("        }")
        appendLine("")
        if (info.hasAndroid) {
            appendLine("        val androidMain by getting {")
            appendLine("            dependencies {")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ComposePlatform.Android)) {
                    appendLine("                ${dep.libraryNotation}")
                }
            }

            appendLine("            }")
            appendLine("        }")
            appendLine("")
        }
        if (info.hasDesktop) {
            appendLine("        val desktopMain by getting {")
            appendLine("            dependencies {")
            appendLine("                implementation(compose.desktop.common)")
            appendLine("                implementation(compose.desktop.currentOs)")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ComposePlatform.Desktop)) {
                    appendLine("                ${dep.libraryNotation}")
                }
            }

            appendLine("            }")
            appendLine("        }")
            appendLine("")
        }
        if (info.hasBrowser) {
            appendLine("        val jsMain by getting {")
            appendLine("            dependencies {")
            appendLine("                implementation(compose.html.core)")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ComposePlatform.Browser)) {
                    appendLine("                ${dep.libraryNotation}")
                }
            }

            appendLine("            }")
            appendLine("        }")
            appendLine("")
        }
        if (info.hasIos) {
            appendLine("        val iosMain by getting {")
            appendLine("            dependencies {")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ComposePlatform.Ios)) {
                    appendLine("                ${dep.libraryNotation}")
                }
            }

            appendLine("            }")
            appendLine("        }")
            appendLine("")
        }
        appendLine("    }")
        appendLine("}")
        appendLine("")
        if (info.hasAndroid) {
            appendLine("android {")
            appendLine("    namespace = \"${info.packageId}\"")
            appendLine("    compileSdk = ${info.androidTargetSdk}")
            appendLine("")
            appendLine("    defaultConfig {")
            appendLine("        minSdk = ${info.androidMinSdk}")
            appendLine("        targetSdk = ${info.androidTargetSdk}")
            appendLine("")
            appendLine("        applicationId = \"${info.packageId}.androidApp\"")
            appendLine("        versionCode = 1")
            appendLine("        versionName = \"1.0.0\"")
            appendLine("    }")
            appendLine("    sourceSets[\"main\"].apply {")
            appendLine("        manifest.srcFile(\"src/androidMain/AndroidManifest.xml\")")
            appendLine("        res.srcDirs(\"src/androidMain/resources\")")

            if (withComposeResources) {
                appendLine("        resources.srcDirs(\"src/commonMain/resources\")")
            }

            appendLine("    }")
            appendLine("    compileOptions {")
            appendLine("        sourceCompatibility = JavaVersion.VERSION_1_8")
            appendLine("        targetCompatibility = JavaVersion.VERSION_1_8")
            appendLine("    }")
            appendLine("}")
            appendLine("")
        }
        if (info.hasDesktop) {
            appendLine("compose.desktop {")
            appendLine("    application {")
            appendLine("        mainClass = \"MainKt\"")
            appendLine("")
            appendLine("        nativeDistributions {")
            appendLine("            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)")
            appendLine("            packageName = \"${info.packageId}.desktopApp\"")
            appendLine("            packageVersion = \"1.0.0\"")
            appendLine("        }")
            appendLine("    }")
            appendLine("}")
        }

        if (info.hasBrowser) {
            appendLine("")
            appendLine("compose.experimental {")
            appendLine("    web.application {}")
            appendLine("}")
        }

        if (plugins.contains(LibresPlugin)) {
            appendLine("")
            appendLine("libres {")
            appendLine("    // https://github.com/Skeptick/libres#setup")
            appendLine("}")

            if (info.hasDesktop) {
                appendLine("tasks.getByPath(\"desktopProcessResources\").dependsOn(\"libresGenerateResources\")")
                appendLine("tasks.getByPath(\"desktopSourcesJar\").dependsOn(\"libresGenerateResources\")")
            }
            if (info.hasBrowser) {
                appendLine("tasks.getByPath(\"jsProcessResources\").dependsOn(\"libresGenerateResources\")")
            }
        }

        if (plugins.contains(BuildConfigPlugin)) {
            appendLine("")
            appendLine("buildConfig {")
            appendLine("  // BuildConfig configuration here.")
            appendLine("  // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts")
            appendLine("}")
        }

        if (plugins.contains(BuildKonfigPlugin)) {
            appendLine("")
            appendLine("buildkonfig {")
            appendLine("  // BuildKonfig configuration here.")
            appendLine("  // https://github.com/yshrsmz/BuildKonfig#gradle-configuration")
            appendLine("  packageName = \"${info.packageId}\"")
            appendLine("  defaultConfigs {")
            appendLine("  }")
            appendLine("}")
        }

        if (plugins.contains(SQLDelightPlugin)) {
            appendLine("")
            appendLine("sqldelight {")
            appendLine("  databases {")
            appendLine("    create(\"MyDatabase\") {")
            appendLine("      // Database configuration here.")
            appendLine("      // https://cashapp.github.io/sqldelight")
            appendLine("      packageName.set(\"${info.packageId}.db\")")
            appendLine("    }")
            appendLine("  }")
            appendLine("}")
        }

        if (plugins.contains(ApolloPlugin)) {
            appendLine("")
            appendLine("apollo {")
            appendLine("  service(\"api\") {")
            appendLine("    // GraphQL configuration here.")
            appendLine("    // https://www.apollographql.com/docs/kotlin/advanced/plugin-configuration/")
            appendLine("    packageName.set(\"${info.packageId}.graphql\")")
            appendLine("  }")
            appendLine("}")
        }
    }
}