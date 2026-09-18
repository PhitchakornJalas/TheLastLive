package main;

import entity.NPC_Grandma_south;
import entity.NPC_Oldman;
import entity.NPC_Uncle;
import monster.MON_GreenSlime;
import object.OBJ_Cat;
import object.OBJ_Diarybook;
import object.OBJ_Javarock4;
import object.OBJ_Lantern;
import object.OBJ_Lighter;
import object.OBJ_Litter;
import object.OBJ_Locker1;
import object.OBJ_Locker2;
import object.OBJ_Mrap;
import object.OBJ_Pan;
import object.OBJ_Rope;
import object.OBJ_Steamer;
import object.OBJ_Woodplanks;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject(int mapIndex) {
		
	    if (mapIndex == 0 && gp.currentCheckpoint == 0) {
	        gp.obj[mapIndex][0] = new OBJ_Diarybook(gp);
	        gp.obj[mapIndex][0].worldX = 12 * gp.TILE_SIZE + gp.TILE_SIZE / 2;
	        gp.obj[mapIndex][0].worldY = 4 * gp.TILE_SIZE - 20;
	    } else if (mapIndex == 1 && gp.currentCheckpoint == 0) {
	    	
	    	if (!gp.player.hasLantern_Hospital) {
		        gp.obj[mapIndex][0] = new OBJ_Lantern(gp);
		        gp.obj[mapIndex][0].worldX = 24 * gp.TILE_SIZE;
		        gp.obj[mapIndex][0].worldY = 4 * gp.TILE_SIZE;
	    	}
	    	
	    	if (gp.eHandler.itemsAdded && !gp.player.hasLighter) {
	    		gp.obj[1][1] = new OBJ_Lighter(gp);
		        gp.obj[1][1].worldX = 29 * gp.TILE_SIZE;
		        gp.obj[1][1].worldY = 18 * gp.TILE_SIZE;
	    	}
	    	
	    	gp.door[mapIndex][0] = new OBJ_Locker2(gp);
	        gp.door[mapIndex][0].worldX = 62 * gp.TILE_SIZE;
	        gp.door[mapIndex][0].worldY = 10 * gp.TILE_SIZE;
	    	
	    	gp.door[mapIndex][1] = new OBJ_Locker1(gp);
	        gp.door[mapIndex][1].worldX = 68 * gp.TILE_SIZE;
	        gp.door[mapIndex][1].worldY = 9 * gp.TILE_SIZE;
	        
	        gp.door[mapIndex][2] = new OBJ_Locker2(gp);
	        gp.door[mapIndex][2].worldX = 71 * gp.TILE_SIZE;
	        gp.door[mapIndex][2].worldY = 10 * gp.TILE_SIZE;
	        
	        gp.door[mapIndex][3] = new OBJ_Locker1(gp);
	        gp.door[mapIndex][3].worldX = 66 * gp.TILE_SIZE;
	        gp.door[mapIndex][3].worldY = 9 * gp.TILE_SIZE;
	        
	        gp.door[mapIndex][4] = new OBJ_Locker1(gp);
	        gp.door[mapIndex][4].worldX = 62 * gp.TILE_SIZE;
	        gp.door[mapIndex][4].worldY = 24 * gp.TILE_SIZE;
	        
	        gp.door[mapIndex][5] = new OBJ_Locker1(gp);
	        gp.door[mapIndex][5].worldX = 68 * gp.TILE_SIZE;
	        gp.door[mapIndex][5].worldY = 25 * gp.TILE_SIZE;
	    } 
	    
	    else if (mapIndex == 0 && gp.currentCheckpoint == 1) {
	    	
	    	gp.obj[mapIndex][0] = new OBJ_Cat(gp);
	        gp.obj[mapIndex][0].worldX = 56 * gp.TILE_SIZE;
	        gp.obj[mapIndex][0].worldY = 13 * gp.TILE_SIZE;

	        gp.obj[mapIndex][1] = new OBJ_Rope(gp);
	        gp.obj[mapIndex][1].worldX = 17 * gp.TILE_SIZE;
	        gp.obj[mapIndex][1].worldY = 22 * gp.TILE_SIZE;

	        gp.obj[mapIndex][2] = new OBJ_Woodplanks(gp);
	        gp.obj[mapIndex][2].worldX = 5 * gp.TILE_SIZE;
	        gp.obj[mapIndex][2].worldY = 24 * gp.TILE_SIZE;
	    } 
	    
	    else if (mapIndex == 0 && gp.currentCheckpoint == 4) {
	    	gp.obj[mapIndex][0] = new NPC_Grandma_south(gp);
			gp.obj[mapIndex][0].worldX = gp.TILE_SIZE * 14;
			gp.obj[mapIndex][0].worldY = gp.TILE_SIZE * 13;
	    } else if (mapIndex == 1 && gp.currentCheckpoint == 4) {
	    	gp.obj[mapIndex][0] = new OBJ_Pan(gp);
			gp.obj[mapIndex][0].worldX = gp.TILE_SIZE * 18;
			gp.obj[mapIndex][0].worldY = gp.TILE_SIZE * 15;
			
			gp.obj[mapIndex][1] = new OBJ_Litter(gp);
			gp.obj[mapIndex][1].worldX = gp.TILE_SIZE * 14;
			gp.obj[mapIndex][1].worldY = gp.TILE_SIZE * 15;
			
			gp.obj[mapIndex][2] = new OBJ_Steamer(gp);
			gp.obj[mapIndex][2].worldX = gp.TILE_SIZE * 16;
			gp.obj[mapIndex][2].worldY = gp.TILE_SIZE * 15;
			
			gp.obj[mapIndex][3] = new NPC_Grandma_south(gp);
			gp.obj[mapIndex][3].worldX = gp.TILE_SIZE * 18;
			gp.obj[mapIndex][3].worldY = gp.TILE_SIZE * 10;
			
	    } else if (mapIndex == 4 && gp.currentCheckpoint == 4) {
	    	gp.obj[mapIndex][0] = new OBJ_Mrap(gp);
			gp.obj[mapIndex][0].worldX = gp.TILE_SIZE * 10;
			gp.obj[mapIndex][0].worldY = gp.TILE_SIZE * 8;
			
			gp.obj[mapIndex][1] = new OBJ_Javarock4(gp);
			gp.obj[mapIndex][1].worldX = gp.TILE_SIZE * 19;
			gp.obj[mapIndex][1].worldY = gp.TILE_SIZE * 9;
	    } else if (mapIndex == 6 && gp.currentCheckpoint == 4) {
	    	gp.obj[mapIndex][0] = new NPC_Uncle(gp);
			gp.obj[mapIndex][0].worldX = gp.TILE_SIZE * 29;
			gp.obj[mapIndex][0].worldY = gp.TILE_SIZE * 9;
	    }
	}
	
//	public void setNPC(int mapIndex) {
//		
//		if (mapIndex == 0 && gp.currentCheckpoint == 4) {
//	    	
//			gp.npc[mapIndex][0] = new NPC_Grandma_south(gp);
//			gp.npc[mapIndex][0].worldX = gp.TILE_SIZE * 14;
//			gp.npc[mapIndex][0].worldY = gp.TILE_SIZE * 13;
//	    }
//		
//	}
	
	public void setMonster() {
		
//		int i = 0;
//		gp.monster[i] = new MON_GreenSlime(gp);
//		gp.monster[i].worldX = gp.TILE_SIZE * 23;
//		gp.monster[i].worldY = gp.TILE_SIZE * 26;
//		i++;
//		gp.monster[i] = new MON_GreenSlime(gp);
//		gp.monster[i].worldX = gp.TILE_SIZE * 23;
//		gp.monster[i].worldY = gp.TILE_SIZE * 27;
//		i++;
//		gp.monster[i] = new MON_GreenSlime(gp);
//		gp.monster[i].worldX = gp.TILE_SIZE * 23;
//		gp.monster[i].worldY = gp.TILE_SIZE * 28;
//		i++;
//		gp.monster[i] = new MON_GreenSlime(gp);
//		gp.monster[i].worldX = gp.TILE_SIZE * 23;
//		gp.monster[i].worldY = gp.TILE_SIZE * 29;
//		i++;
//		gp.monster[i] = new MON_GreenSlime(gp);
//		gp.monster[i].worldX = gp.TILE_SIZE * 23;
//		gp.monster[i].worldY = gp.TILE_SIZE * 30;
//		i++;
	}
}
