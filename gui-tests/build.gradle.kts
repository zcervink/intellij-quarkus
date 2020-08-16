import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

group = "com.redhat.devtools.intellij.quarkus"

plugins {
    id("tanvd.kosogor") version "1.0.7" apply true
    kotlin("jvm") version "1.3.41" apply true
}

allprojects {
    repositories {
        mavenCentral()
    }

    apply {
        plugin("tanvd.kosogor")
        plugin("org.jetbrains.intellij")
        plugin("kotlin")
    }

    tasks.withType<KotlinJvmCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}
