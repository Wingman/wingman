package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface PlayerDefinition {
    void initialize(int[] arg0, int[] arg1, boolean arg2, int arg3);

    boolean getIsFemale();

    int[] getAppearance();

    int[] getAppearanceColors();

    @SuppressWarnings("all")
    interface Unsafe {
        void setIsFemale(boolean value);

        void setAppearance(int[] value);

        void setAppearanceColors(int[] value);
    }
}
