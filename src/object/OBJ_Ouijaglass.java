package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Ouijaglass extends Entity {

	public OBJ_Ouijaglass(GamePanel gp) {

		super(gp);

		name = "Ouijaglass";
		down1 = setup("/objects/ouijaglass", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ แก้ว ]";
		
		showE = true;
		showTextBtn = "เก็บ";
	}
}
