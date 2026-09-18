package main;

import javax.swing.JFrame;

public class Main {
	
	public static JFrame window;

	public static void main(String[] args) {
		
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("TheLastLive");
//		window.setUndecorated(true);
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		gamePanel.config.loadConfig();
		if (gamePanel.fullScreenOn == true) {
			window.setUndecorated(true);
		}
		
		window.pack(); // ปรับขนาดหน้าต่าง (JFrame) ให้พอดีกับคอมโพเนนต์ภายใน โดยอัตโนมัติ
		
		window.setLocationRelativeTo(null); // คำสั่งนี้ใช้สำหรับ กำหนดตำแหน่งของหน้าต่าง (window) ใน Java Swing null หมายความว่า หน้าต่างจะอยู่กึ่งกลางหน้าจอโดยอัตโนมัติ
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}

}
