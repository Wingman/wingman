package com.wingman.client.api.mapping;

import java.text.MessageFormat;

public class FieldInfo {

    public final String cleanName;
    public final String owner;
    public final String name;
    public final String obfType;
    public final String deobfType;
    public final boolean isStatic;

    public final long getter;
    public final long setter;

    public FieldInfo(String cleanName, String owner, String name, String obfType, String deobfType, boolean isStatic, String multiplier) {
        this.cleanName = cleanName;
        this.owner = owner;
        this.name = name;
        this.obfType = obfType;
        this.deobfType = deobfType;
        this.isStatic = isStatic;

        this.getter = Long.parseLong(multiplier);

        long setter = 1;
        if (obfType.equals("I") || obfType.equals("J")) {
            try {
                setter = MappingsHelper.getMMI(this.getter, obfType.equals("I"));
            } catch (ArithmeticException e) {
                System.out.println(MessageFormat.format("Multiplier {0} for {1}.{2} ({3}) is broken - {4}",
                        this.getter,
                        owner,
                        name,
                        cleanName,
                        e.getMessage()));
            }
        }
        this.setter = setter;
    }
}
