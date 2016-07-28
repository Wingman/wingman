package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.Throwable;

@SuppressWarnings("all")
public interface RSException {
    Throwable getThrowable();

    String getComment();

    @SuppressWarnings("all")
    interface Unsafe {
        void setThrowable(Throwable value);

        void setComment(String value);
    }
}
