package entity;

import main.GamePanel;

public class NPC_Grandma_south extends Entity {

	public NPC_Grandma_south(GamePanel gp) {

		super(gp);

		name = "Grandmom_south";
		down1 = setup("/npc/grandmom", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		showE = true;
		showTextBtn = "คุย";
		
	}
}