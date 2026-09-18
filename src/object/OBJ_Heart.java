package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
	
	public OBJ_Heart(GamePanel gp) {
		
		super(gp);
		
		name = "Heart";
		
		image = setup("/objects/heart_full", gp.TILE_SIZE, gp.TILE_SIZE);
		image2 = setup("/objects/heart_half", gp.TILE_SIZE, gp.TILE_SIZE);
		image3 = setup("/objects/heart_blank", gp.TILE_SIZE, gp.TILE_SIZE);
	}
}
