package com.redhat.devtools.intellij.quarkus

import org.gradle.api.Project

val Project.channel: String
    get() {
        return "nightly"
    }

inline fun <reified Value> jbProperties() = System.getProperties()
        .filterKeys { it.toString().startsWith("idea") || it.toString().startsWith("jb") }
        .filterKeys { it.toString() !in setOf("idea.paths.selector", "jb.vmOptionsFile", "idea.version", "idea.home.path") }
        .mapKeys { it.key as String }.mapValues { it.value as Value }.toMutableMap()

fun execArguments() = System.getProperty("exec.args", "").split(",")
