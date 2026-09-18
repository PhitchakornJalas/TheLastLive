package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Gamesave {

	GamePanel gp;
	
	public Gamesave (GamePanel gp) {
		this.gp = gp;
	}
	
	public void saveGame(int checkPoint) {
	    try {
	        BufferedWriter bw = new BufferedWriter(new FileWriter("gamesave/" + PlayerSession.getPlayerName() + ".txt"));
	        
	        bw.write(String.valueOf(checkPoint));
	        
	        bw.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public int loadSaveGame() {
	    try {
	        BufferedReader br = new BufferedReader(new FileReader("gamesave/" + PlayerSession.getPlayerName() + ".txt"));
	        
	        String s = br.readLine();
	        br.close();
	        
	        s = s.trim();
	        
	        return Integer.parseInt(s);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1;
	    }
	}

	
}
