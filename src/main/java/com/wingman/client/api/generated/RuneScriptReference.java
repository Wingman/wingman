package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface RuneScriptReference {
    RuneScriptDefinition getDefinition();

    String[] getLocalStrings();

    int[] getLocalInteger();

    @SuppressWarnings("all")
    interface Unsafe {
        void setDefinition(RuneScriptDefinition value);

        void setLocalStrings(String[] value);

        void setLocalInteger(int[] value);
    }
}
