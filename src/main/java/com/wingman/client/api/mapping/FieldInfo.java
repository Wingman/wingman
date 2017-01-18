package com.wingman.client.api.mapping;

import java.text.MessageFormat;

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
                     String getter) {

        this.realName = realName;
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.realType = realType;
        this.isStatic = isStatic;

        this.getter = Long.parseLong(getter);

        long setter = 1;
        if (type.equals("I") || type.equals("J")) {
            try {
                setter = MappingsHelper.getMMI(this.getter, type.equals("I"));
            } catch (ArithmeticException e) {
                System.out.println(MessageFormat.format("Multiplier {0} for {1}.{2} ({3}) is broken - {4}",
                        this.getter,
                        owner,
                        name,
                        realName,
                        e.getMessage()));
            }
        }
        this.setter = setter;
    }
}
