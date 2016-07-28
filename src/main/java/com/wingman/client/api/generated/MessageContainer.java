package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface MessageContainer {
    Message addMessage(int arg0, String arg1, String arg2, String arg3);

    Message[] getMessages();

    int getMessageCount();

    @SuppressWarnings("all")
    interface Unsafe {
        void setMessages(Message[] value);

        void setMessageCount(int value);
    }
}
