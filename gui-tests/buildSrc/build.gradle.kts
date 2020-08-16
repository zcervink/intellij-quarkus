repositories {
    gradlePluginPortal()
}

plugins {
    `kotlin-dsl` apply true
}

dependencies {
    api("gradle.plugin.org.jetbrains.intellij.plugins", "gradle-intellij-plugin", "0.4.9")
}
