package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Javarock2 extends Entity {

	public OBJ_Javarock2(GamePanel gp) {

		super(gp);

		name = "Javarock2";
		down1 = setup("/stone/javarock2", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ ชิ้นส่วนที่ 2 ]\n ภาคเหนือ";
		
//		showE = true;
//		showTextBtn = "เก็บ";
	}
}
