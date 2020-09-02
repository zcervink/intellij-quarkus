package org.jboss.tools.intellij.quarkus.utils.quarkus;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.fixtures.JRadioButtonFixture;
import com.intellij.remoterobot.fixtures.dataExtractor.RemoteText;
import com.intellij.remoterobot.utils.Keyboard;
import org.jboss.tools.intellij.quarkus.fixtures.dialogs.NewProjectDialogFixture;
import org.jboss.tools.intellij.quarkus.fixtures.dialogs.WelcomeFrameDialogFixture;
import org.jboss.tools.intellij.quarkus.fixtures.quarkus.DownloadingOptionsDialogFixture;
import org.jboss.tools.intellij.quarkus.utils.BuildUtils;
import org.jboss.tools.intellij.quarkus.utils.NewProjectDialogUtils;
import org.jboss.tools.intellij.quarkus.utils.ProjectToolWindowUtils;

import java.awt.*;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;
import static com.intellij.remoterobot.stepsProcessing.StepWorkerKt.step;

public class Utils {

    public static void createNewQuarkusProject(RemoteRobot remoteRobot, BuildUtils.ToolToBuildTheProject toolToBuildTheProject) {
        step("Create New Quarkus Project", () -> {
            final WelcomeFrameDialogFixture welcomeFrameDialogFixture = remoteRobot.find(WelcomeFrameDialogFixture.class);
            welcomeFrameDialogFixture.createNewProjectLink().click();
            final NewProjectDialogFixture newProjectDialogFixture = welcomeFrameDialogFixture.find(NewProjectDialogFixture.class, Duration.ofSeconds(20));

            NewProjectDialogUtils.selectNewProjectType(remoteRobot, "Quarkus");
            newProjectDialogFixture.button("Next").click();

            switch (toolToBuildTheProject) {
                case GRADLE:
                    newProjectDialogFixture.toolComboBox().click();
                    new Keyboard(remoteRobot).hotKey(40);
                    break;
                case MAVEN:
                    break;
            }

            newProjectDialogFixture.button("Next").click();
            selectQuarkusExtensions(newProjectDialogFixture, 2, new int[]{0, 1, 3});
            selectQuarkusExtensions(newProjectDialogFixture, 10, new int[]{1, 2, 3});


            newProjectDialogFixture.button("Next").click();
            String newProjectName = "code-with-quarkus-" + toolToBuildTheProject.toString().toLowerCase();
            newProjectDialogFixture.projectNameField().setText(newProjectName);

            ProjectToolWindowUtils.projectPath = newProjectDialogFixture.projectLocationField().getText();
            newProjectDialogFixture.button("Finish").click();
        });
    }

    public static String createNewJavaProjectWithQuarkus(RemoteRobot remoteRobot, String projectName) {
        final WelcomeFrameDialogFixture welcomeFrameDialogFixture = remoteRobot.find(WelcomeFrameDialogFixture.class);
        welcomeFrameDialogFixture.createNewProjectLink().click();
        final NewProjectDialogFixture newProjectDialogFixture = welcomeFrameDialogFixture.find(NewProjectDialogFixture.class, Duration.ofSeconds(20));
        NewProjectDialogUtils.selectNewProjectType(remoteRobot, "Java");

        newProjectDialogFixture.theFrameworksTree().findText("Quarkus").click();
        Point quarkusCheckboxLocation = newProjectDialogFixture.theFrameworksTree().findText("Quarkus").getPoint();
        quarkusCheckboxLocation.x = 10;
        newProjectDialogFixture.theFrameworksTree().click(quarkusCheckboxLocation);

        JRadioButtonFixture theDownloadRadioButton = newProjectDialogFixture.find(JRadioButtonFixture.class, byXpath("//div[@accessiblename='Download' and @class='JRadioButton' and @text='Download']"), Duration.ofSeconds(10));
        theDownloadRadioButton.click();
        newProjectDialogFixture.button("Configure...").click();

        DownloadingOptionsDialogFixture dodf = newProjectDialogFixture.find(DownloadingOptionsDialogFixture.class);
        String runtimeJarName = dodf.filesToDownload().findAllText().get(0).getText();
        dodf.button("Cancel").click();
        newProjectDialogFixture.button("Next").click();

        newProjectDialogFixture.projectNameField().setText(projectName);
        newProjectDialogFixture.button("Finish").click();

        return runtimeJarName;
    }

    public static void selectQuarkusExtensions(NewProjectDialogFixture newProjectDialogFixture, int categoryIndex, int[] extensionsIndexes) {
        step("Create New Quarkus Project", () -> {
            List<String> extensionCategoriesRenderedText = newProjectDialogFixture.extensionCategoriesJBList().findAllText()
                    .stream()
                    .map(RemoteText::getText)
                    .collect(Collectors.toList());

            newProjectDialogFixture.extensionCategoriesJBList().findText(extensionCategoriesRenderedText.get(categoryIndex)).click();

//        List<String> extensionsRenderedText = newProjectDialogFixture.extensionsTable().findAllText()
//                .stream()
//                .map(RemoteText::getText)
//                .filter(item -> !item.trim().isEmpty())
//                .collect(Collectors.toList());

            for (int index : extensionsIndexes) {
                Point positionInTheExtensionsTable = new Point(10, 10 + index * 24);
                newProjectDialogFixture.extensionsTable().click(positionInTheExtensionsTable);
            }
        });
    }
}