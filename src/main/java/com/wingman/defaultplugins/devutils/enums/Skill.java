package com.wingman.defaultplugins.devutils.enums;

/**
 * Enum for skills in the order they appear when retrieved from the high scores.
 */
public enum Skill{
    TOTAL("Total"),             ATTACK("Attack"),       DEFENCE("Defence"),
    STRENGTH("Strength"),       HITPOINTS("Hitpoints"), RANGED("Ranged"),
    PRAYER("Prayer"),           MAGIC("Magic"),         COOKING("Cooking"),
    WOODCUTTING("Woodcutting"), FLETCHING("Fletching"), FISHING("Fishing"),
    FIREMAKING("Firemaking"),   CRAFTING("Crafting"),   SMITHING("Smithing"),
    MINING("Mining"),           HERBLORE("Herblore"),   AGILITY("Agility"),
    THIEVING("Thieving"),       SLAYER("Slayer"),       FARMING("Farming"),
    RUNECRAFT("Runecrafting"),  HUNTER("Hunter"),       CONSTRUCTION("Construction");

    /**
     * Properly punctuated skill name.
     */
    private String prettyName;

    Skill(String prettyName){
        this.prettyName = prettyName;
    }

    public String getPrettyName(){
        return prettyName;
    }
}