package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Incense extends Entity {

	public OBJ_Incense(GamePanel gp) {

		super(gp);

		name = "Incense";
		down1 = setup("/objects/incense", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ ธูป ]";
		
		showE = true;
		showTextBtn = "เก็บ";
	}
}

