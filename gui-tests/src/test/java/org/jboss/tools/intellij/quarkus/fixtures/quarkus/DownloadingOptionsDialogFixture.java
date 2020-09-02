package org.jboss.tools.intellij.quarkus.fixtures.quarkus;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.CommonContainerFixture;
import com.intellij.remoterobot.fixtures.ComponentFixture;
import com.intellij.remoterobot.fixtures.DefaultXpath;
import com.intellij.remoterobot.fixtures.FixtureName;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;

@DefaultXpath(by = "MyDialog type", xpath = "//div[@accessiblename='Downloading Options' and @class='MyDialog']")
@FixtureName(name = "Dialog")
public class DownloadingOptionsDialogFixture extends CommonContainerFixture {
    public DownloadingOptionsDialogFixture(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
    }

    public ComponentFixture nameField() {
        return find(ComponentFixture.class, byXpath("//div[@accessiblename='Name:' and @class='JTextField']"));
    }

    public ComponentFixture filesToDownload() {
        return find(ComponentFixture.class, byXpath("//div[@accessiblename='Files to download:' and @class='CheckBoxList']"));
    }
}