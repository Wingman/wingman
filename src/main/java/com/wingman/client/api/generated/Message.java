package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Message extends DoublyNode {
    String getMessage();

    String getSender();

    int getType();

    String getPrefix();

    int getIndex();

    int getPushedLoopCycle();

    @SuppressWarnings("all")
    interface Unsafe extends DoublyNode {
        void setMessage(String value);

        void setSender(String value);

        void setType(int value);

        void setPrefix(String value);

        void setIndex(int value);

        void setPushedLoopCycle(int value);
    }
}
