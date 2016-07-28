package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Message extends DualNode {
    int getType();

    int getIndex();

    String getSender();

    int getCycle();

    String getMessage();

    String getPrefix();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setType(int value);

        void setIndex(int value);

        void setSender(String value);

        void setCycle(int value);

        void setMessage(String value);

        void setPrefix(String value);
    }
}
