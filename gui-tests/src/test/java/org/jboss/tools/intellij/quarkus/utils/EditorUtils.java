package org.jboss.tools.intellij.quarkus.utils;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.fixtures.ComponentFixture;
import org.jboss.tools.intellij.quarkus.fixtures.mainIdeWindow.EditorFixtureFixture;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;
import static com.intellij.remoterobot.stepsProcessing.StepWorkerKt.step;

public class EditorUtils {

    public static void switchBetweenOpenEditors(RemoteRobot remoteRobot, String editorLabel) {
        step("Switch between editors that are currently open", () -> {
            final EditorFixtureFixture editorFixtureFixture = remoteRobot.find(EditorFixtureFixture.class);
            editorFixtureFixture.singleHeightLabel(editorLabel).click();
        });
    }

    public static void closeEditor(RemoteRobot remoteRobot, String editorLabel) {
        step("Close editor with given label", () -> {
            final EditorFixtureFixture editorFixtureFixture = remoteRobot.find(EditorFixtureFixture.class);
            editorFixtureFixture.singleHeightLabel(editorLabel)
                    .find(ComponentFixture.class, byXpath("//div[@accessiblename='Close. Alt-Click to Close Others' and @class='InplaceButton']")).click();
        });
    }
}
