package org.jboss.tools.intellij.quarkus;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.fixtures.JRadioButtonFixture;
import org.jboss.tools.intellij.quarkus.fixtures.dialogs.NewProjectDialogFixture;
import org.jboss.tools.intellij.quarkus.fixtures.dialogs.WelcomeFrameDialogFixture;
import org.jboss.tools.intellij.quarkus.fixtures.quarkus.DownloadingOptionsDialogFixture;
import org.jboss.tools.intellij.quarkus.utils.*;
import org.jboss.tools.intellij.quarkus.utils.quarkus.Utils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.time.Duration;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;
import static com.intellij.remoterobot.stepsProcessing.StepWorkerKt.step;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BasicTests {

    private static RemoteRobot robot;

    @BeforeAll
    public static void connect() throws InterruptedException {
        robot = new RemoteRobot("http://127.0.0.1:8082");
        for (int i = 0; i < 60; i++) {
            try {
                robot.find(WelcomeFrameDialogFixture.class);
            } catch (Exception ex) {
                Thread.sleep(1000);
            }
        }
    }

    @AfterAll
    public static void quitTheIde() {
        GlobalIdeUtils.quitIntelliJFromTheWelcomeDialog(robot);
    }

    @Test
    public void createAQuarkusProjectAndBuildItUsingMaven() {
        step("Create a Quarkus project and build it using maven", () -> {
            Utils.createNewQuarkusProject(robot, BuildUtils.ToolToBuildTheProject.MAVEN);
            GlobalIdeUtils.waitUntilTheProjectImportIsComplete(robot);
            GlobalIdeUtils.closeTheTipOfTheDayDialog(robot);
            GlobalIdeUtils.maximizeTheIdeWindow(robot);
            GlobalIdeUtils.waitUntilAllTheBgTasksFinish(robot);
            BuildUtils.buildTheProject(robot, BuildUtils.ToolToBuildTheProject.MAVEN);
            GlobalIdeUtils.waitUntilAllTheBgTasksFinish(robot);
            BuildUtils.testIfBuildIsSuccessful(robot);
            GlobalIdeUtils.checkForExceptions(robot);
            GlobalIdeUtils.closeTheProject(robot);
            ProjectToolWindowUtils.clearTheWorkspace(robot);
        });
    }

    //@Test
    public void testIfTheQuarkusRuntimeIsUpToDate() {
        step("Test if the Quarkus runtime is up to date", () -> {
            final WelcomeFrameDialogFixture welcomeFrameDialogFixture = robot.find(WelcomeFrameDialogFixture.class);
            welcomeFrameDialogFixture.createNewProjectLink().click();
            final NewProjectDialogFixture newProjectDialogFixture = welcomeFrameDialogFixture.find(NewProjectDialogFixture.class, Duration.ofSeconds(20));

            NewProjectDialogUtils.selectNewProjectType(robot, "Java");

            // get the current runtime version
            String currentVersion = "";

            newProjectDialogFixture.theFrameworksTree().findText("Quarkus").click();
            Point quarkusCheckboxLocation = newProjectDialogFixture.theFrameworksTree().findText("Quarkus").getPoint();
            quarkusCheckboxLocation.x = 10;
            newProjectDialogFixture.theFrameworksTree().click(quarkusCheckboxLocation);

            JRadioButtonFixture theDownloadRadioButton = newProjectDialogFixture.find(JRadioButtonFixture.class, byXpath("//div[@accessiblename='Download' and @class='JRadioButton' and @text='Download']"), Duration.ofSeconds(5));
            theDownloadRadioButton.click();

            newProjectDialogFixture.button("Configure...").click();

            DownloadingOptionsDialogFixture dodf = newProjectDialogFixture.find(DownloadingOptionsDialogFixture.class);
            currentVersion = HelperUtils.listOfRemoteTextToString(dodf.nameField().findAllText());
            dodf.button("Cancel").click();
            newProjectDialogFixture.button("Cancel").click();

            // get latest version from mvn repository
            String artifactGroup = "io.quarkus";
            String artifactName = "quarkus-core";
            String latestVersion = HelperUtils.getLatestVersionFromMvnRepository(artifactGroup, artifactName);

            assertTrue(currentVersion.equals(latestVersion), "Newest version (" + latestVersion + ") of the quarkus runtime is available. You have the "
                    + currentVersion + " version.");
        });
    }

    //@Test
    public void testIfTheQuarkusRuntimeIsDownloaded() {
        step("Test if the Quarkus runtime is downloaded", () -> {
            // create new Java Quarkus project, select to download the runtime
            String projectName = "java-project-with-quarkus-runtime";
            String runtimeJarName = Utils.createNewJavaProjectWithQuarkus(robot, projectName);

            GlobalIdeUtils.waitUntilTheProjectImportIsComplete(robot);
            GlobalIdeUtils.closeTheTipOfTheDayDialog(robot);
            GlobalIdeUtils.waitUntilAllTheBgTasksFinish(robot);
            GlobalIdeUtils.maximizeTheIdeWindow(robot);


            assertTrue(ProjectToolWindowUtils.isAProjectFilePresent(robot, projectName, "lib", runtimeJarName), "The runtime has not been downloaded.");
            GlobalIdeUtils.closeTheProject(robot);
            ProjectToolWindowUtils.clearTheWorkspace(robot);
        });
    }

    //@Test
    public void createAQuarkusProjectAndBuildItUsingGradle() {
        step("Create a Quarkus project and build it using gradle", () -> {
            Utils.createNewQuarkusProject(robot, BuildUtils.ToolToBuildTheProject.GRADLE);
            GlobalIdeUtils.waitUntilTheProjectImportIsComplete(robot);
            GlobalIdeUtils.closeTheTipOfTheDayDialog(robot);
            GlobalIdeUtils.waitUntilAllTheBgTasksFinish(robot);
            GlobalIdeUtils.maximizeTheIdeWindow(robot);


            // not finished yet - there is an issues with the combo box
            // for now 20 second sleep and do it manually
            //PreferencesDialogUtils.makeSureGradleUsesJ11(robot);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            BuildUtils.buildTheProject(robot, BuildUtils.ToolToBuildTheProject.GRADLE);
            GlobalIdeUtils.waitUntilAllTheBgTasksFinish(robot);
            BuildUtils.testIfBuildIsSuccessful(robot);
            GlobalIdeUtils.checkForExceptions(robot);
            GlobalIdeUtils.closeTheProject(robot);
            ProjectToolWindowUtils.clearTheWorkspace(robot);
        });
    }
}