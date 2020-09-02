package org.jboss.tools.intellij.quarkus.utils;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.fixtures.ComponentFixture;
import com.intellij.remoterobot.utils.WaitForConditionTimeoutException;
import org.apache.commons.io.FileUtils;
import org.assertj.swing.core.MouseButton;
import org.jboss.tools.intellij.quarkus.fixtures.dialogs.WelcomeFrameDialogFixture;
import org.jboss.tools.intellij.quarkus.fixtures.mainIdeWindow.ProjectToolWindowFixture;
import org.jboss.tools.intellij.quarkus.fixtures.other.ContextMenuFixture;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;
import static com.intellij.remoterobot.stepsProcessing.StepWorkerKt.step;

public class ProjectToolWindowUtils {

    private static final int WAIT_BEFORE_MOUSE_MOVES_IN_THE_CONTEXT_MENU = 500;
    public static String projectPath = "";

    private static void navigateThroughTheProjectTree(RemoteRobot remoteRobot, ActionToPerform action, String... pathArray) {
        step("Navigate through the project tree", () -> {
            List<String> path = Arrays.asList(pathArray);
            Iterator<String> pathIterator = path.iterator();
            while (pathIterator.hasNext()) {
                final ProjectToolWindowFixture projectToolWindowFixture = remoteRobot.find(ProjectToolWindowFixture.class);
                String pathItem = pathIterator.next();

                // for last item perform different action
                if (!pathIterator.hasNext()) {
                    switch (action) {
                        case OPEN:
                            projectToolWindowFixture.projectViewTree().findText(pathItem).doubleClick();
                            break;
                        case OPEN_CONTEXT_MENU:
                            projectToolWindowFixture.projectViewTree().findText(pathItem).click(MouseButton.RIGHT_BUTTON);
                            break;
                        case HIGHLIGHT:
                            projectToolWindowFixture.projectViewTree().findText(pathItem).click();
                            break;
                    }
                } else {
                    projectToolWindowFixture.projectViewTree().findText(pathItem).doubleClick();
                }
            }
        });
    }

    public static void openAProjectFile(RemoteRobot remoteRobot, String... path) {
        step("Open a project file", () -> {
            navigateThroughTheProjectTree(remoteRobot, ActionToPerform.OPEN, path);
        });
    }

    public static boolean isAProjectFilePresent(RemoteRobot remoteRobot, String... path) {
        try {
            navigateThroughTheProjectTree(remoteRobot, ActionToPerform.HIGHLIGHT, path);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public static void openContextMenuOnAProjectFile(RemoteRobot remoteRobot, String... path) {
        step("Open context menu on a project file", () -> {
            navigateThroughTheProjectTree(remoteRobot, ActionToPerform.OPEN_CONTEXT_MENU, path);
        });
    }

    public static void navigateToInTheContextMenu(RemoteRobot remoteRobot, String... pathArray) {
        step("Click on a item in the context menu according to given path", () -> {
            List<String> path = Arrays.asList(pathArray);

            Iterator<String> pathIterator = path.iterator();
            while (pathIterator.hasNext()) {
                try {
                    Thread.sleep(WAIT_BEFORE_MOUSE_MOVES_IN_THE_CONTEXT_MENU);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<ContextMenuFixture> contextMenuFixtures = remoteRobot.findAll(ContextMenuFixture.class);
                String pathItem = pathIterator.next();

                // is last item
                if (!pathIterator.hasNext()) {
                    contextMenuFixtures.get(contextMenuFixtures.size() - 1).findText(pathItem).click();
                } else {
                    contextMenuFixtures.get(contextMenuFixtures.size() - 1).findText(pathItem).moveMouse();
                }
            }
        });
    }

    public static void clearTheWorkspace(RemoteRobot remoteRobot) {
        step("Delete all the projects in the workspace", () -> {
            // delete all the projects' links from the 'Welcome to IntelliJ IDEA' dialog
            final WelcomeFrameDialogFixture welcomeFrameDialogFixture = remoteRobot.find(WelcomeFrameDialogFixture.class, Duration.ofSeconds(10));
            welcomeFrameDialogFixture.runJs("const horizontal_offset = component.getWidth()/2;\n" +
                    "robot.click(component, new Point(horizontal_offset, 10), MouseButton.LEFT_BUTTON, 1);");

            while (true) {
                try {
                    ComponentFixture cf = welcomeFrameDialogFixture.find(ComponentFixture.class, byXpath("//div[@accessiblename='Recent Projects' and @class='MyList']"));
                    cf.runJs("const horizontal_offset = component.getWidth()-22;\n" +
                            "robot.click(component, new Point(horizontal_offset, 22), MouseButton.LEFT_BUTTON, 1);");
                } catch (WaitForConditionTimeoutException e) {
                    break;
                }
            }

            // delete all the files and folders in the IdeaProjects folder
            try {
                String pathToDirToMakeEmpty = System.getProperty("user.home") + File.separator + "IdeaProjects";
                FileUtils.cleanDirectory(new File(pathToDirToMakeEmpty));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private enum ActionToPerform {
        OPEN, OPEN_CONTEXT_MENU, HIGHLIGHT
    }
}