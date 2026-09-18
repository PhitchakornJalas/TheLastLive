package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Steamer extends Entity {

	public OBJ_Steamer(GamePanel gp) {

		super(gp);

		name = "steamer";
		down1 = setup("/objects/steamer", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		showE = true;
		showTextBtn = "ใช้ซึ้ง";
	}
}
