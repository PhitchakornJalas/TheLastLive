package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Javarock1 extends Entity {

	public OBJ_Javarock1(GamePanel gp) {

		super(gp);

		name = "Javarock1";
		down1 = setup("/stone/javarock1", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ ชิ้นส่วนที่ 1 ]\n ภาคกลาง";
		
//		showE = true;
//		showTextBtn = "เก็บ";
	}
}
