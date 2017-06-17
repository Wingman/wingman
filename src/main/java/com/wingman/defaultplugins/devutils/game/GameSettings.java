package com.wingman.defaultplugins.devutils.game;

import com.wingman.client.api.generated.GameAPI;
import com.wingman.defaultplugins.devutils.enums.AttackOption;

public class GameSettings {

    public static boolean isChatTransparent() {
        return (GameAPI.getGameSettings()[1055] & 512) == 0;
    }

    public static boolean isChatClickable() {
        return (GameAPI.getGameSettings()[1055] & 2048) == 0;
    }

    public static boolean isSidePanelTransparent() {
        return (GameAPI.getGameSettings()[1055] & 1024) == 0;
    }

    public static boolean isSidePanelClosableByHotKeys() {
        return (GameAPI.getGameSettings()[1055] & 4096) == 0;
    }

    public static boolean areSideButtonsInTheBottom() {
        return (GameAPI.getGameSettings()[1055] & 256) != 0;
    }

    public static boolean areDataOrbsEnabled() {
        return (GameAPI.getGameSettings()[1055] & 8) != 0;
    }

    public static boolean isRunOn() {
        return GameAPI.getGameSettings()[173] == 1;
    }

    public static boolean areChatEffectsOn() {
        return GameAPI.getGameSettings()[171] == 0;
    }

    public static boolean isAcceptAidOn() {
        return GameAPI.getGameSettings()[427] == 1;
    }

    public static boolean isProfanityFilterOn() {
        return GameAPI.getGameSettings()[1074] == 0;
    }

    public static boolean isPrivateChatSplit() {
        return GameAPI.getGameSettings()[287] == 1;
    }

    public static boolean isPrivateChatHiddenWhenChatBoxIsHidden() {
        return (GameAPI.getGameSettings()[1055] & 16384) != 0;
    }

    public static boolean isLoginLogoutNotificationTimeoutOn() {
        return (GameAPI.getGameSettings()[1055] & 128) == 0;
    }

    public static boolean isMouseCameraOn() {
        return (GameAPI.getGameSettings()[1055] & 32) == 0;
    }

    public static boolean isNumberOfMouseButtonsTwo() {
        return GameAPI.getGameSettings()[170] == 0;
    }

    public static boolean isMusicLooping() {
        return GameAPI.getGameSettings()[19] == 1;
    }

    public static boolean isMusicPlayingAutomatically() {
        return GameAPI.getGameSettings()[18] == 1;
    }

    public static boolean isAutoRetaliateOn() {
        return GameAPI.getGameSettings()[172] == 0;
    }

    public static AttackOption getPlayerAttackOption() {
        return AttackOption.values()[GameAPI.getGameSettings()[1107]];
    }

    public static AttackOption getNpcAttackOption() {
        return AttackOption.values()[GameAPI.getGameSettings()[1306]];
    }

    public static int getBrightnessLevel() {
        return GameAPI.getGameSettings()[166];
    }

    public static int getMusicLevel() {
        return GameAPI.getGameSettings()[168];
    }

    public static int getSoundEffectLevel() {
        return GameAPI.getGameSettings()[169];
    }

    public static int getAreaSoundEffectLevel() {
        return GameAPI.getGameSettings()[872];
    }

    public static int getAttackStyle() {
        return GameAPI.getGameSettings()[43];
    }

    public static int getOpenSkillManual() {
        return GameAPI.getGameSettings()[965];
    }
}
