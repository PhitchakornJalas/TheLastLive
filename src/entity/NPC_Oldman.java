package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Oldman extends Entity {

	public NPC_Oldman(GamePanel gp) {

		super(gp);

		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
	}

	public void getImage() {

		up1 = setup("/npc/oldman_up_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/npc/oldman_up_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/npc/oldman_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/npc/oldman_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/npc/oldman_left_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/npc/oldman_left_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/npc/oldman_right_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/npc/oldman_right_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	public void setDialogue() {
		
		dialogues[0] = "Hello, lad.";
		dialogues[1] = "So you' com to .................\n.............?";
		dialogues[2] = "I use .................";
		dialogues[3] = "Well ...............";
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if (actionLockCounter == 120) {
			
			Random random = new Random();
			int i = random.nextInt(100) + 1; // 1 - 100
			
			if (i <= 25) {
				direction = "up";
			}
			if (i > 25 && i <= 50) {
				direction = "down";
			}
			if (i > 50 && i <= 75) {
				direction = "left";
			}
			if (i > 75 && i <= 100) {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
	}
	
	public void speak() {
		
		super.speak();
	}
}
