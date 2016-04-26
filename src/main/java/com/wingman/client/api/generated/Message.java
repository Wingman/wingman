package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Message extends DualNode {
    int getCycle();

    int getIndex();

    String getMessage();

    String getPrefix();

    String getSender();

    int getType();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setCycle(int value);

        void setIndex(int value);

        void setMessage(String value);

        void setPrefix(String value);

        void setSender(String value);

        void setType(int value);
    }
}
