package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Message extends DualNode {
    String getMessage();

    String getPrefix();

    int getIndex();

    int getCycle();

    String getSender();

    int getType();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setMessage(String value);

        void setPrefix(String value);

        void setIndex(int value);

        void setCycle(int value);

        void setSender(String value);

        void setType(int value);
    }
}
