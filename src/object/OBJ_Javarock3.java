package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Javarock3 extends Entity {

	public OBJ_Javarock3(GamePanel gp) {

		super(gp);

		name = "Javarock3";
		down1 = setup("/stone/javarock3", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ ชิ้นส่วนที่ 3 ]\n ภาคอีสาน";
		
//		showE = true;
//		showTextBtn = "เก็บ";
	}
}
