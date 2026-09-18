package main;

import java.util.ArrayList;

import light.CandleLight;
import main.GamePanel.CameraMode;
import object.OBJ_Incense;
import object.OBJ_Lighter;

public class CheckpointStage {
	
	GamePanel gp;
	
	int counter = 0;
	
	// map index
	public int mapId = 0;
	public int missionChecklistCount = 0;

	// phone
	public boolean phoneStage0 = false;
	boolean phoneEventTriggered0 = false;
	
	// show btn
	// - bedroom 0
	public boolean showP = false;
	public boolean showedP = false;
	
	public boolean showCloseD = false;
	
	// - hospital 1
	public boolean showI = false;
	public boolean showSelectInventory = false;
	public boolean showOpenCloseLocker = false;
	public int coolDownOC = 0;
	
	// hospital
	public int hospitalFloor = 1;
	
	// - bedroom 2
	public boolean showOpenDoorBed = false;
	
	// - pingkai 3
	public int kaiGo = 0;
	
	// - homegrandma1
	public int talkGrandma = 0;
	public boolean talkKanomGrandma = false;
//	public int kanom = 
	public boolean canUsePan = false;
	public boolean canUseSteamer = false;
	public boolean canUseLitter = false;
	public boolean finishKanom = false;
	public boolean showMrap = false;
	
	public int kaiEnd = 0;
	
	// checklist
	public ArrayList<ChecklistItem> checklist = new ArrayList<>();
	
	public CheckpointStage(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void stage0() {
		
		switch(mapId) {
		case 0:
			gp.cameraMode = CameraMode.FIXED;
	 	    gp.player.setDefaultValues(gp.TILE_SIZE * 12, gp.TILE_SIZE * 5);
	 	    gp.tileM.loadMap("bedroom", "bedroom1");
			break;
		case 1 :
			gp.cameraMode = CameraMode.FOLLOW;
			gp.player.setDefaultValues(gp.TILE_SIZE * 34, gp.TILE_SIZE * 17);
			gp.player.direction = "up";
	 	    gp.tileM.loadMap("hospital_1", "hospital_1");
	 	    break;
		case 2 :
			gp.cameraMode = CameraMode.FIXED;
	 	    gp.player.setDefaultValues(gp.TILE_SIZE * 12, gp.TILE_SIZE * 5);
	 	    gp.tileM.loadMap("bedroom", "bedroom3");
	 	    
	 	   gp.ui.addMessage("เงินในบัญชีคงเหลือ 5000 บาท");
	 	   gp.ui.addMessage("เมื่อคืนเรื่องจริงหรอเนี่ย ออกไปหาลุงร้านไก่ดีกว่า");
	 	   break;
		case 3 :
			gp.cameraMode = CameraMode.FOLLOW;
	 	    gp.player.setDefaultValues(gp.TILE_SIZE * 29, gp.TILE_SIZE * 7);
	 	    gp.player.direction = "left";
	 	    gp.tileM.loadMap("pingkai", "pingkai");
	 	   break;

		}
		
	}
	
	public void updateStage0() {
		
		if (phoneStage0 && !phoneEventTriggered0) {
	        counter++;

	        if (counter > 120) {
	        	
	            gp.playLSE(1);
            	
	            if (!showedP) {
	            	showP = true;
	            }
 	            
 	            counter = 0;
 	            gp.ui.phoneScreenState = gp.ui.PHONE_STATE;
 	            phoneEventTriggered0 = true;
	        }
	    }
		
	}
	
	public void updateStage01() {
		
		if (coolDownOC > 0) {
			coolDownOC--;
		}
		
		if (gp.player.worldX == 2104 && gp.player.worldY == 176 && gp.player.direction == "left" && hospitalFloor == 1 && gp.player.hasLantern_Hospital) {
			
			gp.player.setDefaultValues(gp.TILE_SIZE * 36, gp.TILE_SIZE * 27);
			gp.player.direction = "up";
			hospitalFloor = 2;
			
			for (int i = 0; i < gp.lights[mapId].length; i++) {
			    gp.lights[mapId][i] = null;
			}
			
			gp.lights[mapId][0] = new CandleLight(gp);
	        gp.lights[mapId][0].worldX = 100 * gp.TILE_SIZE;
	        gp.lights[mapId][0].worldY = 100 * gp.TILE_SIZE;
	        gp.lights[mapId][0].size = 5;
	        
	        for (int i = 0; i < gp.obj[mapId].length; i++) {
			    gp.obj[mapId][i] = null;
			}
	        
	        if (gp.eHandler.itemsAdded && !gp.player.hasIncense) {
	        	gp.obj[1][3] = new OBJ_Incense(gp);
	 	        gp.obj[1][3].worldX = 35 * gp.TILE_SIZE;
	 	        gp.obj[1][3].worldY = 41 * gp.TILE_SIZE;
	        }
			
			gp.tileM.loadMap("hospital_2", "hospital_2");
		} else if (gp.player.worldX == 2104 && gp.player.worldY == 176 && gp.player.direction == "left" && hospitalFloor == 1 && !gp.player.hasLantern_Hospital) {
			
			if (!gp.ui.hasShowedMessage)  {
				gp.ui.addMessage("ไม่กล้าขึ้น มันมืดมาก");
				gp.ui.hasShowedMessage = true;
			}
			
		}
		
		if (gp.player.worldX >= 1672 && gp.player.worldX <= 1732 && gp.player.worldY == 1316 && gp.player.direction == "down" && hospitalFloor == 2) {
			
			gp.player.setDefaultValues(gp.TILE_SIZE * 44, gp.TILE_SIZE * 4);
			gp.player.direction = "right";
			hospitalFloor = 1;
			
			 for (int i = 0; i < gp.obj[mapId].length; i++) {
				    gp.obj[mapId][i] = null;
			 }
			
			gp.lSetter.setupLight(mapId);
			gp.aSetter.setObject(mapId);
			
			gp.tileM.loadMap("hospital_1", "hospital_1");
		}
	}
	
	public void updateStage03() {
		
		if (kaiGo >= 1 && gp.player.worldX == gp.TILE_SIZE * 29 && gp.player.direction == "right") {
			gp.ui.loadingScreenState = 2;
			gp.gameState = gp.LOADING_STATE;
			
			gp.gamesave.saveGame(1);
			mapId = 0;
		}
	}
	
	public void stage1() {
 	   	gp.player.hasDiary = true;
		
		switch(mapId) {
		case 0:
			gp.cameraMode = CameraMode.FOLLOW;
	 	    gp.player.setDefaultValues(gp.TILE_SIZE * 35, gp.TILE_SIZE * 6);
	 	    gp.player.direction = "down";
	 	    gp.tileM.loadMap("central", "central");
			break;
		}
		
	}
	
	public void stage2() {
		gp.player.hasDiary = true;
 		gp.player.hasrock1 = true;
	}
	
	public void stage3() {
		
	}
	
	public void stage4() {
		
 	   	gp.player.hasDiary = true;
 		gp.player.hasrock1 = true;
 		gp.player.hasrock2 = true;
 		gp.player.hasrock3 = true;
		
		switch(mapId) {
		case 0:
			gp.cameraMode = CameraMode.FOLLOW;
	 	    gp.player.setDefaultValues(gp.TILE_SIZE * 10, gp.TILE_SIZE * 7);
	 	    gp.player.direction = "down";
	 	    gp.tileM.loadMap("south0", "south0");
	 	    
	 	    gp.ui.addMessage("เงียบจัง…วัดนี้เหมือนถูกลืมไปแล้วเลย");
			break;
		case 1:
			gp.cameraMode = CameraMode.FOLLOW;
	 	    gp.player.setDefaultValues(932, 368);
	 	    gp.player.direction = "down";
	 	    gp.tileM.loadMap("south1", "south1");
			break;
		case 2:
			gp.cameraMode = CameraMode.FOLLOW;
			gp.player.setDefaultValues(696, 656);
	 	    gp.player.direction = "up";
	 	    gp.tileM.loadMap("south0", "south0");
			break;
		case 3:
			gp.cameraMode = CameraMode.FOLLOW;
			gp.player.setDefaultValues(gp.TILE_SIZE * 6, gp.TILE_SIZE * 17);
			 gp.player.direction = "right";
			gp.tileM.loadMap("south2", "tamplebad");
			break;
		case 4:
			gp.cameraMode = CameraMode.FOLLOW;
			gp.tileM.loadMap("south2", "tamplegood");
			break;
		case 5:
			gp.cameraMode = CameraMode.FOLLOW;
	 	    gp.player.setDefaultValues(gp.TILE_SIZE * 29, gp.TILE_SIZE * 7);
	 	    gp.player.direction = "left";
	 	    gp.tileM.loadMap("pingkai", "pingkai");
	 	    break;
		case 6:
			gp.player.hasrock1 = false;
	 		gp.player.hasrock2 = false;
	 		gp.player.hasrock3 = false;
	 		gp.player.hasrock4 = false;
			
			gp.cameraMode = CameraMode.FOLLOW;
	 	   	gp.player.setDefaultValues(gp.TILE_SIZE * 29, gp.TILE_SIZE * 10);
			gp.player.direction = "up";
	 	    gp.tileM.loadMap("hospital_1", "hospital_1");
	 	    
	 	   String playerName = PlayerSession.getPlayerName();
	 	   	gp.gameState = gp.DIALOGUE_STATE;
			gp.ui.dialogueManager.startDialogue(
					"ลุง: เอาล่ะ เรามาเริ่มกันเลย\n "
					+ "ลุง: (เริ่มสวดคาถา)\n "
					+ playerName + ": (เอ๊ะ ทำไมรู้สึกว่าลุงสวดภาษาแปลกๆ\t มันดูไม่ใช่ภาษาที่เราคุ้นเลยแฮะ)\n "
					+ playerName + ": (แต่ก็คงบทสวดทั่วไปแหละมั้ง อย่าคิดมาก เราแค่ไม่รู้จักเฉยๆ)\n "
					+ "ลุง: หึหึหึ (ยิ้มมุมปาก)\n "
					+ playerName + ": ละ..ลุง..เป็นอะไรครับ!?\n "
					+ "ลุง: พิธีจบแล้วล่ะ\n "
					+ "ลุง: ขอบใจนะ\n "
					+ "ลุง: เอ็งนี่มันหลอกง่ายจริงๆ ข้าแทบไม่ต้องเหนื่อย \tก็ได้ Javarock มาทำพิธีของข้าละ\n "
					+ "ลุง: หลังจากนี้\n "
					+ "ลุง: เอ็งก็โชคดีล่ะ...หึหึ"
					);
			gp.gamesave.saveGame(5);
	 	    break;
		}
		
		
	}
	
	public void stage5() {
		gp.ui.titleScreenState = 4;
		gp.gameState = gp.TITLE_STATE;
	}

	
}
