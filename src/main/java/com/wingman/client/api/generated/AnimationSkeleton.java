package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface AnimationSkeleton {
    AnimationSkin getSkin();

    int getStepCount();

    boolean getHasAlpha();

    @SuppressWarnings("all")
    interface Unsafe {
        void setSkin(AnimationSkin value);

        void setStepCount(int value);

        void setHasAlpha(boolean value);
    }
}
