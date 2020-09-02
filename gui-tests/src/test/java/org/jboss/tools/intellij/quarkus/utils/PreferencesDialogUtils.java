package org.jboss.tools.intellij.quarkus.utils;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.utils.Keyboard;
import org.jboss.tools.intellij.quarkus.fixtures.dialogs.PreferencesDialogFixture;

import java.awt.event.KeyEvent;
import java.time.Duration;

import static com.intellij.remoterobot.stepsProcessing.StepWorkerKt.step;

public class PreferencesDialogUtils {

    public static void openACategory(RemoteRobot remoteRobot, String... path) {
        step("Open a category in the preferences", () -> {
            final PreferencesDialogFixture preferencesDialogFixture = remoteRobot.find(PreferencesDialogFixture.class, Duration.ofSeconds(10));

            for (String node : path) {
                preferencesDialogFixture.settingsTreeView().findText(node).doubleClick();
            }
        });
    }

    public static void openTheBuildGradleCategory(RemoteRobot remoteRobot) {
        step("Open the build gradle category in the preferences", () -> {
            openACategory(remoteRobot, "Build, Execution, Deployment", "Build Tools", "Gradle");
        });
    }

    public static void openThePreferencesDialog(RemoteRobot remoteRobot) {
        step("Open the preferences dialog", () -> {
            Keyboard kb = new Keyboard(remoteRobot);
            final int VK_COMMA = 0xBC;

            if (remoteRobot.isMac()) {
                kb.hotKey(KeyEvent.VK_META, KeyEvent.VK_COMMA);
            } else {
                kb.hotKey(KeyEvent.VK_CONTROL, KeyEvent.VK_COMMA);
            }
        });
    }

    public static void confirmTheDialog(RemoteRobot remoteRobot) {
        step("Confirm the Preferences dialog by click on the OK button", () -> {
            final PreferencesDialogFixture preferencesDialogFixture = remoteRobot.find(PreferencesDialogFixture.class, Duration.ofSeconds(10));
            preferencesDialogFixture.button("OK").click();
        });
    }

    public static void makeSureGradleUsesJ11(RemoteRobot remoteRobot) {
        step("Make sure the build gradle JVM is J11", () -> {
            PreferencesDialogUtils.openThePreferencesDialog(remoteRobot);
            PreferencesDialogUtils.openTheBuildGradleCategory(remoteRobot);


            // check/change the settings
            final PreferencesDialogFixture preferencesDialogFixture = remoteRobot.find(PreferencesDialogFixture.class, Duration.ofSeconds(10));
            String comboBoxText = OtherUtils.listOfRemoteTextToString(preferencesDialogFixture.gradleJVMComboBox().findAllText());
            preferencesDialogFixture.gradleJVMComboBox().click();
            //new Keyboard(remoteRobot).hotKey(49);
            //new Keyboard(remoteRobot).hotKey(49);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            new Keyboard(remoteRobot).enter();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            PreferencesDialogUtils.confirmTheDialog(remoteRobot);
        });
    }
}