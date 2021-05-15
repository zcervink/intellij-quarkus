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
import org.apache.commons.io.FileUtils;
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


    @Test
    public void createAQuarkusProjectAndBuildItUsingMaven() {
        step("Create a Quarkus project and build it using maven", () -> {
            makeSureAllTermsAndConditionsAreAccepted();
        });
    }

    private static void makeSureAllTermsAndConditionsAreAccepted() {
        String osName = System.getProperty("os.name");

        if (osName.equals("Linux")) {
            String prefsXmlSourceLocation = System.getProperty("user.home") + "/prefs.xml";
            String prefsXmlDir = System.getProperty("user.home") + "/.java/.userPrefs/jetbrains/_!(!!cg\"p!(}!}@\"j!(k!|w\"w!'8!b!\"p!':!e@==";
            createDirectoryHierarchy(prefsXmlDir);
            copyX(prefsXmlSourceLocation, prefsXmlDir + "/prefs.xml");

            String acceptedSourceLocation = System.getProperty("user.home") + "/accepted";
            String acceptedDir = System.getProperty("user.home") + "/.local/share/JetBrains/consentOptions";
            createDirectoryHierarchy(acceptedDir);
            copyX(acceptedSourceLocation, acceptedDir + "/accepted");
        }
    }

    private static void createDirectoryHierarchy(String location) {
        Path path = Paths.get(location);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyX(String sourceFileLocation, String destFileLocation) {
        File original = new File(
                sourceFileLocation);
        File copied = new File(
                destFileLocation);
        try {
            FileUtils.copyFile(original, copied);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}