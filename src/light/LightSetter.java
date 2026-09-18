package light;

import main.GamePanel;

public class LightSetter {

	GamePanel gp;
	
	public LightSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setupLight(int mapIndex) {
		
		 if (mapIndex == 1 && gp.currentCheckpoint == 0) {
			 
		 	gp.lights[mapIndex][0] = new CandleLight(gp);
	        gp.lights[mapIndex][0].worldX = 28 * gp.TILE_SIZE;
	        gp.lights[mapIndex][0].worldY = 6 * gp.TILE_SIZE;
	        gp.lights[mapIndex][0].size = 100;
	        
	        gp.lights[mapIndex][1] = new CandleLight(gp);
	        gp.lights[mapIndex][1].worldX = 30 * gp.TILE_SIZE;
	        gp.lights[mapIndex][1].worldY = 6 * gp.TILE_SIZE;
	        gp.lights[mapIndex][1].size = 100;
		 
		 	gp.lights[mapIndex][2] = new CandleLight(gp);
	        gp.lights[mapIndex][2].worldX = 27 * gp.TILE_SIZE;
	        gp.lights[mapIndex][2].worldY = 7 * gp.TILE_SIZE;
	        gp.lights[mapIndex][2].size = 100;
		 
		 	gp.lights[mapIndex][3] = new CandleLight(gp);
	        gp.lights[mapIndex][3].worldX = 27 * gp.TILE_SIZE;
	        gp.lights[mapIndex][3].worldY = 8 * gp.TILE_SIZE;
	        gp.lights[mapIndex][3].size = 100;
		 
		 	gp.lights[mapIndex][4] = new CandleLight(gp);
	        gp.lights[mapIndex][4].worldX = 31 * gp.TILE_SIZE;
	        gp.lights[mapIndex][4].worldY = 7 * gp.TILE_SIZE;
	        gp.lights[mapIndex][4].size = 100;
		 
		 	gp.lights[mapIndex][5] = new CandleLight(gp);
	        gp.lights[mapIndex][5].worldX = 31 * gp.TILE_SIZE;
	        gp.lights[mapIndex][5].worldY = 8 * gp.TILE_SIZE;
	        gp.lights[mapIndex][5].size = 100;
		 
			gp.lights[mapIndex][6] = new CandleLight(gp);
	        gp.lights[mapIndex][6].worldX = 28 * gp.TILE_SIZE;
	        gp.lights[mapIndex][6].worldY = 9 * gp.TILE_SIZE;
	        gp.lights[mapIndex][6].size = 100;
		 
		 	gp.lights[mapIndex][7] = new CandleLight(gp);
	        gp.lights[mapIndex][7].worldX = 30 * gp.TILE_SIZE;
	        gp.lights[mapIndex][7].worldY = 9 * gp.TILE_SIZE;
	        gp.lights[mapIndex][7].size = 100;
	        
	        if (!gp.player.hasLantern_Hospital) {
	        	gp.lights[mapIndex][8] = new CandleLight(gp);
		        gp.lights[mapIndex][8].worldX = 24 * gp.TILE_SIZE;
		        gp.lights[mapIndex][8].worldY = 4 * gp.TILE_SIZE;
		        gp.lights[mapIndex][8].size = 150;
			 }
	        
		 } else if (mapIndex == 6 && gp.currentCheckpoint == 4) {
			 gp.lights[mapIndex][0] = new CandleLight(gp);
	        gp.lights[mapIndex][0].worldX = 0 * gp.TILE_SIZE;
	        gp.lights[mapIndex][0].worldY = 0 * gp.TILE_SIZE;
	        gp.lights[mapIndex][0].size = 10;
		 }
			        
	}
}
