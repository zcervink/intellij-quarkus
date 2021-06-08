/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.intellij.quarkus;

import com.intellij.remoterobot.RemoteRobot;
import org.jboss.tools.intellij.quarkus.utils.BuildUtils;
import org.jboss.tools.intellij.quarkus.utils.GlobalUtils;
import org.jboss.tools.intellij.quarkus.utils.ProjectToolWindowUtils;
import org.jboss.tools.intellij.quarkus.utils.QuarkusUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.intellij.remoterobot.stepsProcessing.StepWorkerKt.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Basic JUnit UI tests
 *
 * @author zcervink@redhat.com
 */
public class BasicTests {

    private static RemoteRobot robot;
    private static GlobalUtils.IdeaVersion ideaVersion;

    @BeforeAll
    public static void connect() throws InterruptedException {

        String acceptedSourceLocation = "accepted";
        String acceptedDir = System.getProperty("user.home") + "\\AppData\\Roaming\\JetBrains\\consentOptions";
        createDirectoryHierarchy(acceptedDir);
        copyFileFromJarResourceDir(acceptedSourceLocation, acceptedDir + "\\accepted");

        String registryPath = "HKCU:\\Software\\JavaSoft\\Prefs\\jetbrains\\privacy_policy";
        ProcessBuilder pb1 = new ProcessBuilder("powershell.exe", "New-Item", "-Path", registryPath, "-Force");
        ProcessBuilder pb2 = new ProcessBuilder("powershell.exe", "New-ItemProperty", "-Path", registryPath, "-Name", "accepted_version", "-Value", "\"2.1\"");

        try {
            Process p1 = pb1.start();
            p1.waitFor();
            Process p2 = pb2.start();
            p2.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }





        GlobalUtils.waitUntilIntelliJStarts(8082);
        robot = GlobalUtils.getRemoteRobotConnection(8082);
        GlobalUtils.clearTheWorkspace(robot);
        ideaVersion = GlobalUtils.getTheIntelliJVersion(robot);
    }

    private static void createDirectoryHierarchy(String location) {
        Path path = Paths.get(location);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFileFromJarResourceDir(String sourceFileLocation, String destFileLocation) {
        String acceptedDirSource = System.getProperty("user.home") + "\\accepted";
        File initialFile = new File(acceptedDirSource);
        InputStream resourceStream = null;
        try {
            resourceStream = new FileInputStream(initialFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            byte[] buffer = new byte[resourceStream.available()];
            resourceStream.read(buffer);
            File targetFile = new File(destFileLocation);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void finishTheTestRun() {
        GlobalUtils.checkForExceptions(robot);
        GlobalUtils.clearTheWorkspace(robot);
    }

    @Test
    public void createAQuarkusProjectAndBuildItUsingMaven() {
        step("Create a Quarkus project and build it using maven", () -> {
            QuarkusUtils.createNewQuarkusProject(robot, BuildUtils.ToolToBuildTheProject.MAVEN, QuarkusUtils.EndpointURLType.DEFAULT);
            GlobalUtils.waitUntilTheProjectImportIsComplete(robot);
            GlobalUtils.closeTheTipOfTheDayDialogIfItAppears(robot);
            GlobalUtils.maximizeTheIdeWindow(robot);
            GlobalUtils.waitUntilAllTheBgTasksFinish(robot);
            BuildUtils.buildTheProject(robot, BuildUtils.ToolToBuildTheProject.MAVEN);
            GlobalUtils.waitUntilAllTheBgTasksFinish(robot);
            BuildUtils.testIfBuildIsSuccessful(robot);
            GlobalUtils.closeTheProject(robot);
        });
    }

    @Test
    public void testIfTheQuarkusRuntimeIsDownloaded() {
        step("Test whether the Quarkus runtime can be downloaded", () -> {
            String projectName = "java-project-with-quarkus-runtime";
            String runtimeJarName = QuarkusUtils.createNewJavaProjectWithQuarkusFramework(robot, projectName);
            GlobalUtils.waitUntilTheProjectImportIsComplete(robot);
            GlobalUtils.closeTheTipOfTheDayDialogIfItAppears(robot);
            GlobalUtils.maximizeTheIdeWindow(robot);
            GlobalUtils.waitUntilAllTheBgTasksFinish(robot);
            assertTrue(ProjectToolWindowUtils.isAProjectFilePresent(robot, projectName, "lib", runtimeJarName), "The runtime has not been downloaded.");
            GlobalUtils.closeTheProject(robot);
        });
    }

    @Test
    public void createNewQuarkusProjectWithValidCustomEndpointURL() {
        step("Create new Quarkus project with valid custom endpoint URL", () -> {
            QuarkusUtils.createNewQuarkusProject(robot, BuildUtils.ToolToBuildTheProject.MAVEN, QuarkusUtils.EndpointURLType.CUSTOM);
            GlobalUtils.waitUntilTheProjectImportIsComplete(robot);
            GlobalUtils.closeTheTipOfTheDayDialogIfItAppears(robot);
            GlobalUtils.maximizeTheIdeWindow(robot);
            GlobalUtils.waitUntilAllTheBgTasksFinish(robot);
            GlobalUtils.closeTheProject(robot);
        });
    }

    @Test
    public void createNewQuarkusProjectWithInvalidCustomEndpointURL() {
        step("Create new Quarkus project with invalid custom endpoint URL", () -> {
            QuarkusUtils.tryToCreateNewQuarkusProjectWithInvalidCustomEndpointURL(robot);
        });
    }

    @Test
    public void createAQuarkusProjectAndBuildItUsingGradle() {
        step("Create a Quarkus project and build it using gradle", () -> {
            QuarkusUtils.createNewQuarkusProject(robot, BuildUtils.ToolToBuildTheProject.GRADLE, QuarkusUtils.EndpointURLType.DEFAULT);
            GlobalUtils.waitUntilTheProjectImportIsComplete(robot);
            GlobalUtils.closeTheTipOfTheDayDialogIfItAppears(robot);
            GlobalUtils.maximizeTheIdeWindow(robot);
            GlobalUtils.waitUntilAllTheBgTasksFinish(robot);
            BuildUtils.buildTheProject(robot, BuildUtils.ToolToBuildTheProject.GRADLE);
            GlobalUtils.waitUntilAllTheBgTasksFinish(robot);
            BuildUtils.testIfBuildIsSuccessful(robot);
            GlobalUtils.closeTheProject(robot);
        });
    }
}