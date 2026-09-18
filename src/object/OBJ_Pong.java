package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pong extends Entity {

	public OBJ_Pong(GamePanel gp) {

		super(gp);

		name = "Pong";
		down1 = setup("/kanom/pong_finish3", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ ขนมพอง ]\n";
		
	}
}
