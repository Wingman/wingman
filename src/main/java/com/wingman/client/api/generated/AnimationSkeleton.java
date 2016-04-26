package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface AnimationSkeleton {
    boolean getHasAlpha();

    AnimationSkin getSkin();

    int getStepCount();

    @SuppressWarnings("all")
    interface Unsafe {
        void setHasAlpha(boolean value);

        void setSkin(AnimationSkin value);

        void setStepCount(int value);
    }
}
