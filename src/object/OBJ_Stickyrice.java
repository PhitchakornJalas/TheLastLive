package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Stickyrice extends Entity {

	public OBJ_Stickyrice(GamePanel gp) {

		super(gp);

		name = "Stickyrice";
		down1 = setup("/kanom/stickyrice_sun", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ ข้าวเหนียว ]\n นำไปตากแดด";
		
	}
}
