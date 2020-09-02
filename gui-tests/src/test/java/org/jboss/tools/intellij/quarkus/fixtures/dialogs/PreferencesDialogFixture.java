package org.jboss.tools.intellij.quarkus.fixtures.dialogs;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.CommonContainerFixture;
import com.intellij.remoterobot.fixtures.ComponentFixture;
import com.intellij.remoterobot.fixtures.DefaultXpath;
import com.intellij.remoterobot.fixtures.FixtureName;
import org.jetbrains.annotations.NotNull;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;

@DefaultXpath(by = "MyDialog type", xpath = "//div[@accessiblename='Preferences' and @class='MyDialog']")
@FixtureName(name = "Preferences Dialog")
public class PreferencesDialogFixture extends CommonContainerFixture {
    public PreferencesDialogFixture(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
    }

    public ComponentFixture settingsTreeView() {
        return find(ComponentFixture.class, byXpath("//div[@class='MyTree']"));
    }

    public ComponentFixture gradleJVMComboBox() {
        return find(ComponentFixture.class, byXpath("//div[@class='SdkComboBox']"));
    }
}