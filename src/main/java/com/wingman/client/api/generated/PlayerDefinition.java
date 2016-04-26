package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface PlayerDefinition {
    int[] getAppearance();

    int[] getAppearanceColors();

    boolean getIsFemale();

    void initialize(int[] arg0, int[] arg1, boolean arg2, int arg3);

    @SuppressWarnings("all")
    interface Unsafe {
        void setAppearance(int[] value);

        void setAppearanceColors(int[] value);

        void setIsFemale(boolean value);
    }
}
