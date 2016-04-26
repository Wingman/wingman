package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface RuneScriptReference {
    RuneScriptDefinition getDefinition();

    int[] getLocalInteger();

    String[] getLocalStrings();

    @SuppressWarnings("all")
    interface Unsafe {
        void setDefinition(RuneScriptDefinition value);

        void setLocalInteger(int[] value);

        void setLocalStrings(String[] value);
    }
}
