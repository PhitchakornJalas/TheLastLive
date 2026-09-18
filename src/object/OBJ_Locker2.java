package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Locker2 extends Entity {
	
	public OBJ_Locker2(GamePanel gp) {

		super(gp);

		name = "Locker1";
		down1 = setup("/objects/locker_2", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		isDoor = true;
		showE = true;
		showTextBtn = "เปิด";
	}

}