package object;

import entity.Entity;
import main.GamePanel;
 
public class OBJ_Litter extends Entity {

	public OBJ_Litter(GamePanel gp) {

		super(gp);

		name = "litter";
		down1 = setup("/objects/litter", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		showE = true;
		showTextBtn = "ใช้แคร่";
	}
}
