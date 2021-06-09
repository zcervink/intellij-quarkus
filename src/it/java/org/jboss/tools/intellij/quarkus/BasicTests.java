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


    @Test
    public void createAQuarkusProjectAndBuildItUsingMaven() {
        System.out.println("asdsa");

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
    }
}