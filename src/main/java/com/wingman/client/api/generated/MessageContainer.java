package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface MessageContainer {
    int getMessageCount();

    Message[] getMessages();

    Message addMessage(int arg0, String arg1, String arg2, String arg3);

    @SuppressWarnings("all")
    interface Unsafe {
        void setMessageCount(int value);

        void setMessages(Message[] value);
    }
}
