package org.jboss.tools.intellij.quarkus.utils;

import com.intellij.remoterobot.fixtures.dataExtractor.RemoteText;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

public class OtherUtils {

    public static String listOfRemoteTextToString(List<RemoteText> data) {
        List<String> listOfStrings = data
                .stream()
                .map(RemoteText::getText)
                .collect(Collectors.toList());

        String concatString = String.join("", listOfStrings);

        return concatString;
    }

    public static String getLatestVersionFromMvnRepository(String artifactGroup, String artifactName) {
        String originalUrl = "https://mvnrepository.com/artifact/" + artifactGroup + "/" + artifactName + "/latest";
        String redirectedUrl;
        URLConnection con;

        try {
            con = new URL(originalUrl).openConnection();
            con.connect();
            InputStream is = con.getInputStream();
            is.close();
            redirectedUrl = con.getURL().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        int lastIndexOf = redirectedUrl.lastIndexOf("/");
        String latestVersion = redirectedUrl.substring(lastIndexOf + 1);

        return latestVersion;
    }
}