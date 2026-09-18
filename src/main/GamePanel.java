package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import light.Light;
import light.LightSetter;
import object.OBJ_Diarybook;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	// screen setting
	final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
	final int SCALE = 3;

	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
	public final int MAX_SCREEN_COL = 16; // 16
	public final int MAX_SCREEN_ROW = 9; // 9
	public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 960 pixels
	public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels
	
	// world setting
	public int MAX_WORLD_COL;
	public int MAX_WORLD_ROW;
//	public final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
//	public final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;
	public final int mapMax = 50;
	public int currentMap = 0;
	
	// for full screen
	int screenWidth2 = SCREEN_WIDTH;
	int screenHeight2 = SCREEN_HEIGHT;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn = false;

	// FPS
	int FPS = 60;

	// system
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Config config = new Config(this);
	Light light = new Light(this);
	LightSetter lSetter = new LightSetter(this);
	ChecklistSetter cSetter = new ChecklistSetter(this);
	MiniGameSetter mSetter = new MiniGameSetter(this);
	Gamesave gamesave = new Gamesave(this);
	Thread gameThread;
	
	// entity and object
	public Player player = new Player(this, keyH);
	public Entity obj[][] = new Entity[mapMax][100];
	public Entity door[][] = new Entity[mapMax][100];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	public Light lights[][] = new Light[mapMax][100];
	public List<Light> lightSources = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	
	// game state
	public int gameState;
	public final int TITLE_STATE = 0;
	public final int PLAY_STATE = 1;
	public final int PAUSE_STATE = 2;
	public final int DIALOGUE_STATE = 3;
	public final int CHARACTER_STATE = 4;
	public final int OPTION_STATE = 5;
	public final int LOADING_STATE = 6;
	public final int DIARY_STATE = 7;
	public final int INVENTORY_STATE = 8;
	public final int CHECKLIST_STATE = 9;
	public final int MINIGAME_STATE = 10;
	public final int QA_STATE = 11;
	
	// checkpoint
	public CheckpointStage stage = new CheckpointStage(this);
	public int currentCheckpoint; // default
	int lastCheckpoint = -1;
	int lastMapId = -1;

	// Set player's default position
//	int playerX = 100;
//	int playerY = 100;
//	int playerSpeed = 4;
	
	// camera
	public enum CameraMode { FOLLOW, FIXED }
	public CameraMode cameraMode = CameraMode.FIXED;

	public GamePanel() {

		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		
//		 aSetter.setNPC();
		 aSetter.setMonster();
		 
//		 playMusic(0); // เปิดด้วย ไม่เปิด error
		 
		gameState = TITLE_STATE;
//		 gameState = MINIGAME_STATE;
		
//		tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D)tempScreen.getGraphics();
		
		if (fullScreenOn == true) {
			setFullScreen();
		}
	}
	
	public void setFullScreen() {
		
		// get local screen device
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		
		// get full screen width and height
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
	}

	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
//	public void run() {
//		
//		while(gameThread != null) {
//			
//			double drawInterval = 1000000000 / FPS; // 0.01666 seconds.
//			double nextDrawTime = System.nanoTime() + drawInterval;
//			
	// System.out.println("the game loop is running");
//			
	// 1.UPDATE: update information such as character positions
//			update();
//			
	// 2.DRAW: draw the screen with the updated information
//			repaint();
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime / 1000000;
//				
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long) remainingTime);
//				
//				nextDrawTime += drawInterval;
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				drawToTempScreen();
				drawToScreen();
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}

		}
	}

	public void update() {

		if (gameState == PLAY_STATE) {
			
			currentCheckpoint = gamesave.loadSaveGame();
			int currentMapId = stage.mapId;
			
			if (currentCheckpoint != lastCheckpoint || currentMapId != lastMapId) {
				
				switch (currentCheckpoint) {
			       case 0:
			    	   stage.stage0();
			           break;
			       case 1:
			    	   stage.stage1();
			    	   break;
			       case 2:
			    	   stage.stage2();
			    	   break;
			       case 3:
			    	   stage.stage3();
			    	   break;
			       case 4:
			    	   stage.stage4();
			    	   break;
			       case 5:
			    	   stage.stage5();
			    	   break;
				}
				eHandler.initEventRect();
				
				aSetter.setObject(currentMapId);
				lSetter.setupLight(currentMapId);
				cSetter.setChecklist(currentMapId);
				
				lastCheckpoint = currentCheckpoint;
				lastMapId = currentMapId;
			}
			
			// update stage
			if (currentCheckpoint == 0) {
				if (currentMapId == 0) {
					stage.updateStage0();
				} else if (currentMapId == 1) {
					stage.updateStage01();
				}else if (currentMapId == 3) {
					stage.updateStage03();
				}
	        } 
			
			// player
			player.update();
			player.useItem();
			// npc
			for (int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
			// monster
			for (int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					if (monster[i].alive == true && monster[i].dying == false) {
						monster[i].update();
					}
					if (monster[i].alive == false) {
						monster[i] = null;
					}
				}
			}
		}
		if (gameState == MINIGAME_STATE) {
			mSetter.miniGame(ui.miniGameScreenState);
			
			if (mSetter != null && mSetter.moving) {
				mSetter.updateSpiritGlassMovement();
		    }
		}
		if (gameState == PAUSE_STATE) {
			// nothing
		}
	}
	
	public void drawToTempScreen() {
		
		// debug
		long drawStart = 0;
		if (keyH.showDebugText == true) {
			drawStart = System.nanoTime();
		}

		// title screen
		if (gameState == TITLE_STATE) {
			ui.draw(g2);
		} else if (gameState == LOADING_STATE) {
			ui.draw(g2);
		} else { // OTHERS
			// tile
			tileM.draw(g2);
			
			// add entity to list
			entityList.add(player);
			
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			
			for (int i = 0; i < obj[stage.mapId].length; i++) {
				if (obj[stage.mapId][i] != null) {
					entityList.add(obj[stage.mapId][i]);
				}
			}
			for (int i = 0; i < door[stage.mapId].length; i++) {
				if (door[stage.mapId][i] != null) {
					if (door[stage.mapId][i].isVisible) {
						entityList.add(door[stage.mapId][i]);
					}
				}
			}
			
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			
			//sort
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					
					int result = Integer.compare(e1.worldY, e2.worldY);
					 return Integer.compare(e1.worldY, e2.worldY);
				}
				
			});
			
			// draw entity
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			
			// empty entity list
			entityList.clear();
			
			// light
			light.draw(g2);
 			
			// UI
			ui.draw(g2);
		}
		
		// debug
		if (keyH.showDebugText == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			
			g2.setFont(new Font("Arial", Font.PLAIN, 20));
			g2.setColor(Color.white);
			int x = 10;
			int y = 200;
			int lineHeight = 20;
			
			g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
			g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
			g2.drawString("Col: " + (player.worldX + player.solidArea.x) / TILE_SIZE, x, y); y += lineHeight;
			g2.drawString("Row: " + (player.worldY + player.solidArea.y) / TILE_SIZE, x, y); y += lineHeight;
			
			g2.drawString("Draw Time: " + passed, x, y);
		}
	}
	
	public void drawToScreen() {
		
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
	}
	
	public void playLSE(int i) {
		
		se.setFile(i);
		se.play();
		se.loop();
	}
	
	public void stopSE() {
		
		se.stop();
	}
}
