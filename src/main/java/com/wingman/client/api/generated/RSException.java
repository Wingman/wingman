package com.wingman.client.api.generated;

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
