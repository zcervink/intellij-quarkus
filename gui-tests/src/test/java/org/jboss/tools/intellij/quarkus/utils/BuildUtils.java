package org.jboss.tools.intellij.quarkus.utils;

import com.intellij.remoterobot.RemoteRobot;
import org.jboss.tools.intellij.quarkus.fixtures.mainIdeWindow.ToolWindowsPaneFixture;

import static com.intellij.remoterobot.stepsProcessing.StepWorkerKt.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuildUtils {

    public static void testIfBuildIsSuccessful(RemoteRobot remoteRobot) {
        step("Test if the build is successful", () -> {
            final ToolWindowsPaneFixture toolWindowsPaneFixture = remoteRobot.find(ToolWindowsPaneFixture.class);
            String theRunConsoleOutput = OtherUtils.listOfRemoteTextToString(toolWindowsPaneFixture.theRunConsole().findAllText());
            assertTrue(theRunConsoleOutput.contains("BUILD SUCCESS"), "The build should be successful, but is not.");
        });
    }

    public static void buildTheProject(RemoteRobot remoteRobot, ToolToBuildTheProject toolToBuildTheProject) {
        step("Build the project", () -> {
            switch (toolToBuildTheProject) {
                case MAVEN:
                    step("Open the Maven tab and build the project", () -> {
                        final ToolWindowsPaneFixture toolWindowsPaneFixture = remoteRobot.find(ToolWindowsPaneFixture.class);
                        toolWindowsPaneFixture.stripeButton("Maven").click();
                        toolWindowsPaneFixture.mavenTabTree().findText("code-with-quarkus").doubleClick();
                        toolWindowsPaneFixture.mavenTabTree().findText("Lifecycle").doubleClick();
                        toolWindowsPaneFixture.mavenTabTree().findText("install").doubleClick();
                    });
                    break;
                case GRADLE:
                    step("Open the Gradle tab and build the project", () -> {
                        final ToolWindowsPaneFixture toolWindowsPaneFixture = remoteRobot.find(ToolWindowsPaneFixture.class);
                        toolWindowsPaneFixture.stripeButton("Gradle").click();
                        toolWindowsPaneFixture.gradleTabTree().findText("code-with-quarkus").doubleClick();
                        toolWindowsPaneFixture.gradleTabTree().findText("Tasks").doubleClick();
                        toolWindowsPaneFixture.gradleTabTree().findText("build").doubleClick();
                        toolWindowsPaneFixture.gradleTabTree().findAllText("build").get(1).doubleClick();
                    });
                    break;
            }

            waitUntilTheBuildHasFinished(remoteRobot, 300);
        });
    }

    private static void waitUntilTheBuildHasFinished(RemoteRobot remoteRobot, int timeoutInSeconds) {
        final int SLEEP_SECONDS = 3;
        String buildStatusTreeText;
        int buildTimeElapsed = 0;

        do {
            buildStatusTreeText = getBuildStatusTreeText(remoteRobot);

            try {
                Thread.sleep(SLEEP_SECONDS * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            buildTimeElapsed += SLEEP_SECONDS;
            assertTrue(buildTimeElapsed <= timeoutInSeconds, "The build timeout is up.");

        } while (!buildStatusTreeText.equals(getBuildStatusTreeText(remoteRobot)));
    }

    private static String getBuildStatusTreeText(RemoteRobot remoteRobot) {
        final ToolWindowsPaneFixture toolWindowsPaneFixture = remoteRobot.find(ToolWindowsPaneFixture.class);
        String buildStatusTreeText = OtherUtils.listOfRemoteTextToString(toolWindowsPaneFixture.theBuildStatusTree().findAllText());
        return buildStatusTreeText;
    }

    public enum ToolToBuildTheProject {
        MAVEN,
        GRADLE
    }
}