package com.redhat.devtools.intellij.quarkus

import com.intellij.testGuiFramework.framework.*
import com.intellij.testGuiFramework.launcher.ide.CommunityIde
import org.junit.runner.RunWith
import org.junit.runners.Suite
import com.redhat.devtools.intellij.quarkus.ide.ui.*

@RunWithIde(CommunityIde::class)
@RunWith(GuiTestSuiteRunner::class)
@Suite.SuiteClasses(PrepareSandbox::class)
class PrepareSandboxTestSuite : GuiTestSuite()
