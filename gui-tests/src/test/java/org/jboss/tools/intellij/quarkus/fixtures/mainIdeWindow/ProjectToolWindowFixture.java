package org.jboss.tools.intellij.quarkus.fixtures.mainIdeWindow;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.CommonContainerFixture;
import com.intellij.remoterobot.fixtures.ComponentFixture;
import com.intellij.remoterobot.fixtures.DefaultXpath;
import com.intellij.remoterobot.fixtures.FixtureName;
import org.jetbrains.annotations.NotNull;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;

@DefaultXpath(by = "InternalDecorator type", xpath = "//div[@accessiblename='Project Tool Window' and @class='InternalDecorator']")
@FixtureName(name = "Project Tool Window")
public class ProjectToolWindowFixture extends CommonContainerFixture {
    public ProjectToolWindowFixture(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
    }

    public ComponentFixture projectViewTree() {
        return find(ComponentFixture.class, byXpath("//div[@class='ProjectViewTree']"));
    }
}