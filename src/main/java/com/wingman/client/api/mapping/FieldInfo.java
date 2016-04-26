package com.wingman.client.api.mapping;

public class FieldInfo {

    public final String cleanName;
    public final String owner;
    public final String name;
    public final String obfType;
    public final String deobfType;
    public final boolean isStatic;
    public final int multiplier;

    public FieldInfo(String cleanName, String owner, String name, String obfType, String deobfType, boolean isStatic, int multiplier) {
        this.cleanName = cleanName;
        this.owner = owner;
        this.name = name;
        this.obfType = obfType;
        this.deobfType = deobfType;
        this.isStatic = isStatic;
        this.multiplier = multiplier;
    }
}
