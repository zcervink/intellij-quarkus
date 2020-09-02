package org.jboss.tools.intellij.quarkus.fixtures.other;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.CommonContainerFixture;
import com.intellij.remoterobot.fixtures.DefaultXpath;
import com.intellij.remoterobot.fixtures.FixtureName;
import org.jetbrains.annotations.NotNull;

@DefaultXpath(by = "HeavyWeightWindow type", xpath = "//div[@class='HeavyWeightWindow']")
@FixtureName(name = "Context Menu")
public class ContextMenuFixture extends CommonContainerFixture {
    public ContextMenuFixture(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
    }
}