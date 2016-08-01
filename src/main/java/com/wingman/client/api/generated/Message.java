package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Message extends DoublyNode {
    String getPrefix();

    String getSender();

    int getPushedLoopCycle();

    int getIndex();

    String getMessage();

    int getType();

    @SuppressWarnings("all")
    interface Unsafe extends DoublyNode {
        void setPrefix(String value);

        void setSender(String value);

        void setPushedLoopCycle(int value);

        void setIndex(int value);

        void setMessage(String value);

        void setType(int value);
    }
}
