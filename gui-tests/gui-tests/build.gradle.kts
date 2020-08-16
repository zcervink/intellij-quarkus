import org.jetbrains.intellij.tasks.*
import com.redhat.devtools.intellij.quarkus.*

intellij {
    pluginName = "Quarkus Tools"
    version = "2019.2"
    downloadSources = true
    type = "IС"

    updateSinceUntilBuild = false

    setPlugins(
        "com.intellij.testGuiFramework:0.10.1@nightly"
    )
}

tasks.withType<RunIdeTask> {
    jvmArgs("-Xmx1g")
    args(execArguments())
}

val testsJar = tasks.create("guiTestJar", Jar::class) {
    group = "build"
    classifier = "tests"
    from(sourceSets["test"].output)
    exclude("testData/*")
}

tasks.withType<PrepareSandboxTask> {
    from(testsJar) {
        into("Test GUI Framework/lib")
    }
}

tasks.withType<Test> {
    environment["GUI_TEST_DATA_DIR"] = projectDir.absolutePath + "/src/test/resources/ide/ui/"
    systemProperties(jbProperties<Any>().also { it["idea.gui.tests.gradle.runner"] = true })
}
