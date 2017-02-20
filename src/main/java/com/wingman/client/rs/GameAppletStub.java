package com.wingman.client.rs;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameAppletStub implements AppletStub {

    private Map<String, String> parameters = new HashMap<>();

    private String runeScapeUrl;
    private String archiveName;

    public GameAppletStub(String runeScapeUrl, String pageSource, String archiveName) {
        this.runeScapeUrl = runeScapeUrl;
        this.archiveName = archiveName;

        System.out.println("Loading applet parameters");

        Matcher parameterMatcher = Pattern
                .compile("=([0-9]+)=([\\S]+)")
                .matcher(pageSource);

        while (parameterMatcher.find()) {
            parameters.put(parameterMatcher.group(1), parameterMatcher.group(2));
        }
    }

    public URL getDocumentBase() {
        try {
            return new URL(runeScapeUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URL getCodeBase() {
        try {
            return new URL(runeScapeUrl + archiveName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AppletContext getAppletContext() {
        return null;
    }

    @Override
    public String getParameter(String name) {
        return parameters.get(name);
    }

    public boolean isActive() {
        return true;
    }

    @Override
    public void appletResize(int width, int height) {

    }
}
