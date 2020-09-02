package org.jboss.tools.intellij.quarkus.fixtures.dialogs;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.CommonContainerFixture;
import com.intellij.remoterobot.fixtures.ComponentFixture;
import com.intellij.remoterobot.fixtures.DefaultXpath;
import com.intellij.remoterobot.fixtures.FixtureName;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;

@DefaultXpath(by = "MyDialog type", xpath = "//div[@accessiblename='IDE Fatal Errors' and @class='MyDialog']")
@FixtureName(name = "IDE Fatal Errors Dialog")
public class IdeFatalErrorsDialogFixture extends CommonContainerFixture {
    public IdeFatalErrorsDialogFixture(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
    }

    public ComponentFixture numberOfExcetionsJBLabel() {
        List<ComponentFixture> allJBLabels = findAll(ComponentFixture.class, byXpath("//div[@class='JBLabel']"));
        return allJBLabels.get(0);
    }

    public ComponentFixture exceptionDescriptionJTextArea() {
        return find(ComponentFixture.class, byXpath("//div[@class='JTextArea']"));
    }

    public ComponentFixture previousExceptionButton() {
        return find(ComponentFixture.class, byXpath("//div[@accessiblename='Previous' and @class='ActionButton' and @myaction='Previous (null)']"));
    }

    public ComponentFixture nextExceptionButton() {
        return find(ComponentFixture.class, byXpath("//div[@accessiblename='Next' and @class='ActionButton' and @myaction='Next (null)']"));
    }
}