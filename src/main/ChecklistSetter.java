package main;

public class ChecklistSetter {
	
	GamePanel gp;
	
	public ChecklistSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setChecklist(int mapIndex) {
		
		gp.stage.checklist.clear();
		
//		if (mapIndex == 1) {
//			gp.stage.checklist.add(new ChecklistItem("แก้ว", 1, 0));
//			gp.stage.checklist.add(new ChecklistItem("ธูป", 10, 0));
//			gp.stage.checklist.add(new ChecklistItem("ไฟแช็ค", 1, 1));
//	    }
	}
}