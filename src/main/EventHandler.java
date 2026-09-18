package main;

import java.awt.Rectangle;

import entity.NPC_Grandma_south;
import object.OBJ_Lighter;
import object.OBJ_Mrap;
import object.OBJ_Pong;
import object.OBJ_StickyriceSun;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	boolean itemsAdded = false;
	
	public EventHandler(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void initEventRect() {
		eventRect = new EventRect[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

		int col = 0;
		int row = 0;
		while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

			col++;
			if (col == gp.MAX_WORLD_COL) {
				col = 0;
				row++;
			}
		}
	}

	
	public void checkEvent() {
		
		// check if the player character is more than 1 tile away from the last event
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if (distance > gp.TILE_SIZE) {
			canTouchEvent = true;
		}
		
		if (canTouchEvent == true) {
		
			if (hit(23, 7, "up") == true) healingPool(23, 7, gp.DIALOGUE_STATE);
			
			// hospital
			if (gp.stage.mapId == 1 && gp.currentCheckpoint == 0) {
				if (hit(29, 9, "up") == true) playGlass(29, 9);
			}
			
			if (gp.stage.mapId == 2 && gp.currentCheckpoint == 0) {
				if (hit(4, 3, "up") || hit(5, 3, "up")) {
					gp.stage.showOpenDoorBed = true;
					openBedroom(4, 3);
				} else {
					gp.stage.showOpenDoorBed = false;
				}
			}
			
			if (gp.stage.mapId == 3 && gp.currentCheckpoint == 0) {
				if (hit(19, 8, "up")) pingKai(19, 8);
			}
			if (gp.stage.mapId == 5 && gp.currentCheckpoint == 4) {
				if (hit(19, 8, "up")) pingKaiEnd();
			}
			
			// south
			if (gp.stage.mapId == 0 && gp.currentCheckpoint == 4) {
				if (hit(14, 14, "up") || hit(15, 13, "left") || hit(13, 13, "right")) converGrandmom();
			}
			
			if (gp.stage.mapId == 1 && gp.currentCheckpoint == 4 && !gp.stage.talkKanomGrandma) {
				if (hit(19, 10, "left") || hit(18, 11, "up")) converGrandmom();
			}
			if (gp.stage.mapId == 1 && gp.currentCheckpoint == 4 && gp.stage.talkKanomGrandma) {
				if (hit(8, 13, "left") || hit(7, 14, "up")) {
					converGrandmom();
					if (gp.stage.talkGrandma == 0) {
						gp.stage.talkGrandma = 1;
					}
				}
			}
			if (gp.stage.mapId == 1 && gp.currentCheckpoint == 4) {
				// แคร่
				if (hit(13, 15, "right") || hit(14, 14, "down") || hit(15, 15, "left") || hit(14, 16, "up")) litter();
				// ซึ่ง
				if (hit(16, 14, "down") || hit(16, 16, "up")) steamer();
				// กระทะ
				if (hit(17, 15, "right") || hit(18, 14, "down") || hit(19, 15, "left") || hit(18, 16, "up")) pan();
			}
			if (gp.stage.mapId == 1 && gp.currentCheckpoint == 4 && gp.stage.finishKanom) {
				if (hit(19, 8, "up")) gp.stage.mapId = 2;
			}
			if (gp.stage.mapId == 2 && gp.currentCheckpoint == 4) {
				if (hit(14, 7, "up") || hit(15, 7, "up")) gp.stage.mapId = 3;
			}
			if (gp.stage.mapId == 3 && gp.currentCheckpoint == 4) {
				if (gp.eHandler.near(12, 9, gp.TILE_SIZE * 2)) gp.stage.showMrap = true;
				else gp.stage.showMrap = false;
				
				if (hit(12, 9, "left") ) mrap();
			}
		}
		
		// hospital
		if (gp.stage.mapId == 1 && gp.currentCheckpoint == 0) {
			if (gp.eHandler.near(29, 9, gp.TILE_SIZE * 2) == true) {
				
				if (!itemsAdded) {
					gp.stage.checklist.add(new ChecklistItem("แก้ว", 1, 0));
					gp.stage.checklist.add(new ChecklistItem("ธูป", 1, 0));
					gp.stage.checklist.add(new ChecklistItem("ไฟแช็ก", 1, 0));
					
					gp.obj[1][1] = new OBJ_Lighter(gp);
			        gp.obj[1][1].worldX = 29 * gp.TILE_SIZE;
			        gp.obj[1][1].worldY = 18 * gp.TILE_SIZE;
					
					gp.gameState = gp.DIALOGUE_STATE;
					gp.ui.dialogueManager.startDialogue("คุณต้องไปเก็บของตามรายการ\n กด Q เพื่อเปิดดูรายการ");
					itemsAdded = true;
				}
				
			}
		}
		
	}
	
	public boolean hit(int col, int row, String reqDirection) {
		
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col][row].x = col * gp.TILE_SIZE + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.TILE_SIZE + eventRect[col][row].y;
		
		if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}
	
	public boolean near(int col, int row, int radius) {
		int eventX = col * gp.TILE_SIZE + gp.TILE_SIZE / 2;
		int eventY = row * gp.TILE_SIZE + gp.TILE_SIZE / 2;
		int playerX = gp.player.worldX + gp.player.solidArea.x + gp.player.solidArea.width / 2;
		int playerY = gp.player.worldY + gp.player.solidArea.y + gp.player.solidArea.height / 2;
		
		int dx = playerX - eventX;
		int dy = playerY - eventY;

		double distance = Math.sqrt(dx * dx + dy * dy);
		
		return distance < radius;
	}
	
	public int nearDoor(int radius) {
		
		for (int i = 0; i < gp.door[gp.stage.mapId].length; i++) {
			if (gp.door[gp.stage.mapId][i] != null) {
				int eventX = gp.door[gp.stage.mapId][i].worldX + gp.TILE_SIZE / 2;
				int eventY = gp.door[gp.stage.mapId][i].worldY + gp.TILE_SIZE / 2;
				 
				 int playerX = gp.player.worldX + gp.player.solidArea.x + gp.player.solidArea.width / 2;
			    int playerY = gp.player.worldY + gp.player.solidArea.y + gp.player.solidArea.height / 2;
			    
			    int dx = playerX - eventX;
			    int dy = playerY - eventY;

			    double distance = Math.sqrt(dx * dx + dy * dy);

			    if (distance < radius) {
			    	return i;
			    }
			}
		}
		
		return -1;
	}

	
	public void healingPool(int col, int row, int gameState) {
		
		if (gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.playSE(2);
			gp.ui.currentDialogue = "You drinking the water\n healing full.";
			gp.player.life = gp.player.maxLife;
			gp.aSetter.setMonster();
		}
	}

	
	public void playGlass(int col, int row) {
		
		if (gp.keyH.ePressed == true) {
		
			if (gp.stage.missionChecklistCount == gp.stage.checklist.size()) {
				gp.gameState = gp.MINIGAME_STATE;
				gp.ui.miniGameScreenState = 0;
				gp.mSetter.Answer = "เริ่ม";
			} else {
				if (!gp.ui.hasShowedMessage && gp.stage.mapId == 1 && gp.currentCheckpoint == 0)  {
					gp.ui.addMessage("หาของให้ครบก่อน ยังไม่ให้เล่น");
					gp.ui.hasShowedMessage = true;
				}
			}
		}
	}
	
	public void openBedroom(int col, int row) {
		
		if (gp.keyH.ePressed == true) {
			gp.gameState = gp.LOADING_STATE;
			gp.ui.loadingScreenState = 0;
			gp.stage.mapId = 3;
		}
	}
	
	public void pingKaiEnd() {
		if (gp.stage.kaiEnd == 0) {
			String playerName = PlayerSession.getPlayerName();
			gp.gameState = gp.DIALOGUE_STATE;
			gp.ui.dialogueManager.startDialogue(
					playerName + ": ลุงครับ นี่ ผมตามหามันครบแล้วครับ\t หิน \"Javarock\" นี่เองหรอครับที่จะช่วยล้างคำสาปได้\n "
					+ "ลุง: ใช้แล้วล่ะ เอ็งเก่งมาก\t เราจะต้องกลับไปยังโรงพยาบาลอีกครั้ง กลับไปยังจุดเริ่มต้นของเรื่องทั้งหมด\t แล้วเราจะไปจบเรื่องนี้กัน"
					);
			gp.stage.kaiEnd++;
		} else if (gp.stage.kaiEnd == 1) {
			gp.player.hasrock1 = false;
	 		gp.player.hasrock2 = false;
	 		gp.player.hasrock3 = false;
	 		gp.player.hasrock4 = false;
	 		
			gp.gameState = gp.LOADING_STATE;
			gp.stage.mapId = 6;
		}
	}
	
	public void pingKai(int col, int row) {
		if (gp.stage.kaiGo == 0) {
			gp.gameState = gp.DIALOGUE_STATE;
			String playerName = PlayerSession.getPlayerName();
			gp.ui.dialogueManager.startDialogue(
					"ลุง: อ้าว ลมอะไรพัดมาถึงนี่เนี่ย มาซื้อไก่ลุงหรอ\n "
					+ playerName + ": แหะๆ ครับ แล้วก็มีเรื่องอยากปรึกษาด้วยครับ\n "
					+ "ลุง: มีอะไรล่ะ บอกมาได้เลย\n "
					+ playerName + ": ลุงครับ ช่วงนี้ผมรู้สึกแปลกๆมากเลยครับ \tรู้สึกเหมือนมีอะไรตามตลอดเวลาเลย\n "
					+ "ลุง: แล้วก่อนหน้านี้เอ็งไปทำอะไรมา\n "
					+ playerName + ": ก่อนหน้านี้ ผมไปทำคอนเทนต์เล่นผีถ้วยแก้วในโรงพยาบาลร้างหลังหมู่บ้านมาครับ \tมันมีเบอร์แปลกๆโทรมาเสนอให้ทำคอนเทนต์ \tแล้วให้เงินผมจริงๆด้วย ผมเลยเลือกจะไปครับลุง\n "
					+ "ลุง: ห้ะ... เอ็งหมายถึง โรงพยาบาลบวรเวทย์ น่ะหรอ\n "
					+ playerName + ": ใช่ครับ ทำไมหรอครับลุง\n "
					+ "ลุง: เอ้า เอ็งไปได้ไงเนี่ย รู้ไหมว่าที่นั่นมันเฮี้ยนมากนะ \tก่อนหน้านี้มันก็เคยเป็นโรงพยาบาลปกตินี่แหละ แต่อยู่ดีๆ \tคนไข้ในโรงพยาบาลก็ทยอยตายกันเรื่อยๆ จนทำให้โรงพยาบาลต้องปิดตัวลง \tคนเฒ่าคนแก่เคยเล่าว่า มีคนเล่นของในโรงพยาบาล เลยทำให้คนล้มตายกัน \tลุงได้ยินมาว่า ที่นั่นมีคำสาป หากใครไปลบหลู่นะไม่เคยจบดีสักราย\n "
					+ playerName + ": ลุงครับ.... ผมจะตายไหมครับ ผมไม่น่าไปทำเลย\n "
					+ "ลุง: ใจเย็นๆหน่า มันยังพอมีวิธีแก้อยู่ ลุงพอจะนึกออกอยู่\\t ถึงลุงจะไม่เคยใช้วิชามานาน แต่ก็พอเหลือติดตัวอยู่บ้าง\n "
					+ "ลุง: ลุงจำได้ว่า มันจะมีชั้นส่วนศักดิ์สิทธิ์อยู่ 4 ชิ้น \tที่เมื่อประกอบกันแล้วจะมีพลัง แข็งแกร่งมากจนสามารถล้มล้างคำสาปทุกอย่างได้ \tแต่กว่าจะได้มันมา เราคงต้องสู้ชีวิตหน่อย \tเพราะชิ้นส่วนนี้มันจะกระจายอยู่ทั่ว 4 ภาคในประเทศไทย โดยจะมีแค่ภาคละ 1 ชิ้น \tเอ็งต้องตามหาชิ้นส่วนศักดิ์สิทธิ์ทั้ง 4 ชิ้นให้เจอ \tแล้วมาประกอบกันเพื่อให้ลุงมาทำพิธี ล้างคำสาปให้ เอ็งก็จะหายเป็นปกติได้\n "
					+ playerName + ": ถ้ามันไม่เหลือทางแล้ว ผมก็จะทำครับลุง ขอบคุณนะครับลุง"
 					);
			gp.stage.kaiGo++;
		} else if (gp.stage.kaiGo == 1) {
			gp.gameState = gp.DIALOGUE_STATE;
			gp.ui.dialogueManager.startDialogue("ยังไม่ไปอีก\n ไปได้แล้ว");
			gp.stage.kaiGo++;
		} else if (gp.stage.kaiGo == 2) {
			gp.gameState = gp.DIALOGUE_STATE;
			gp.ui.dialogueManager.startDialogue("ยังอีกกกกก\n ไปๆๆๆๆ");
			gp.stage.kaiGo++;
		} else if (gp.stage.kaiGo == 3) {
			gp.gameState = gp.DIALOGUE_STATE;
			gp.ui.dialogueManager.startDialogue("รีบไปเลย");
			gp.stage.kaiGo++;
		} else {
			gp.gameState = gp.DIALOGUE_STATE;
			gp.ui.dialogueManager.startDialogue("ไปเดี๋ยวนี้!!!!!");
			gp.stage.kaiGo++;
		}
		
	}
	
	public void converGrandmom() {
		
		if (gp.keyH.ePressed) {
			gp.gameState = gp.DIALOGUE_STATE;
			String playerName = PlayerSession.getPlayerName();
			if (gp.stage.mapId == 0 && gp.currentCheckpoint == 4) {
				gp.ui.dialogueManager.startDialogue(
						"ยาย: เป็นคนต่างถิ่นสินะ ท่าทางเอ็งไม่รู้เลยว่าที่นี่กำลังเกิดเรื่องใหญ่\n "
						
						+ playerName + ": ขอโทษนะครับยาย ผมแค่เดินทางมาตามหาของบางอย่าง\n "
						
						+ playerName + ": ว่าแต่ มันเกิดอะไรขึ้นหรอครับ?\n "
						
						+ "ยาย: ยายชื่อ \"ยายจันทร์\" นะ ยายอยู่ที่นี่มาตั้งแต่เกิด\t ปีนี้เป็นปีที่เดือนสิบมืดกว่าทุกปี\t เพราะไม่มีใครทำ \"หมรับ\" ให้บรรพบุรุษ\t ผีเปรตมันเลยออกมาอาละวาด\n "
						
						+ playerName + ": ผีเปรต? หมรับ? ผมไม่เข้าใจเลยครับยาย\n "
						
						+ "ยาย: แน่นอน เอ็งเป็นคนต่างถิ่นก็ต้องไม่รู้ ฟังให้ดีนะ\n "
						
						+ "ยาย: ภาคใต้ของเรามีประเพณีชื่อว่า \"วันสารทเดือนสิบ\"\t มันเป็นวันที่เชื่อว่า \"ประตูนรกเปิด\"\t ผีบรรพบุรุษจะกลับมาเยี่ยมลูกหลาน\t แต่ถ้าลูกหลานลืม ไม่ทำบุญ ไม่ใส่บาตร ไม่ทำหมรับ…\t ผีบางตนที่อด หิว ห่วง จะกลายเป็น \"เปรต\"\t สูงเท่าต้นตาล ปากเท่าเข็ม หิวตลอดเวลา\n "
						
						+ playerName + ": แล้ว \"หมรับ\" นั่นคืออะไรเหรอครับ?\n "
						
						+ "ยาย: หมรับ ก็คือห่อขนมที่ลูกหลานจัดให้ผีบรรพบุรุษ ในนั้นมีขนม 5 อย่าง\t แต่ละอย่างเปรียบเสมือนของใช้ในโลกหน้า\t ใครลืมทำ วิญญาณจะไม่มีเสื้อผ้า ไม่มีเรือเดินทาง ไม่มีของกิน\t กลายเป็นเปรต โกรธ เกรี้ยว และบังตาคนทั้งหมู่บ้าน\n "
						
						+ "ยาย: ยายแก่เกินไปแล้วที่จะไปเซ่นเอง เอ็งต้องทำขนมทั้ง 5 อย่างแล้วใส่หมรับ\t และเอ็งมีแววของผู้กล้าอยู่นะ\t หากเอ็งอยากได้ของศักดิ์สิทธิ์ที่เอ็งตามหา\t เอ็งต้องทำหมรับและนำไปให้เปรตที่ห้างเปรตเก่าในป่าหลังวัดน่ะ\t เปรตจึงจะเลิกบังตาของศักดิ์สิทธิ์ที่เอ็งกำลังตามหา\n "
						
						+ playerName + ": ผมไม่เคยทำอะไรแบบนี้เลยนะครับยาย\t แต่ถ้านี่คือทางเดียวที่ผมจะได้ของมา ผมจะลองครับ\n "
						
						+ "ยาย: ดีล่ะ ถ้าอย่างนั้น ตามยายมา ที่บ้านยายมีอุปกรณ์ทำหมรับไว้หมดแล้ว\t เราต้องเริ่มตอนนี้เลย ก่อนที่เปรตจะออกอาละวาด\t และกลืนวิญญาณคนในหมู่บ้านไปมากกว่านี้"
						);
			} else if (gp.stage.mapId == 1 && gp.currentCheckpoint == 4) {
				
				if (!gp.stage.talkKanomGrandma) {
					gp.ui.dialogueManager.startDialogue(
							"ยายจันทร์: การทำหมรับ ไม่ใช่แค่การใส่ขนมทั่วๆไปนะ\t ขนมเดือนสิบมีห้าอย่าง แต่ละอย่างแทนของใช้วิญญาณที่ใช้ใน\t โลกหลังความตาย\n "
							+ "ขนมลา – บางเหมือนผ้า ใช้แทนเสื้อผ้าให้วิญญาณ\n "
							+ "ขนมพอง – คล้ายเรือ พาวิญญาณเดินทางจากนรกกลับโลก\n "
							+ "ขนมบ้า – เป็นของเล่น ใช้ให้ผีได้คลายทุกข์\n "
							+ "ขนมดีซำ หรือ ขนมเจาะหู – ใช้แทนเงินตรา มีลักษณะคล้ายเบี้ยหอย\n"
							+ "ขนมไข่ปลา หรือ ขนมกง – สื่อถึงเครื่องประดับ ใช้ประดับตกแต่งใน\tโลกหลังความตาย\n "
							+ "ยายจันทร์: เอ็งต้องช่วยยายเตรียมของ และทำขนมพวกนี้ด้วยมือ\n"
							+ playerName + ": ได้ครับยาย ผมจะทำตามที่ยายบอกครับ"
							);
				} else {
					
					int num = gp.stage.talkGrandma;
					switch (num) {
						case 1:
							gp.ui.dialogueManager.startDialogue(
									"ยายจันทร์: เอาล่ะ เรามาเริ่มกันเลย\n "
									+ "ยายจันทร์: ยายทำขนมไว้หมดแล้ว เหลือแค่ขนมพองอย่างเดียว\t เอ็งช่วยยายทำหน่อยนะ เดี๋ยวยายจะบอกวิธีทำ\t 1.เอ็งจะต้องนำข้าวเหนียวไปนึ่งในซึ้ง เอาแค่พอประมาณก็พอ\t 2.หลังจากนั้นให้เอ็งนำข้าวเหนียวที่นึ่งเสร็จแล้ว ไปตากแดดไว้บนแคร่\t เพื่อให้ข้าวเหนียวมีความแข็งตัวขึ้น\n 3.จากนั้นเอ็งก็เอาข้าวเหนียวที่ตากแดดแล้วไปทอดในน้ำมันร้อนจัดๆ\t เอ็งก็จะได้ขนมพองที่เสร็จสมบูรณ์\t เอ็งต้องลงมือเองแล้ว จำได้อยู่ใช่ไหม\n "
									+ playerName + ": จำได้ครับ"
									);
							gp.stage.canUseSteamer = true;
							break;
						case 6:
							gp.ui.dialogueManager.startDialogue(
									"ยายจันทร์: เรียบร้อย หมรับนี้คือความหวังสุดท้ายของหมู่บ้าน\t แต่การนำไปเซ่นมันไม่ง่ายนะ\t เอ็งต้องเข้าไปในวัด\t หลบสายตาเปรตให้ดี และวางหมรับไว้ตรงห้างเก่าๆ\n"
									+ "ยายจันทร์: จำไว้ อย่าให้มันเห็นหน้าเอ็งเด็ดขาด ถ้าเอ็งทำได้\t เอ็งก็จะได้ของที่เอ็งต้องการ\n"
									+ playerName + ": ครับยาย ผมพร้อมแล้ว ผมจะลองดูครับ ขอบคุณยายมากครับ\n"
									);
							
							if (!gp.stage.finishKanom) {
								gp.ui.addMessage("ได้รับ หมรับ");
								gp.player.inventory.add(new OBJ_Mrap(gp));
								
								gp.player.inventory.removeIf(item ->
							    item instanceof OBJ_Pong && item.name.equals("Pong"));
								gp.stage.finishKanom = true;
							}
							break;
					}
				}
				
			}
		}
		
	}
	
	public void pan() {
//		gp.gameState = gp.MINIGAME_STATE;
//		gp.ui.miniGameScreenState = 1;
		if (gp.keyH.ePressed) {
			
			int num = gp.stage.talkGrandma;
			switch (num) {
				case 1:
					if (gp.stage.canUsePan) {
						gp.gameState = gp.MINIGAME_STATE;
						gp.ui.miniGameScreenState = 3;
					} else {
						if (!gp.ui.hasShowedMessage) {
							gp.ui.addMessage("ยังไม่ใช้ตอนนี้");
							gp.ui.hasShowedMessage = true;
						}
					}
					break;
				case 0:
					if (!gp.ui.hasShowedMessage) {
						gp.ui.addMessage("ถามยายก่อน ทำไม่เป็น");
						gp.ui.hasShowedMessage = true;
					}
					break;
				case 6:
					if (!gp.ui.hasShowedMessage) {
						gp.ui.addMessage("เอาขนมไปให้ยาย");
						gp.ui.hasShowedMessage = true;
					}
					break;
			}
		}
	}
	
	public void litter() {
		
		if (gp.keyH.ePressed) {
			
			int num = gp.stage.talkGrandma;
			switch (num) {
				case 1:
					if (gp.stage.canUseLitter) {
						gp.gameState = gp.MINIGAME_STATE;
						gp.ui.miniGameScreenState = 1;
					} else {
						if (!gp.ui.hasShowedMessage) {
							gp.ui.addMessage("ยังไม่ใช้ตอนนี้");
							gp.ui.hasShowedMessage = true;
						}
					}
					break;
				case 0:
					if (!gp.ui.hasShowedMessage) {
						gp.ui.addMessage("ถามยายก่อน ทำไม่เป็น");
						gp.ui.hasShowedMessage = true;
					}
					break;
				case 6:
					if (!gp.ui.hasShowedMessage) {
						gp.ui.addMessage("เอาขนมไปให้ยาย");
						gp.ui.hasShowedMessage = true;
					}
					break;
			}
		}
	}
	
	public void steamer() {
		
		if (gp.keyH.ePressed) {
			
			int num = gp.stage.talkGrandma;
			switch (num) {
				case 1:
					if (gp.stage.canUseSteamer) {
						gp.gameState = gp.MINIGAME_STATE;
						gp.ui.miniGameScreenState = 2;
					} else {
						if (!gp.ui.hasShowedMessage) {
							gp.ui.addMessage("ยังไม่ใช้ตอนนี้");
							gp.ui.hasShowedMessage = true;
						}
					}
					break;
				case 0:
					if (!gp.ui.hasShowedMessage) {
						gp.ui.addMessage("ถามยายก่อน ทำไม่เป็น");
						gp.ui.hasShowedMessage = true;
					}
					break;
				case 6:
					if (!gp.ui.hasShowedMessage) {
						gp.ui.addMessage("เอาขนมไปให้ยาย");
						gp.ui.hasShowedMessage = true;
					}
					break;
			}
		}
	}
	
	public void mrap() {
		
		if (gp.keyH.ePressed) {
			
			gp.stage.mapId = 4;
			
			 gp.player.inventory.removeIf(item ->
			    item instanceof OBJ_Mrap && item.name.equals("Mrap")
			);
			
			if (!gp.ui.hasShowedMessage) {
				gp.ui.addMessage("เปรตหยุดอาละวาด");
				gp.ui.hasShowedMessage = true;
			}
		}
	}
}
