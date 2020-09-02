package org.jboss.tools.intellij.quarkus.fixtures.dialogs;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.CommonContainerFixture;
import com.intellij.remoterobot.fixtures.ComponentFixture;
import com.intellij.remoterobot.fixtures.DefaultXpath;
import com.intellij.remoterobot.fixtures.FixtureName;
import org.jetbrains.annotations.NotNull;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;

@DefaultXpath(by = "JDialog type", xpath = "//div[@accessiblename='This should not be shown' and @class='JDialog']")
@FixtureName(name = "JDialog")
public class JRootPaneMainJDialogFixture extends CommonContainerFixture {
    public JRootPaneMainJDialogFixture(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
    }

    public ComponentFixture confirmExitOfTheIde() {
        return find(ComponentFixture.class, byXpath("//div[@accessiblename='Exit' and @class='JButton' and @name='Exit' and @text='Exit']"));
    }
}