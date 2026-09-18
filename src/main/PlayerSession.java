package main;

public class PlayerSession {
	private static String playerName; 

    public static void setPlayerName(String name) {
        playerName = name;
    }

    public static String getPlayerName() {
        return playerName;
    }
}
