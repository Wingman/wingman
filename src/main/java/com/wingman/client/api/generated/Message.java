package com.wingman.client.api.generated;

@SuppressWarnings("all")
public interface Message extends DualNode {
    int getIndex();

    String getMessage();

    String getPrefix();

    int getPushedLoopCycle();

    String getSender();

    int getType();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setIndex(int value);

        void setMessage(String value);

        void setPrefix(String value);

        void setPushedLoopCycle(int value);

        void setSender(String value);

        void setType(int value);
    }
}
