package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface MessageContainer {
    Message addMessage(int arg0, String arg1, String arg2, String arg3);

    int getMessageCount();

    Message[] getMessages();

    @SuppressWarnings("all")
    interface Unsafe {
        void setMessageCount(int value);

        void setMessages(Message[] value);
    }
}
