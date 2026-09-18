package entity;

import main.GamePanel;

public class NPC_Uncle extends Entity {

	public NPC_Uncle(GamePanel gp) {

		super(gp);

		name = "Uncle";
		down1 = setup("/npc/uncle", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
	}
}
