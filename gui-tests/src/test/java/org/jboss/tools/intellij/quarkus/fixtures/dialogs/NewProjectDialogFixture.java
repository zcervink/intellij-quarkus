package org.jboss.tools.intellij.quarkus.fixtures.dialogs;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;

@DefaultXpath(by = "MyDialog type", xpath = "//div[@accessiblename='New Project' and @class='MyDialog']")
@FixtureName(name = "Dialog")
public class NewProjectDialogFixture extends CommonContainerFixture {
    public NewProjectDialogFixture(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
    }

    public ComponentFixture toolComboBox() {
        return find(ComponentFixture.class, byXpath("//div[@accessiblename='Tool:' and @class='ComboBox']"));
    }

    public JTextFieldFixture projectNameField() {
        return find(JTextFieldFixture.class, byXpath("//div[@accessiblename='Project name:' and @class='JTextField']"));
    }

    public JTextFieldFixture projectLocationField() {
        return find(JTextFieldFixture.class, byXpath("//div[@accessiblename='Project location:' and @class='JTextField']"));
    }

    public ComponentFixture extensionCategoriesJBList() {
        List<ComponentFixture> allJBLists = findAll(ComponentFixture.class, byXpath("//div[@class='JBList']"));
        return allJBLists.get(0);
    }

    public ComponentFixture projectTypeList() {
        return find(ComponentFixture.class, byXpath("JBList", "//div[@class='JBList']"));
    }

    public ComponentFixture extensionsTable() {
        return find(ComponentFixture.class, byXpath("//div[@class='ExtensionsTable']"));
    }

    public ComponentFixture theFrameworksTree() {
        return find(ComponentFixture.class, byXpath("//div[@accessiblename='Additional Libraries and Frameworks:' and @class='FrameworksTree']"));
    }
}