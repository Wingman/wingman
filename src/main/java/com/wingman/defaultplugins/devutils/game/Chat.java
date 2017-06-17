package com.wingman.defaultplugins.devutils.game;

import com.wingman.client.api.generated.GameAPI;

/**
 * Provides API for interacting with the in-game chat box.
 */
public class Chat {

    /**
     * Sets a game field, such that the game repaints the chat box on the next engine cycle. <br>
     * Useful after one has edited or added a chat box message through the API.
     */
    public static void repaintChatBox() {
        GameAPI.Unsafe.setRepaintChatBox(GameAPI.getRepaintFlag());
    }
}
