package com.wingman.client.api.mapping;

public class FieldInfo {

    public final String realName;
    public final String owner;
    public final String name;
    public final String type;
    public final String realType;
    public final boolean isStatic;

    public final long getter;
    public final long setter;

    public FieldInfo(String realName,
                     String owner,
                     String name,
                     String type,
                     String realType,
                     boolean isStatic,
                     long getter,
                     long setter) {

        this.realName = realName;
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.realType = realType;
        this.isStatic = isStatic;

        this.getter = getter;
        this.setter = setter;
    }
}
