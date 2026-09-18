package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lighter extends Entity {
	
	public OBJ_Lighter(GamePanel gp) {

		super(gp);

		name = "Lighter";
		down1 = setup("/objects/lighter", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ ไฟแช็ก ]";
		
		showE = true;
		showTextBtn = "เก็บ";
	}
}
