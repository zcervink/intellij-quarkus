package org.jboss.tools.intellij.quarkus.fixtures.mainIdeWindow;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.CommonContainerFixture;
import com.intellij.remoterobot.fixtures.ContainerFixture;
import com.intellij.remoterobot.fixtures.DefaultXpath;
import com.intellij.remoterobot.fixtures.FixtureName;
import org.jetbrains.annotations.NotNull;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;

@DefaultXpath(by = "EditorsSplitters type", xpath = "//div[@class='EditorsSplitters']")
@FixtureName(name = "EditorFixtureFixture")
public class EditorFixtureFixture extends CommonContainerFixture {
    public EditorFixtureFixture(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
    }

    public ContainerFixture singleHeightLabel(String label) {
        return find(ContainerFixture.class, byXpath("//div[@accessiblename='" + label + "' and @class='SingleHeightLabel']"));
    }
}