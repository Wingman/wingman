package com.wingman.client.rs;

import com.google.common.base.Throwables;

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

    public GameAppletStub() {
        System.out.println("Loading applet parameters");

        Matcher parameterMatcher = Pattern
                .compile("=([0-9]+)=([\\S]+)")
                .matcher(GameDownloader.pageSource);

        while (parameterMatcher.find()) {
            parameters.put(parameterMatcher.group(1), parameterMatcher.group(2));
        }
    }

    public URL getDocumentBase() {
        try {
            return new URL(GameDownloader.runeScapeUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URL getCodeBase() {
        try {
            return new URL(GameDownloader.runeScapeUrl + GameDownloader.archiveName);
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
