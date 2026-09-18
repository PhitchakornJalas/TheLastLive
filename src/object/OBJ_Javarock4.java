package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Javarock4 extends Entity {

	public OBJ_Javarock4(GamePanel gp) {

		super(gp);

		name = "Javarock4";
		down1 = setup("/stone/javarock4", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ ชิ้นส่วนที่ 4 ]\n ภาคใต้";
		
		showE = true;
		showTextBtn = "เก็บ";
	}
}
