package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface RuneScriptReference {
    String[] getLocalStrings();

    int[] getLocalInteger();

    RuneScriptDefinition getDefinition();

    @SuppressWarnings("all")
    interface Unsafe {
        void setLocalStrings(String[] value);

        void setLocalInteger(int[] value);

        void setDefinition(RuneScriptDefinition value);
    }
}
