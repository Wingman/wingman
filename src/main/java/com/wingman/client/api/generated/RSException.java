package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.Throwable;

@SuppressWarnings("all")
public interface RSException {
    String getComment();

    Throwable getThrowable();

    @SuppressWarnings("all")
    interface Unsafe {
        void setComment(String value);

        void setThrowable(Throwable value);
    }
}
