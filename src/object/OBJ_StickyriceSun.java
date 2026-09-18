package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_StickyriceSun extends Entity {

	public OBJ_StickyriceSun(GamePanel gp) {

		super(gp);

		name = "StickyriceSun";
		down1 = setup("/kanom/pong_lv1", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ ข้าวเหนียวตาก ]\n นำไปทอด";
		
	}
}
