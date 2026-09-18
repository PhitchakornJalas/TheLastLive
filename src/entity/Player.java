package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.ChecklistItem;
import main.GamePanel;
import main.GamePanel.CameraMode;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Diarybook;
import object.OBJ_Incense;
import object.OBJ_Javarock1;
import object.OBJ_Javarock2;
import object.OBJ_Javarock3;
import object.OBJ_Javarock4;
import object.OBJ_Lantern;
import object.OBJ_Lighter;
import object.OBJ_Ouijaglass;

public class Player extends Entity {
	
	KeyHandler keyH;
	
	public final int SCREEN_X;
	public final int SCREEN_Y;
//	int standounter = 0;
//	public int hasKey = 0;
	public boolean attackCanceled = false;
	
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int MAX_INVENTORY_SIZE = 16;
	
	public final int MAX_TABS_INVENTORY_SIZE = 4;
    public Entity[] tabs_inventory = new Entity[MAX_TABS_INVENTORY_SIZE];
	
	public boolean hasDiary = false;
	public boolean hadAddDiary = false;
	public boolean hasrock1 = false;
	public boolean hadAddRock1 = false;
	public boolean hasrock2 = false;
	public boolean hadAddRock2 = false;
	public boolean hasrock3 = false;
	public boolean hadAddRock3 = false;
	public boolean hasrock4 = false;
	public boolean hadAddRock4 = false;
	
	public boolean hasLantern_Hospital = false;
	public boolean hasLighter = false;
	public boolean hasIncense = false;
	
	// use obj
	String currentUse = "";
	public int useIndex, lastUseIndex = -1;
	public boolean useLantern = false;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		
		this.keyH = keyH;

		SCREEN_X = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);
		SCREEN_Y = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		attackArea.width = 36;
		attackArea.height =36;
		
		getPlayerImage();
//		getPlayerAttackImage();
	}
	
	public void setDefaultValues(int x, int y) {
		
		worldX = x;
		worldY = y;
		speed = 4;
		direction = "down";
		
		// player status
		level = 1;
		maxLife = 6;
		life = maxLife;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		attack = getAttack();
		defense = getDefense();
	}
	
	public void setItems() {
		
	}
	
	public int getAttack() {
		
		return 0;
	}
	
	public int getDefense() {
		
		return 0;
	}
	
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/player/boy_up_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/player/boy_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/player/boy_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/player/boy_left_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/player/boy_left_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/player/boy_right_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/player/boy_right_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
//	public void getPlayerAttackImage() {
//		
//		attackUp1 = setup("/player/boy_attack_up_1", gp.TILE_SIZE, gp.TILE_SIZE * 2);
//		attackUp2 = setup("/player/boy_attack_up_2", gp.TILE_SIZE, gp.TILE_SIZE * 2);
//		attackDown1 = setup("/player/boy_attack_down_1", gp.TILE_SIZE, gp.TILE_SIZE * 2);
//		attackDown2 = setup("/player/boy_attack_down_2", gp.TILE_SIZE, gp.TILE_SIZE * 2);
//		attackLeft1 = setup("/player/boy_attack_left_1", gp.TILE_SIZE * 2, gp.TILE_SIZE);
//		attackLeft2 = setup("/player/boy_attack_left_2", gp.TILE_SIZE * 2, gp.TILE_SIZE);
//		attackRight1 = setup("/player/boy_attack_right_1", gp.TILE_SIZE * 2, gp.TILE_SIZE);
//		attackRight2 = setup("/player/boy_attack_right_2", gp.TILE_SIZE * 2, gp.TILE_SIZE);
//	}
	
	public void update() {
		
//		if (attacking == true) {
//			attacking();
//		}
		
		if (hasDiary && !hadAddDiary) {
			inventory.add(new OBJ_Diarybook(gp));
			hadAddDiary = true;
		}
		if (hasrock1 && !hadAddRock1) {
			inventory.add(new OBJ_Javarock1(gp));
			hadAddRock1 = true;
		}
		if (hasrock2 && !hadAddRock2) {
			inventory.add(new OBJ_Javarock2(gp));
			hadAddRock2 = true;
		}
		if (hasrock3 && !hadAddRock3) {
			inventory.add(new OBJ_Javarock3(gp));
			hadAddRock3 = true;
		}
		if (hasrock4 && !hadAddRock4) {
			inventory.add(new OBJ_Javarock4(gp));
			hadAddRock4 = true;
		}
		
		if(keyH.upPressed == true || keyH.downPressed == true || 
				keyH.leftPressed == true || keyH.rightPressed == true) {
			if (keyH.upPressed == true) {
				direction = "up";
			} else if (keyH.downPressed == true) {
				direction = "down";
			} else if (keyH.leftPressed == true) {
				direction = "left";
			} else if (keyH.rightPressed == true) {
				direction = "right";
			}
			
			// check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// check object collision
			int objectIndex = gp.cChecker.checkObject(this, true);
			int objectIndexNear = gp.cChecker.checkNearObject(gp.player, 48);
			pickUpObject(objectIndexNear);
			
			// check npc collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// check monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// check event
			gp.eHandler.checkEvent();

			
			// if collision is false, player can't move
			if (collisionOn == false && keyH.enterPressed == false) {
				
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
//			if (keyH.enterPressed == true && attackCanceled == false) {
//				
//				gp.playSE(7);
//				attacking = true;
//				spriteCounter = 0;
//			}
//			attackCanceled = false;
//			
//			gp.keyH.enterPressed = false;
			
			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			} //else {
//				standCounter++;
//				if(standCounter == 20) {
//					spriteNum = 1;
//					standCounter = 0;
//				}
//			}
		}
		
		// this need to be outside of key if statement
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
	}
	
	public void attacking() {
		
		spriteCounter++;
		
		if (spriteCounter <= 5) {
			spriteNum = 1;
		}
		if (spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			// save the current worldX, worldY, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			// adjust player's worldX/Y for attackArea
			switch(direction) {
				case "up": worldY -= attackArea.height; break;
				case "down": worldY += attackArea.height; break;
				case "left": worldX -= attackArea.width; break;
				case "right": worldX += attackArea.width; break;
			}
			
			// attackArae becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			// check monster collision with the updated worldX, worldY and solidArea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);
			
			// after checking collision, resotre the original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	public void pickUpObject(int i) {
		
		if (i != 999) {
			
			String objectName = gp.obj[gp.stage.mapId][i].name;
		
			switch(objectName) {
			case "Diarybook":
				if (gp.keyH.ePressed == true) {
					hasDiary = true;
					gp.obj[gp.stage.mapId][i] = null;
					gp.stage.showCloseD = true;
					gp.gameState = gp.DIARY_STATE;
//					inventory.add(new OBJ_Diarybook(gp));
					gp.stage.phoneStage0 = false;
					break;
				}
			case "Lantern":
				if (gp.keyH.ePressed == true) {
					hasLantern_Hospital = true;
					gp.obj[gp.stage.mapId][i] = null;
					gp.ui.addMessage("ได้รับ ตะเกียง");
					inventory.add(new OBJ_Lantern(gp));
					
					gp.lights[gp.stage.mapId][8] = null;
					
					gp.stage.showI = true;
					break;
				}
			case "Lighter":
				if (gp.keyH.ePressed == true) {
					hasLighter = true;
					gp.obj[gp.stage.mapId][i] = null;
					gp.ui.addMessage("ได้รับ ไฟแช็ก");
					inventory.add(new OBJ_Lighter(gp));
					
					for (ChecklistItem item : gp.stage.checklist) {
			            if (item.text.equals("ไฟแช็ก")) {
			                item.count += 1;
			                break;
			            }
			        }
					break;
				}
			case "Ouijaglass":
				if (gp.keyH.ePressed == true) {
					gp.obj[gp.stage.mapId][i] = null;
					gp.ui.addMessage("ได้รับ แก้ว");
					inventory.add(new OBJ_Ouijaglass(gp));
					
					for (ChecklistItem item : gp.stage.checklist) {
			            if (item.text.equals("แก้ว")) {
			                item.count += 1;
			                break;
			            }
			        }
					break;
				}
			case "Incense":
				if (gp.keyH.ePressed == true) {
					hasIncense = true;
					gp.obj[gp.stage.mapId][i] = null;
					gp.ui.addMessage("ได้รับ ธูป");
					inventory.add(new OBJ_Incense(gp));
					
					for (ChecklistItem item : gp.stage.checklist) {
			            if (item.text.equals("ธูป")) {
			                item.count += 1;
			                break;
			            }
			        }
					break;
				}
			case "Javarock4":
				if (gp.keyH.ePressed == true) {
					hasrock4 = true;
					gp.obj[gp.stage.mapId][i] = null;
					gp.ui.addMessage("ได้รับ ชิ้นส่วนสุดท้าย");
					
					gp.gameState = gp.LOADING_STATE;
					gp.stage.mapId = 5;
					break;
				}
			}
		}
	}
	
	public void useItem() {
		
		boolean isKeyPressed = false;

		if (gp.keyH.num1Pressed) { useIndex = 0; isKeyPressed = true; }
		else if (gp.keyH.num2Pressed) { useIndex = 1; isKeyPressed = true; }
		else if (gp.keyH.num3Pressed) { useIndex = 2; isKeyPressed = true; }
		else if (gp.keyH.num4Pressed) { useIndex = 3; isKeyPressed = true; }
		
		if (isKeyPressed && useIndex != lastUseIndex) {
			
			if (tabs_inventory[useIndex] != null) {
				currentUse = tabs_inventory[useIndex].name;
			} else {
				currentUse = "";
			}
		
			switch(currentUse) {
			case "Lantern":
				useLantern = true;
				break;
				
			case "":
				useLantern = false;
				break;
			}
			
			lastUseIndex = useIndex;
			
		}
		
	}
	
	public void interactNPC(int i) {
		
		if (gp.keyH.enterPressed == true) {
			
			if (i != 999) {
				
				attackCanceled = true;
				gp.gameState = gp.DIALOGUE_STATE;
				gp.npc[i].speak();
			}
		}
	}
	
	public void contactMonster(int i) {
		
		if (i != 999) {
			
			if (invincible == false) {
				gp.playSE(6);
				
				int damage = gp.monster[i].attack - defense;
				if (damage < 0) {
					damage = 0;
				}
				life -= damage;
				invincible = true;
			}
			
		}
	}
	
	public void damageMonster(int i) {
		
		if (i != 999) {
			
			if (gp.monster[i].invincible == false) {
				
				gp.playSE(5);
				
				int damage = attack - gp.monster[i].defense;
				if (damage < 0) {
					damage = 0;
				}
				
				gp.monster[i].life -= damage;
				gp.ui.addMessage(damage + "damage!");
				
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
				if (gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
					gp.ui.addMessage("killed the " + gp.monster[i].name + "!");
					gp.ui.addMessage("Exp " + gp.monster[i].exp);
					exp += gp.monster[i].exp;
					checkLevelUp();
				}
			}
		}
	}
	
	public void checkLevelUp() {
		
		if (exp >= nextLevelExp) {
			
			level++;
			nextLevelExp = nextLevelExp * 2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.gameState = gp.DIALOGUE_STATE;
			gp.ui.currentDialogue = "You are level " + level + " now!\n"
									+ "You feel .........!";
		}
	}
	
	public void draw(Graphics2D g2) {
		
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.TILE_SIZE, gp.TILE_SIZE);
		
		BufferedImage image = null;
		int tempScreenX = SCREEN_X;
		int tempScreenY = SCREEN_Y;
		
		switch(direction) {
		case "up":
			if (attacking == false) {
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;}
			}
			if (attacking == true) {
				tempScreenY = SCREEN_Y - gp.TILE_SIZE;
				if (spriteNum == 1) {image = attackUp1;}
				if (spriteNum == 2) {image = attackUp2;}
			}
			break;
		case "down":
			if (attacking == false) {
				if (spriteNum == 1) {image = down1;}
				if (spriteNum == 2) {image = down2;}
			}
			if (attacking == true) {
				if (spriteNum == 1) {image = attackDown1;}
				if (spriteNum == 2) {image = attackDown2;}
			}
			break;
		case "left":
			if (attacking == false) {
				if (spriteNum == 1) {image = left1;}
				if (spriteNum == 2) {image = left2;}
			}
			if (attacking == true) {
				tempScreenX = SCREEN_X - gp.TILE_SIZE;
				if (spriteNum == 1) {image = attackLeft1;}
				if (spriteNum == 2) {image = attackLeft2;}
			}
			break;
		case "right":
			if (attacking == false) {
				if (spriteNum == 1) {image = right1;}
				if (spriteNum == 2) {image = right2;}
			}
			if (attacking == true) {
				if (spriteNum == 1) {image = attackRight1;}
				if (spriteNum == 2) {image = attackRight2;}
			}
			break;
		}
		
		if (invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		
		 if (gp.cameraMode == CameraMode.FIXED) {
			 
			 g2.drawImage(image, worldX, worldY, null);
		} else {
			g2.drawImage(image, tempScreenX, tempScreenY, null);
		}
		
		// reset alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		// debug
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("invincible: " + invincibleCounter, 10, 400);
	}
}
