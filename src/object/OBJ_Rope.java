package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Rope extends Entity {

	public OBJ_Rope(GamePanel gp) {

		super(gp);

		name = "Rope";
		down1 = setup("/objects/rope", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ เชือก ]\n สวัสดีชาวโลกกกกก\n หนังสืออออออ";
		
		showE = true;
		showTextBtn = "เก็บ";
	}
}
