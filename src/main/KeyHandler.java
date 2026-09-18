package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, ePressed, num1Pressed, num2Pressed, num3Pressed, num4Pressed;
	// debug
	boolean showDebugText = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		 if (gp.ui.titleScreenState == 2 && gp.ui.commandNum == 0 && gp.ui.isTypingName) {
	        char c = e.getKeyChar();

	        if (Character.isLetterOrDigit(c)) {
	            if (gp.ui.playerName.length() < 10) {
	                gp.ui.playerName += c;
	                gp.ui.errorName = false;
	            } else {
	                gp.ui.errorName = true;
	            }
	        } else if (c == '\b' && gp.ui.playerName.length() > 0) {
	            gp.ui.playerName = gp.ui.playerName.substring(0, gp.ui.playerName.length() - 1);
	            if (gp.ui.playerName.length() < 10) {
	                gp.ui.errorName = false;
	            }
	        }
	    }
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		// title state
		if (gp.gameState == gp.TITLE_STATE) {
			
			titleState(code);
		}
		
		// play state
		else if (gp.gameState == gp.PLAY_STATE) {
			
			playState(code);
		}
		
		// pause state
		else if (gp.gameState == gp.PAUSE_STATE) {
			
			pauseState(code);
		}
		
		// dialogue state
		else if (gp.gameState == gp.DIALOGUE_STATE) {
			
			dialogueState(code);
		}
		// character state
		else if (gp.gameState == gp.CHARACTER_STATE) {
			
			characterState(code);
		}
		// option state
		else if (gp.gameState == gp.OPTION_STATE) {
			
			optionState(code);
		}
		// diary state
		else if (gp.gameState == gp.DIARY_STATE) {
			
			diaryState(code);
		}
		// inventory state
		else if (gp.gameState == gp.INVENTORY_STATE) {
			
			inventoryState(code);
		}
		// checklist state
		else if (gp.gameState == gp.CHECKLIST_STATE) {
			
			checklistState(code);
		}
		// minigame state
		else if (gp.gameState == gp.MINIGAME_STATE) {
			
			miniGameState(code);
		}
		// qa state
		else if (gp.gameState == gp.QA_STATE) {
			
			qaState(code);
		}
	}
	
	public void titleState(int code) {
		
		if (gp.ui.titleScreenState == 0) {
			
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 1;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 1) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
					if (gp.ui.commandNumWasSet) {
						gp.ui.commandNum = 1;
					} else {
						gp.ui.commandNum = 0;
					}
				}
				else if (gp.ui.commandNum == 1) {
					System.exit(0);
				}
			}
		}
		
		else if (gp.ui.titleScreenState == 1) {
			
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNumWasSet) {
					if (gp.ui.commandNum < 1) {
						gp.ui.commandNum = 2;
					}
				} else {
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = 2;
					}
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNumWasSet) {
					if (gp.ui.commandNum > 2) {
						gp.ui.commandNum = 1;
					}
				} else {
					if (gp.ui.commandNum > 2) {
						gp.ui.commandNum = 0;
					}
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 2;
					gp.ui.commandNum = 0;
				}
				else if (gp.ui.commandNum == 1) {
					gp.ui.titleScreenState = 3;
					gp.ui.commandNum = 0;
				}
				else if (gp.ui.commandNum == 2) {
					gp.ui.titleScreenState = 0;
					gp.ui.commandNum = 0;
				}
			}
		}
		
		else if (gp.ui.titleScreenState == 2) { // เกมใหม่
			
			if (!gp.ui.isTypingName) { 
				
				if (code == KeyEvent.VK_W) {
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = 2;
					}
				}
				if (code == KeyEvent.VK_S) {
					gp.ui.commandNum++;
					if (gp.ui.commandNum > 2) {
						gp.ui.commandNum = 0;
					}
				}
				
				if (code == KeyEvent.VK_ENTER && gp.ui.commandNum == 0) {
		            gp.ui.isTypingName = !gp.ui.isTypingName; 
		        }
				
				if (code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 1 && !gp.ui.playerName.isEmpty() && !gp.ui.errorName) {
						PlayerSession.setPlayerName(gp.ui.playerName);
						 try {
							String fileName = gp.ui.playerName + ".txt";
							FileWriter writer = new FileWriter("./gamesave/" + fileName);
						    writer.write("0");
						    writer.close();
						    } catch (IOException e) {
						        e.printStackTrace();
						    }
						gp.gameState = gp.LOADING_STATE;
						gp.ui.playerName = "";
					}
					else if (gp.ui.commandNum == 2) {
						gp.ui.titleScreenState = 1;
						gp.ui.commandNum = 0;
						gp.ui.playerName = "";
		                gp.ui.errorName = false;
					}
				}
				
	        } else {
	        	 if (code == KeyEvent.VK_ENTER) {
	                 if (!gp.ui.playerName.isEmpty() && !gp.ui.errorName) {
	                     gp.ui.isTypingName = false;
	                 }
	        	 }
	        }	
		}
		
		else if (gp.ui.titleScreenState == 3) { // เล่นต่อ
			if (gp.ui.hasValidFiles) {
				
				if (code == KeyEvent.VK_W) {
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = gp.ui.menuIndex;
					}
				}
				if (code == KeyEvent.VK_S) {
					gp.ui.commandNum++;
					if (gp.ui.commandNum > gp.ui.menuIndex) {
						gp.ui.commandNum = 0;
					}
				}
				
				if (code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum >= 0 && gp.ui.commandNum < gp.ui.menuIndex) {
						gp.gameState = gp.LOADING_STATE;
					}
					
					if (gp.ui.commandNum == gp.ui.menuIndex) {
						gp.ui.titleScreenState = 1;
						if (gp.ui.commandNumWasSet) {
							gp.ui.commandNum = 1;
						} else {
							gp.ui.commandNum = 0;
						}
					}
				}
			} else {
				if (code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 0) {
						gp.ui.titleScreenState = 1;
						if (gp.ui.commandNumWasSet) {
							gp.ui.commandNum = 1;
						} else {
							gp.ui.commandNum = 0;
						}
					}
				}
			}
		}
		
		else if (gp.ui.titleScreenState == 4) {
			if (gp.ui.commandNum == 0) {
				System.exit(0);
			}
		}
		
	}
	
	public void playState(int code) {
		
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		} 
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_P) {
			gp.ui.phoneCommandNum = 0;
			gp.gameState = gp.PAUSE_STATE;
		}
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.CHARACTER_STATE;
		}
		if (code == KeyEvent.VK_ENTER) {
//			enterPressed = true;
		}
		if (code == KeyEvent.VK_E) {
			ePressed = true;
		}
		if (code == KeyEvent.VK_I) {
			gp.gameState = gp.INVENTORY_STATE;
			gp.ui.invetorySlot = 0;
			
			if (gp.stage.mapId == 1) {
				gp.stage.showI = false;
			}
		}
		if (code == KeyEvent.VK_Q && !gp.stage.checklist.isEmpty()) { 
			gp.gameState = gp.CHECKLIST_STATE;
		}
		
		if (code == KeyEvent.VK_1) {
			num1Pressed = true;
			 gp.ui.showedNum = -1;
		}
		if (code == KeyEvent.VK_2) {
			num2Pressed = true;	
			gp.ui.showedNum = -1;
		}
		if (code == KeyEvent.VK_3) {
			num3Pressed = true;
			gp.ui.showedNum = -1;
		}
		if (code == KeyEvent.VK_4) {
			num4Pressed = true;
			gp.ui.showedNum = -1;
		}
		
		// debug
		if (code == KeyEvent.VK_T) {
			if (showDebugText == false) {
				showDebugText = true;
			} else if (showDebugText == true) {
				showDebugText = false;
			}
		}
//		if (code == KeyEvent.VK_R) {
//			gp.tileM.loadMap("/maps/maptest.txt");
//		}
	}
	
	public void pauseState(int code) {
		
		if (code == KeyEvent.VK_W) {
			switch (gp.ui.phoneCommandNum) {
			case 3 : gp.ui.phoneCommandNum = 0; break;
			case 4 : gp.ui.phoneCommandNum = 1; break;
			case 5 : gp.ui.phoneCommandNum = 2; break;
			}
		}
		
		if (code == KeyEvent.VK_S) {
			switch (gp.ui.phoneCommandNum) {
			case 0 : gp.ui.phoneCommandNum = 3; break;
			case 1 : gp.ui.phoneCommandNum = 4; break;
			case 2 : gp.ui.phoneCommandNum = 5; break;
			}
		}
		
		if (code == KeyEvent.VK_A) {
			gp.ui.phoneCommandNum--;
			if (gp.ui.phoneCommandNum < 0) {
				gp.ui.phoneCommandNum = 5;
			}
		}
		
		if (code == KeyEvent.VK_D) {
			gp.ui.phoneCommandNum++;
			if (gp.ui.phoneCommandNum > 5) {
				gp.ui.phoneCommandNum = 0;
			}
		}
		
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.PLAY_STATE;
		}
		
		if (code == KeyEvent.VK_ENTER) {
			
			if (gp.ui.phoneCommandNum == 5) { // setting
				gp.ui.commandNum = 0;
				gp.gameState = gp.OPTION_STATE;
			}
			
			if (gp.ui.phoneScreenState == gp.ui.PHONE_STATE) { // รับสายแรก
				
				if (gp.stage.phoneEventTriggered0) {
					
					gp.stage.phoneStage0 = false;
					gp.stage.phoneEventTriggered0 = true;
					gp.ui.phoneScreenState = gp.ui.HOME_STATE;
					
					gp.stopSE();
					gp.stage.showP = false;
					gp.stage.showedP = true;
					
					gp.gameState = gp.DIALOGUE_STATE;
					String playerName = PlayerSession.getPlayerName();
					gp.ui.dialogueManager.startDialogue(
							"บุคคลปริศนา: สวัสดีครับใช่คุณ " + playerName + " หรือเปล่า?\n "
							+ playerName + ": ใช่ครับ มีอะไรหรือป่าวครับ แล้วได้เบอร์ผมมาจากไหนหรอครับ\n "
							+ "บุคคลปริศนา: ผมได้มาจากไหนไม่สำคัญหรอกครับ แต่ผมติดตามคุณอยู่ใน \tโซเชียลนะครับ ผมชอบคอนเทนต์คุณมาก ช่วงนี้คุณมีปัญหาเรื่องเงิน \tอยู่ใช่ไหมล่ะครับ ผมมีข้อเสนอ ผมอยากให้คุณไปทำคอนเทนต์ \tที่โรงพยาบาลร้าง แล้วผมจะให้คุณ30,000บาท คุณสนใจทำไหมครับ?\n "
							+ playerName + ": (หมอนี่ชักจะแปลกๆแฮะ ดูไม่ค่อยน่าไว้ใจเลย Call Center รึเปล่า)\n "
							+ playerName + ": งั้น...เดี๋นวผมขอลองคิดดูก่อนนะครับ\n "
							+ playerName + ":ผมสนใจครับ ไม่ทราบว่าผมต้องทำอะไรบ้างหรอครับ\n"
							+ "บุคคลปริศนา: ดีเลยครับ ถ้าคุณสนใจ ห้คุณมาที่\"โรงพยาบาลบวรเวทย์\" \tโรงพยาบาลร้างหลังหมู่บ้านได้เลยครับ ผมจะเตรียมของรอคุณไว้ \tผมจะรอดูคอนเทนต์คุณนะครับ ขอให้โชคดี\n"
							);
				}
			}
			
		}
		
		if (code == KeyEvent.VK_BACK_SPACE) {
			
			if (gp.ui.phoneScreenState == gp.ui.PHONE_STATE) { // ตัดสายแรก
				
				if (gp.stage.phoneEventTriggered0) {
					
					gp.stage.phoneStage0 = true;
					gp.stage.phoneEventTriggered0 = false;
					gp.ui.phoneScreenState = gp.ui.HOME_STATE;
					
					gp.stopSE();
					gp.stage.showP = false;
					gp.stage.showedP = true;
					
					gp.gameState = gp.PLAY_STATE;
				}
			}
		}
	}
	
	
	public void dialogueState(int code) {
		
		if (code == KeyEvent.VK_ENTER) {
	        gp.ui.dialogueManager.nextLine();

	        if (gp.ui.dialogueManager.isDialogueFinished()) {
	        	
	        	if (gp.stage.phoneEventTriggered0 && gp.stage.mapId == 0 && gp.currentCheckpoint == 0) {
	        		gp.stage.mapId = 1;
	        		gp.ui.loadingScreenState = 0;
	        		gp.gameState = gp.LOADING_STATE;
	        	} else if (gp.stage.mapId == 0 && gp.currentCheckpoint == 4) {
	        		gp.stage.mapId = 1;
	        		gp.ui.loadingScreenState = 0;
	        		gp.gameState = gp.LOADING_STATE;
	        	} else if (gp.stage.mapId == 1 && gp.currentCheckpoint == 4 && !gp.stage.talkKanomGrandma) {
	        		gp.stage.talkKanomGrandma = true;
	        		
	        		gp.obj[gp.stage.mapId][3].worldX = gp.TILE_SIZE * 7;
	    			gp.obj[gp.stage.mapId][3].worldY = gp.TILE_SIZE * 13;
					
	        		gp.gameState = gp.PLAY_STATE;
	        	} else {
	        		gp.gameState = gp.PLAY_STATE;
	        	}
				
	        }
	    }
	}
	
	public void characterState(int code) {
		
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.PLAY_STATE;
		}
	}
	
	public void diaryState(int code) {
		
		if (code == KeyEvent.VK_BACK_SPACE) {
			gp.gameState = gp.PLAY_STATE;
			
			// ฉาก 1 ปิดแล้วโทรศัพท์เข้า
			if (gp.currentCheckpoint == 0 && gp.stage.phoneStage0 == false) {
				gp.stage.showCloseD = false;
				gp.stage.phoneStage0 = true;
			}
		}
		
		if (code == KeyEvent.VK_A) {
			gp.ui.currentPage--;
			if (gp.ui.currentPage < 0) {
				gp.ui.currentPage = 0;
			}
		}
		
		if (code == KeyEvent.VK_D) {
			gp.ui.currentPage++;
			if (gp.ui.currentPage > gp.ui.pages.length - 2) {
				gp.ui.currentPage = gp.ui.pages.length - 2;
			}
		}
	}
	
	public void inventoryState (int code) {
		
		enterPressed = false;
		
		if (code == KeyEvent.VK_I) {
			gp.gameState = gp.PLAY_STATE;
		}
		
		if (gp.ui.inventory_state == 0) {
			
			if (code == KeyEvent.VK_A) {
				gp.ui.invetorySlot--;
				if (gp.ui.invetorySlot < 0) {
					gp.ui.invetorySlot = 15;
				}
			}
			
			if (code == KeyEvent.VK_D) {
				gp.ui.invetorySlot++;
				if (gp.ui.invetorySlot > 15) {
					gp.ui.invetorySlot = 0;
				}
			}
			
			if (code == KeyEvent.VK_W) {
				switch (gp.ui.invetorySlot) {
				case 4 : case 8 : case 12 : gp.ui.invetorySlot = gp.ui.invetorySlot - 4; break;
				case 5 : case 9 : case 13 : gp.ui.invetorySlot = gp.ui.invetorySlot - 4; break;
				case 6 : case 10 : case 14 : gp.ui.invetorySlot = gp.ui.invetorySlot - 4; break;
				case 7 : case 11 : case 15 : gp.ui.invetorySlot = gp.ui.invetorySlot - 4; break;
				}
			}
			
			if (code == KeyEvent.VK_S) {
				switch (gp.ui.invetorySlot) {
				case 0 : case 4 : case 8 : gp.ui.invetorySlot = gp.ui.invetorySlot + 4; break;
				case 1 : case 5 : case 9 : gp.ui.invetorySlot = gp.ui.invetorySlot + 4; break;
				case 2 : case 6 : case 10 : gp.ui.invetorySlot = gp.ui.invetorySlot + 4; break;
				case 3 : case 7 : case 11 : gp.ui.invetorySlot = gp.ui.invetorySlot + 4; break;
				}
			}
		} else if (gp.ui.inventory_state == 1) {
			
			if (code == KeyEvent.VK_W) {
				gp.ui.tabs_inventorySlot--;
				if (gp.ui.tabs_inventorySlot < 0) {
					gp.ui.tabs_inventorySlot = 3;
				}
			}
			
			if (code == KeyEvent.VK_S) {
				gp.ui.tabs_inventorySlot++;
				if (gp.ui.tabs_inventorySlot > 3) {
					gp.ui.tabs_inventorySlot = 0;
				}
			}
			
			if (code == KeyEvent.VK_BACK_SPACE) {
				gp.ui.inventory_state = 0;
			}
		}
		
		
		
		if (code == KeyEvent.VK_ENTER ) {
			
			if (gp.ui.inventory_state == 1) {
				gp.ui.enter_tabsInventory = true;
			} else {
				gp.ui.enter_invetory = true;
			}
			
			if (gp.player.hasDiary && gp.ui.invetorySlot == 0) {
				gp.gameState = gp.DIARY_STATE;
			}
		}
	}
	
	public void checklistState(int code) {
		
		if (code == KeyEvent.VK_Q) {
			gp.gameState = gp.PLAY_STATE;
		}
	}
	
	public void miniGameState(int code) {
		
		if (code == KeyEvent.VK_ENTER)  {
			enterPressed = true;
		}
		
		
		if (gp.ui.miniGameScreenState == 2) {
			if (code == KeyEvent.VK_W) {
				gp.ui.selectKanom--;
				if (gp.ui.selectKanom < 0) {
					gp.ui.selectKanom = gp.ui.maxSelectKanom;
				}
			}
			
			if (code == KeyEvent.VK_S) {
				gp.ui.selectKanom++;
				if (gp.ui.selectKanom > gp.ui.maxSelectKanom) {
					gp.ui.selectKanom = 0;
				}
			}
		}
	}

	public void qaState(int code) {
		if (gp.ui.miniGameScreenState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.answerNum--;
				if (gp.ui.answerNum < 0) {
					gp.ui.answerNum = 1;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.answerNum++;
				if (gp.ui.answerNum > 1) {
					gp.ui.answerNum = 0;
				}
			}
		}
		
		if (code == KeyEvent.VK_ENTER)  {
			enterPressed = true;
		}
	}
	
 	public void optionState(int code) {
		
//		if (code == KeyEvent.VK_BACK_SPACE) {
//			gp.ui.phoneScreenState = 0;
//			gp.ui.subState = 0;
//			gp.gameState = gp.PAUSE_STATE;
//			
//		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		
		int maxCommandNum = 0;
		switch(gp.ui.subState) {
		case 0: maxCommandNum = 4; break;
		case 2: maxCommandNum = 1; break;
		}
		
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale > 1) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale > 1) {
					gp.se.volumeScale--;
				}
			}
		}
		if (code == KeyEvent.VK_D) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_E) {
			ePressed = false;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		
		if (code == KeyEvent.VK_1) {
			num1Pressed = false;
		}
		if (code == KeyEvent.VK_2) {
			num2Pressed = false;		
		}
		if (code == KeyEvent.VK_3) {
			num3Pressed = false;
		}
		if (code == KeyEvent.VK_4) {
			num4Pressed = false;
		}
	}

}
