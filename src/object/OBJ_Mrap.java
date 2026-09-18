package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mrap extends Entity {

	public OBJ_Mrap(GamePanel gp) {

		super(gp);

		name = "Mrap";
		down1 = setup("/kanom/mrap", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ หมรับ ]\n เอาหมรับไปเซ่นเปรต\n เพื่อให้เปรตหยุดอาละวาด";
		
	}
}
