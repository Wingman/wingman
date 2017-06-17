package com.wingman.defaultplugins.devutils.util.external;

import com.wingman.defaultplugins.devutils.enums.GameMode;
import com.wingman.defaultplugins.devutils.enums.Skill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class for looking up a player on the high scores. A {@code HiscorePlayer} object represents
 * a single player on the high scores.
 *
 * <p>
 *
 * Example use:
 * Retrieving a player from the standard high scores and printing the results to the console.
 *
 * <blockquote><pre>
 *   new HiscorePlayer("Zezima").lookup(GameMode.STANDARD, new LookupCallback() {
 *     public void done(HiscorePlayer player) {
 *       // If the lookup was successful, iterate through the levels
 *       if(player != null){
 *         for(Level level : player.getLevels()){
 *           // Do something.
 *         }
 *       } else{
 *         // The lookup failed.
 *       }
 *     }
 *  });
 * </pre></blockquote>
 */
public class HiscorePlayer {

    /**
     * API URL prefix for standard high scores. The player's name should be appended to this string.
     */
    private static final String STANDARD_API_PREFIX;

    /**
     * API URL prefix for ironman high scores. The player's name should be appended to this string.
     */
    private static final String IRONMAN_API_PREFIX;

    /**
     * API URL prefix for ultimate ironman high scores. The player's name should be appended to this string.
     */
    private static final String ULTIMATE_API_PREFIX;

    /**
     * API URL prefix for deadman mode high scores. The player's name should be appended to this string.
     */
    private static final String DEADMAN_API_PREFIX;

    /**
     * API URL prefix for seasonal deadman mode high scores. The player's name should be appended to this string.
     */
    private static final String SEASONAL_API_PREFIX;

    /**
     * Array of skills in the order which they appear on the high scores.
     */
    private static final Skill[] SKILL_ORDER;

    /**
     * Whether or not this player has been successfully retrieved from the high scores.
     */
    private volatile boolean retrieved;

    /**
     * Which game mode this player's high scores were last retrieved from. If retrieved is false, this value is null.
     */
    private volatile GameMode lastRetrievedGameMode;

    /**
     * Whether or not this player is currently being looked up.
     */
    private volatile boolean busy;

    /**
     * Mapping of skills to levels.
     */
    private volatile LinkedHashMap<Skill, Level> levels;

    /**
     * Single thread executor service for executing the lookup thread.
     */
    private ExecutorService lookupExecutor;

    /**
     * The player's in-game name.
     */
    private String playerName;

    static {
        STANDARD_API_PREFIX = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player=";
        IRONMAN_API_PREFIX  = "http://services.runescape.com/m=hiscore_oldschool_ironman/index_lite.ws?player=";
        ULTIMATE_API_PREFIX = "http://services.runescape.com/m=hiscore_oldschool_ultimate/index_lite.ws?player=";
        DEADMAN_API_PREFIX  = "http://services.runescape.com/m=hiscore_oldschool_deadman/index_lite.ws?player=";
        SEASONAL_API_PREFIX = "http://services.runescape.com/m=hiscore_oldschool_seasonal/index_lite.ws?player=";
        SKILL_ORDER = Skill.values();
    }

    /**
     * Callback interface for methods to be invoked when retrieving a player from the high scores.
     */
    public interface LookupCallback {

        /**
         * Called when retrieving a player from the high scores completes
         * @param player The player if the lookup was successful. null otherwise.
         */
        void done(HiscorePlayer player);
    }

    /**
     * Class representing a single level. A level has a skill, actual level value, experience, and rank.
     */
    public static class Level {
        Skill skill;
        int level;
        long exp; // 64 bits for the super-players whose total exp won't fit in a 32-bit int.
        int rank;
    }

    /**
     * Runnable to be submitted to the executor service. Retrieves the player's information
     * from the high scores.
     */
    private class LookupThread implements Runnable {

        private LookupCallback callback;
        private GameMode gameMode;

        LookupThread(GameMode gameMode, LookupCallback callback) {
            this.callback = callback;
            this.gameMode = gameMode;
        }

        public void run() {
            try {
                URL url = new URL(buildURL(gameMode));
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                String next;
                int cycler = 0;
                int skillPosition = 0;
                Level level = null;

                // Clear any levels from an old look up
                HiscorePlayer.this.levels.clear();

                while ((next = in.readLine()) != null && skillPosition < SKILL_ORDER.length) {
                    for (String str : next.split(",")) {
                        switch (cycler++) {
                            case 0: // Start new level, read rank and skill name
                                level = new Level();
                                level.rank = Integer.parseInt(str);
                                level.skill = SKILL_ORDER[skillPosition];
                                break;
                            case 1: // Read level
                                level.level = Integer.parseInt(str);
                                break;
                            default: // (case 2) Read experience, end this level, add to levels map
                                level.exp = Long.parseLong(str);
                                HiscorePlayer.this.levels.put(SKILL_ORDER[skillPosition++], level);
                                cycler = 0;
                                break;
                        }
                    }
                }

                // In the case not all skills are retrieved, map the remaining ones to dummy values.
                for (int i = skillPosition; i < SKILL_ORDER.length; ++i) {
                    level = new Level();
                    level.skill = SKILL_ORDER[i];
                    level.rank = -1;
                    level.exp = -1;
                    level.level = -1;
                    HiscorePlayer.this.levels.put(SKILL_ORDER[i], level);
                }

            } catch (IOException e) {
                HiscorePlayer.this.retrieved = false;
                HiscorePlayer.this.lastRetrievedGameMode = null;
                HiscorePlayer.this.busy = false;
                HiscorePlayer.this.levels.clear();
                callback.done(null);
                return;
            }

            // If this line is reached, the lookup was a success.
            HiscorePlayer.this.retrieved = true;
            HiscorePlayer.this.busy = false;
            HiscorePlayer.this.lastRetrievedGameMode = gameMode;
            callback.done(HiscorePlayer.this);
        }
    }

    /**
     * Constructs a new HiscorePlayer with a specified name.
     *
     * @param playerName The player's name
     */
    public HiscorePlayer(String playerName) {
        this.playerName = playerName;
        this.levels = new LinkedHashMap<>();
        retrieved = false;
        lastRetrievedGameMode = null;
        busy = false;
        lookupExecutor = Executors.newSingleThreadExecutor();
    }

    /**
     * Default constructor. The player's name should be set with HiscorePlayer#setName.
     */
    public HiscorePlayer() {
        this("");
    }

    /**
     * Builds the url to access the high score API for a specific game mode.
     *
     * @param gameMode The game mode high scores to access. If null, defaults to standard.
     * @return The url
     */
    private String buildURL(GameMode gameMode) {
        if (gameMode == null) {
            gameMode = GameMode.STANDARD;
        }
        switch(gameMode) {
            case STANDARD:
                return STANDARD_API_PREFIX + playerName;
            case IRONMAN:
                return IRONMAN_API_PREFIX + playerName;
            case ULTIMATE:
                return ULTIMATE_API_PREFIX + playerName;
            case DEADMAN:
                return DEADMAN_API_PREFIX + playerName;
            case SEASONAL:
                return SEASONAL_API_PREFIX + playerName;
            default: // default to standard high scores.
                return STANDARD_API_PREFIX + playerName;
        }
    }

    /**
     * Asynchronously looks up the player on the high scores.
     *
     * @param gameMode The game mode to use. If null, defaults to standard.
     * @param callback The callback to invoke upon completion.
     */
    public void lookup(final GameMode gameMode, final LookupCallback callback) {
        // Do not start a new lookup if one is already in progress.
        if (this.busy) {
            callback.done(null);
            return;
        }

        // Set appropriate flags
        this.busy = true;
        this.retrieved = false;
        this.lastRetrievedGameMode = null;

        // Submit the lookup thread
        lookupExecutor.submit(new LookupThread(gameMode, callback));
    }

    /**
     * Returns a mapping of skills to levels.
     *
     * @return The mapping if this player has been successfully retrieved (HiscorePlayer#isRetrieved == true).
     *         null otherwise
     */
    public LinkedHashMap<Skill, Level> getLevelsMap() {
        if (this.retrieved) {
            // Do not return our private reference. Return a copy.
            return new LinkedHashMap<>(this.levels);
        } else {
            return null;
        }
    }

    /**
     * Returns a collection of this player's levels. The collection can be iterated
     * in the order the skills appear on the high scores.
     *
     * @return If retrieved, the collection of levels. false otherwise.
     */
    public Collection<Level> getLevels() {
        if (this.retrieved) {
            return this.levels.values();
        } else {
            return null;
        }
    }

    /**
     * Gets this player's level for a specific skill.
     *
     * @param skill The skill to get the level for
     * @return The level if this player has been successfully retrieved (HiscorePlayer#isRetrieved == true).
     *         null otherwise.
     */
    public Level getLevel(Skill skill) {
        if (this.retrieved){
            return this.levels.get(skill);
        } else {
            return null;
        }
    }

    /**
     * Returns whether or not this player has been retrieved.
     *
     * @return true if it has; false otherwise.
     */
    public boolean isRetrieved() {
        return this.retrieved;
    }

    /**
     * Which game mode this player's high scores were last retrieved from. If isRetrieved() is false
     * this method returns null.
     * @return The game mode.
     */
    public GameMode getLastRetrievedGameMode() {
        return this.lastRetrievedGameMode;
    }

    /**
     * Returns whether or not this player is currently being retrieved.
     *
     * @return true if this player is currently being retrieved; false otherwise.
     */
    public boolean isBusy() {
        return this.busy;
    }

    /**
     * Changes this player's name. HiscorePlayer#isBusy must be false for this method to take
     * action. If the player's name is changed, any high score data previously retrieved
     * is invalidated and must be retrieved again via HiscorePlayer#lookup.
     *
     * @param playerName The player's new name.
     * @return reference to this if the name was successfully changed. false otherwise.
     */
    public HiscorePlayer setName(String playerName) {
        if (busy) return null;

        if (!this.playerName.equalsIgnoreCase(playerName)) {
            this.playerName = playerName;
            this.levels.clear();
            this.retrieved = false;
            this.lastRetrievedGameMode = null;
        }
        return this;
    }

    /**
     * Returns this player's name.
     *
     * @return The name.
     */
    public String getName() {
        return this.playerName;
    }

//    // Example use:
//    // Retrieving a player from the standard high scores and printing the results to the console.
//    public static void main(String[] args){
//        new HiscorePlayer("Lynx Titan").lookup(GameMode.STANDARD, new LookupCallback() {
//            @Override
//            public void done(HiscorePlayer player) {
//                // If the lookup was successful, print the results.
//                if(player != null){
//                    System.out.printf("High scores for player %s:%n", player.getName());
//                    System.out.printf("%-15s%-9s%-7s%s%n", "Skill", "Rank", "Level", "Experience");
//                    System.out.println("------------------------------------------");
//                    for(Level level : player.getLevels()){
//                        System.out.printf("%-15s%-9d%-7d%d%n",
//                                level.skill.getPrettyName(), level.rank, level.level, level.exp
//                        );
//                    }
//                } else{
//                    System.err.println("High score lookup failed!");
//                }
//            }
//        });
//    }
}