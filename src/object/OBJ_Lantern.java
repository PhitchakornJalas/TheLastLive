package object;

import entity.Entity;
import main.GamePanel;
import main.PlayerSession;

public class OBJ_Lantern extends Entity {

	public OBJ_Lantern(GamePanel gp) {

		super(gp);

		name = "Lantern";
		down1 = setup("/objects/lantern", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ ตะเกียง ]\n สามาถใช้ได้";
		
		showE = true;
		showTextBtn = "เก็บ";
		
		canUse = true;
	}
}
