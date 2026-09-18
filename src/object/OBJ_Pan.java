package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pan extends Entity {

	public OBJ_Pan(GamePanel gp) {

		super(gp);

		name = "Pan";
		down1 = setup("/objects/pan", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		showE = true;
		showTextBtn = "ใช้กระทะ";
	}
}
