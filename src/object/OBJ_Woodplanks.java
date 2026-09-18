package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Woodplanks extends Entity {
	
	public OBJ_Woodplanks(GamePanel gp) {

		super(gp);

		name = "Woodplanks";
		down1 = setup("/objects/woodplanks", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ ไม้กระดาน ]";
		
		showE = true;
		showTextBtn = "เก็บ";
	}

}
