package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface AnimationSkeleton {
    AnimationSkin getSkin();

    boolean getHasAlpha();

    int getStepCount();

    @SuppressWarnings("all")
    interface Unsafe {
        void setSkin(AnimationSkin value);

        void setHasAlpha(boolean value);

        void setStepCount(int value);
    }
}
