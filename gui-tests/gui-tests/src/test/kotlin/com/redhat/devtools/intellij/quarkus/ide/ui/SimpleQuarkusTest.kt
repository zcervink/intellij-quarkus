package com.redhat.devtools.intellij.quarkus.ide.ui

import com.intellij.testGuiFramework.fixtures.IdeFrameFixture
import com.intellij.testGuiFramework.fixtures.ProjectViewFixture
import com.intellij.testGuiFramework.fixtures.newProjectWizard.NewProjectWizardFixture
import com.intellij.testGuiFramework.framework.Timeouts
import com.intellij.testGuiFramework.impl.*
import com.intellij.testGuiFramework.util.*
import org.fest.swing.timing.Timeout
import org.junit.Test
import com.redhat.devtools.intellij.quarkus.GuiTestsTestBase

class SimpleQuarkusTest : GuiTestsTestBase() {

    @Test
    fun createNewQuarkusProject() {
        step("Create new Quarkus project") {
            welcomeFrame {
                actionLink("Create New Project").click()
            }
            newProjectWizardFrame {
                selectProjectType("Quarkus")
                clickNext()
                clickNext()
                clickNext()
                setProjectName("simple-quarkus-project")
                clickFinish()
            }
        }
    }

    fun newProjectWizardFrame(timeout: Timeout = Timeouts.minutes05, body: NewProjectWizardFixture.() -> Unit) {
        body(findNewProjectWizardFrame(timeout))
    }

    fun findNewProjectWizardFrame(timeout: Timeout = Timeouts.minutes05): NewProjectWizardFixture {
        return NewProjectWizardFixture.find(robot())
    }
}
