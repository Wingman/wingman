package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface RuneScriptReference {
    int[] getLocalInteger();

    String[] getLocalStrings();

    RuneScriptDefinition getDefinition();

    @SuppressWarnings("all")
    interface Unsafe {
        void setLocalInteger(int[] value);

        void setLocalStrings(String[] value);

        void setDefinition(RuneScriptDefinition value);
    }
}
