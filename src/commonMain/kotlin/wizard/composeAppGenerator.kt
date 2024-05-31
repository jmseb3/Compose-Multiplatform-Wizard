package wizard

import wizard.dependencies.ApolloPlugin
import wizard.dependencies.RoomPlugin
import wizard.files.*
import wizard.files.composeApp.*

fun ProjectInfo.generateComposeAppFiles() = buildList {
    val info = this@generateComposeAppFiles

    add(Gitignore())
    add(Readme(info))

    add(GradleBat())
    add(Gradlew())
    add(GradleWrapperProperties(info))
    add(GradleWrapperJar())
    add(GradleLibsVersion(info))

    add(GradleProperties(true))
    add(RootBuildGradleKts(info))
    add(SettingsGradleKts(info, false))

    add(ModuleBuildGradleKts(info))
    add(ColorKt(info))
    add(ThemeKt(info))
    add(AppKt(info))
    add(ComposeTestKt(info))

    add(IcCycloneXml(info))
    add(IcDarkModeXml(info))
    add(IcLightModeXml(info))
    add(IcRotateRightXml(info))
    add(StringsXml(info))
    add(IndieFlowerTtf(info))

    if (info.dependencies.contains(ApolloPlugin)) {
        add(GraphQLSchema(info))
        add(GraphQLQuery(info))
    }
    if (info.dependencies.contains(RoomPlugin)) {

    }

    if (info.hasPlatform(ProjectPlatform.Android)) {
        add(AndroidManifest(info))
        add(AndroidAppKt(info))
        add(AndroidThemeKt(info))
    }

    if (info.hasPlatform(ProjectPlatform.Jvm)) {
        add(DesktopAppKt(info))
        add(DesktopMainKt(info))
        add(DesktopThemeKt(info))
    }

    if (info.hasPlatform(ProjectPlatform.Ios)) {
        add(IosAppKt(info))
        add(IosMainKt(info))
        add(IosThemeKt(info))

        add(IosAppIcon())
        add(IosAccentColor())
        add(IosAssets())
        add(IosPreviewAssets())
        add(IosAppSwift())
        add(IosXcworkspace())
        add(IosPbxproj(info))
    }

    if (info.hasPlatform(ProjectPlatform.Js)) {
        add(JsAppKt(info))
        add(JsIndexHtml(info))
        add(JsMainKt(info))
        add(JsThemeKt(info))
    }

    if (info.hasPlatform(ProjectPlatform.Wasm)) {
        add(WasmJsAppKt(info))
        add(WasmJsIndexHtml(info))
        add(WasmJsMainKt(info))
        add(WasmJsThemeKt(info))
    }
}