package com.wingman.client.api.mapping;

public class MethodInfo {

    public final String realName;
    public final String owner;
    public final String name;
    public final String type;
    public final String realType;
    public final String desc;
    public final String realDesc;
    public final boolean isStatic;

    public final boolean hasOpaquePredicate;
    public final int opaquePredicate;

    public MethodInfo(String realName,
                      String owner,
                      String name,
                      String type,
                      String realType,
                      String desc,
                      String realDesc,
                      boolean isStatic,
                      boolean hasOpaquePredicate,
                      int opaquePredicate) {

        this.realName = realName;
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.realType = realType;
        this.desc = desc;
        this.realDesc = realDesc;
        this.isStatic = isStatic;

        this.hasOpaquePredicate = hasOpaquePredicate;
        this.opaquePredicate = opaquePredicate;
    }
}
