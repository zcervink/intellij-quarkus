package org.jboss.tools.intellij.quarkus.utils;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.fixtures.ComponentFixture;
import com.intellij.remoterobot.utils.Keyboard;
import com.intellij.remoterobot.utils.WaitForConditionTimeoutException;
import org.jboss.tools.intellij.quarkus.fixtures.dialogs.IdeFatalErrorsDialogFixture;
import org.jboss.tools.intellij.quarkus.fixtures.dialogs.JRootPaneMainJDialogFixture;
import org.jboss.tools.intellij.quarkus.fixtures.dialogs.TipOfTheDayDialogFixture;
import org.jboss.tools.intellij.quarkus.fixtures.dialogs.WelcomeFrameDialogFixture;
import org.jboss.tools.intellij.quarkus.fixtures.mainIdeWindow.IdeStatusBarFixture;

import java.awt.event.KeyEvent;
import java.time.Duration;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;
import static com.intellij.remoterobot.stepsProcessing.StepWorkerKt.step;

public class GlobalIdeUtils {

    public static void quitIntelliJFromTheIde(RemoteRobot remoteRobot) {
        step("Quit IntelliJ Idea from the IDE", () -> {
            Keyboard kb = new Keyboard(remoteRobot);

            if (remoteRobot.isMac()) {
                kb.hotKey(KeyEvent.VK_META, KeyEvent.VK_Q);
            } else {
                kb.hotKey(KeyEvent.VK_CONTROL, KeyEvent.VK_Q);
            }

            final JRootPaneMainJDialogFixture JRootPaneMainJDialogFixture = remoteRobot.find(JRootPaneMainJDialogFixture.class);
            JRootPaneMainJDialogFixture.confirmExitOfTheIde().click();
        });
    }

    public static void quitIntelliJFromTheWelcomeDialog(RemoteRobot remoteRobot) {
        step("Quit IntelliJ Idea from the welcome dialog", () -> {
            WelcomeFrameDialogFixture welcomeFrameDialogFixture = remoteRobot.find(WelcomeFrameDialogFixture.class);
            if (remoteRobot.isMac()) {
                welcomeFrameDialogFixture.runJs("robot.click(component, new Point(15, 10), MouseButton.LEFT_BUTTON, 1);");
            } else if (remoteRobot.isWin()) {
                welcomeFrameDialogFixture.runJs("const horizontal_offset = component.getWidth()-24;\n" +
                        "robot.click(component, new Point(horizontal_offset, 18), MouseButton.LEFT_BUTTON, 1);");
            } else {
                welcomeFrameDialogFixture.runJs("const horizontal_offset = component.getWidth()-18;\n" +
                        "robot.click(component, new Point(horizontal_offset, 18), MouseButton.LEFT_BUTTON, 1);");
            }
        });
    }

    public static void closeTheTipOfTheDayDialog(RemoteRobot remoteRobot) {
        step("Close the 'Tip of the Day' Dialog", () -> {
            final TipOfTheDayDialogFixture tipOfTheDayDialogFixture = remoteRobot.find(TipOfTheDayDialogFixture.class, Duration.ofSeconds(20));
            tipOfTheDayDialogFixture.button("Close").click();
        });
    }

    public static void closeTheProject(RemoteRobot remoteRobot) {
        step("Close the project that is currently open", () -> {
            ComponentFixture cf = remoteRobot.find(ComponentFixture.class, byXpath("//div[@class='IdeFrameImpl']"));
            if (remoteRobot.isMac()) {
                cf.runJs("robot.click(component, new Point(15, 10), MouseButton.LEFT_BUTTON, 1);");
            } else if (remoteRobot.isWin()) {
                cf.runJs("const horizontal_offset = component.getWidth()-24;\n" +
                        "robot.click(component, new Point(horizontal_offset, 18), MouseButton.LEFT_BUTTON, 1);");
            } else {
                cf.runJs("const horizontal_offset = component.getWidth()-18;\n" +
                        "robot.click(component, new Point(horizontal_offset, 18), MouseButton.LEFT_BUTTON, 1);");
            }
        });
    }

    public static void waitUntilTheProjectImportIsComplete(RemoteRobot remoteRobot) {
        step("Wait until the project import is complete", () -> {
            while (true) {
                try {
                    ComponentFixture cf = remoteRobot.find(ComponentFixture.class, byXpath("//div[@class='EngravedLabel']"));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (WaitForConditionTimeoutException e) {
                    // the importing of the project has finished -> exit the loop
                    return;
                }
            }
        });
    }

    public static void maximizeTheIdeWindow(RemoteRobot remoteRobot) {
        ComponentFixture cf = remoteRobot.find(ComponentFixture.class, byXpath("//div[@class='IdeFrameImpl']"));
        cf.runJs("const horizontal_offset = component.getWidth()/2;\n" +
                "robot.click(component, new Point(horizontal_offset, 10), MouseButton.LEFT_BUTTON, 2);");
    }

    public static void waitUntilAllTheBgTasksFinish(RemoteRobot remoteRobot) {
        step("Wait until all the background tasks finish", () -> {
            final IdeStatusBarFixture ideStatusBarFixture = remoteRobot.find(IdeStatusBarFixture.class);

            byte numberOfSecondsWithNoBgTask = 0;
            final byte BREAK_WAITING_AFTER_SECONDS = 5;

            do {
                try {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        ideStatusBarFixture.bgTasksIcon();

                        numberOfSecondsWithNoBgTask = 0;
                    }
                } catch (WaitForConditionTimeoutException e) {
                    numberOfSecondsWithNoBgTask++;
                }
            } while (numberOfSecondsWithNoBgTask < BREAK_WAITING_AFTER_SECONDS);
        });
    }

    public static void checkForExceptions(RemoteRobot remoteRobot) {
        step("Check for exceptions", () -> {
            final IdeStatusBarFixture ideStatusBarFixture = remoteRobot.find(IdeStatusBarFixture.class);
            try {
                ideStatusBarFixture.ideErrorsIcon();
            } catch (WaitForConditionTimeoutException e) {
                return;
            }
            ideStatusBarFixture.ideErrorsIcon().click();


            final IdeFatalErrorsDialogFixture ideFatalErrorsDialogFixture = remoteRobot.find(IdeFatalErrorsDialogFixture.class, Duration.ofSeconds(10));
            String exceptionNumberLabel = HelperUtils.listOfRemoteTextToString(ideFatalErrorsDialogFixture.numberOfExcetionsJBLabel().findAllText());
            int numberOfExceptions = Integer.parseInt(exceptionNumberLabel.substring(5));

            for (int i = 0; i < numberOfExceptions; i++) {
                String exceptionStackTrace = HelperUtils.listOfRemoteTextToString(ideFatalErrorsDialogFixture.exceptionDescriptionJTextArea().findAllText());

                if (i + 1 < numberOfExceptions) {
                    ideFatalErrorsDialogFixture.nextExceptionButton().click();
                }
            }

            ideFatalErrorsDialogFixture.button("Clear all").click();
        });
    }
}