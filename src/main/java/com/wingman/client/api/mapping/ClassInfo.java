package com.wingman.client.api.mapping;

public class ClassInfo {

    public final String cleanName;
    public final String obfName;

    public ClassInfo(String cleanName, String obfName) {
        this.cleanName = cleanName;
        this.obfName = obfName;
    }
}
