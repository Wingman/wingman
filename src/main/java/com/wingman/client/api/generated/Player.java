package com.wingman.client.api.generated;

@SuppressWarnings("all")
public interface Player extends Character {
    Model getModel();

    String[] getActions();

    int getCombatLevel();

    boolean getHidden();

    int getOverHeadIcon();

    int getSkullIcon();

    int getTeam();

    int getTotalLevel();

    @SuppressWarnings("all")
    interface Unsafe extends Character {
        void setActions(String[] value);

        void setCombatLevel(int value);

        void setHidden(boolean value);

        void setOverHeadIcon(int value);

        void setSkullIcon(int value);

        void setTeam(int value);

        void setTotalLevel(int value);
    }
}
