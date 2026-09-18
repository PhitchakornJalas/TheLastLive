package object;

import entity.Entity;
import main.GamePanel;
import main.PlayerSession;

public class OBJ_Cat extends Entity {

	public OBJ_Cat(GamePanel gp) {

		super(gp);

		name = "Cat";
		down1 = setup("/objects/cat", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ แมว ]";
		
		showE = true;
		showTextBtn = "เก็บ";
	}
}
