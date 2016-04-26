package com.wingman.client.api.mapping;

public class MethodInfo {

    public final String cleanName;
    public final String name;
    public final String owner;
    public final String obfType;
    public final String deobfType;
    public final String desc;
    public final String cleanDesc;
    public final boolean isStatic;
    public final boolean hasDummy;
    public final int dummy;

    public MethodInfo(String cleanName, String owner, String name, String obfType, String deobfType, String desc, String cleanDesc, boolean isStatic, boolean hasDummy, int dummy) {
        this.cleanName = cleanName;
        this.owner = owner;
        this.name = name;
        this.obfType = obfType;
        this.deobfType = deobfType;
        this.desc = desc;
        this.cleanDesc = cleanDesc;
        this.isStatic = isStatic;
        this.hasDummy = hasDummy;
        this.dummy = dummy;
    }
}
