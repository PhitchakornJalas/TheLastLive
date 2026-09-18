package object;

import entity.Entity;
import main.GamePanel;
import main.PlayerSession;

public class OBJ_Diarybook extends Entity {

	public OBJ_Diarybook(GamePanel gp) {

		super(gp);

		name = "Diarybook";
		down1 = setup("/objects/diarybook", gp.TILE_SIZE, gp.TILE_SIZE);
		
		type = 3;
		collision = true;
		
		description = "[ สมุดไดอารี่ ของ " + PlayerSession.getPlayerName() + " ]";
		
		showE = true;
		showTextBtn = "เก็บ";
	}
}
