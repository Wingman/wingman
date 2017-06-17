package com.wingman.defaultplugins.devutils.enums;

/**
 * The different attack options available to select in the settings screen of the game.
 */
public enum AttackOption {

    DEPENDS_ON_COMBAT (0),
    RIGHT_CLICK (1),
    LEFT_CLICK (2),
    HIDDEN (3);

    public final int id;

    AttackOption(int id) {
        this.id = id;
    }
}
