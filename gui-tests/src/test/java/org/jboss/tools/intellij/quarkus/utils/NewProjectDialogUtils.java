package org.jboss.tools.intellij.quarkus.utils;

import com.intellij.remoterobot.RemoteRobot;
import org.jboss.tools.intellij.quarkus.fixtures.dialogs.NewProjectDialogFixture;

public class NewProjectDialogUtils {

    public static void selectNewProjectType(RemoteRobot remoteRobot, String projectType) {
        NewProjectDialogFixture newProjectDialogFixture = remoteRobot.find(NewProjectDialogFixture.class);
        newProjectDialogFixture.projectTypeList().findText(projectType).click();
    }
}