package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import object.OBJ_Diarybook;
import object.OBJ_Heart;
import object.OBJ_Lighter;
import object.OBJ_Ouijaglass;
import object.OBJ_Pong;
import object.OBJ_Stickyrice;
import object.OBJ_StickyriceSun;
import entity.Entity;
import main.GamePanel.CameraMode;

public class UI {

	GamePanel gp;
	Graphics2D g2;
//	Font purisaB;
	Font arial_40, arial_80B;
	BufferedImage keyImage, heart_full, heart_half, heart_blank;
	public boolean messageOn = false;
//	public String message = "";
//	int messageCounter = 0;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean hasShowedMessage = false;
	
	public boolean gameFinished = false;
	
	public DialogueManager dialogueManager = new DialogueManager();
	public QuestionManager questionManager = new QuestionManager();
	public String currentDialogue = "";
	
	public int commandNum = 0;
	public int titleScreenState = 0; // 0: the first screen 1: secound
	BufferedImage titleScreenImage;
	
	public int loadingScreenState = 0; // default load
	public int loadingCounter = 0;
	
	int subState = 0;
	
	public boolean isTypingName = false;
	String playerName = "";
	boolean errorName = false;
	
	boolean commandNumWasSet = false;
	int menuIndex;
	boolean hasValidFiles = false;
	
	// loading
	
	// diarybook
	public int currentPage = 0;
	public String[] pages;
	
	// inventory
	public int inventory_state = 0;
	public int showedNum = -1;
	
	public int invetorySlot = 0;
	public int currentSlot = 0;
	
	public int tabs_inventorySlot = 0;
	public int currentSlot_tabs_inventory = 0;
	
	public boolean enter_invetory = false;
	public boolean enter_tabsInventory = false;
	
	// phone
	public int phoneScreenState;
	public final int HOME_STATE = 0;
	public final int PHONE_STATE = 4;
	public int phoneCommandNum = 0;
	int phoneMenuIndex;
	
	// minigame
	public int miniGameScreenState = 0;
	public int glassNum = 42;
	int glassIndex;
	public int miniGame0Counter = 0;
	public int miniGame0Delay = 20;
	public int answerNum = 0;
	int answerIndex;
	
	public int maxSelectKanom = 0;
	public int selectKanom = 0;
	
	public int litterState = 0;
	public int steamerState = 0;
	public int panState = 0;
	
//	double playTime;
//	DecimalFormat df = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
//		InputStream is = getClass().getResourceAsStream("/font/______");
//		purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
//		OBJ_Key key = new OBJ_Key(gp);
//		keyImage = key.image;
		
		// create hud object
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
	}
	
	class FileInfo {
	    String name;
	    int value;

	    FileInfo(String name, int value) {
	        this.name = name;
	        this.value = value;
	    }
	}

	
	public void addMessage(String text) {
		
		message.add(text);
		messageCounter.add(0);
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		// title state
		if (gp.gameState == gp.TITLE_STATE) {
			drawTitleScreen();
		}
		
		// loading state
		if (gp.gameState == gp.LOADING_STATE) {
			drawLoadingScreen();
		}
		
		
		// play state
		if (gp.gameState == gp.PLAY_STATE) {
			// do playstate stuff later
			
			drawBtnGuide();
//			drawPlayerLife();
			drawMessage();
		}
		
		// pause state
		if (gp.gameState == gp.PAUSE_STATE) {
			
//			drawPlayerLife();
			drawPauseScreen();
		}
		
		// dialogue state
		if (gp.gameState == gp.DIALOGUE_STATE) {
			
//			drawPlayerLife();
			drawDialogueScreen();
		}
		
		// character state
		if (gp.gameState == gp.CHARACTER_STATE) {
			
			drawCharacterScreen();
		}
		
		// option state
		if (gp.gameState == gp.OPTION_STATE) {
			
			drawOptionScreen();
		}
		
		// diarybook state
		if (gp.gameState == gp.DIARY_STATE) {
			
			drawDairyBook();
			drawBtnGuide();
		}
		
		// inventory state
		if (gp.gameState == gp.INVENTORY_STATE) {
			
			drawInventory();
			drawBtnGuide();
		}
		
		// checklist
		if (gp.gameState == gp.CHECKLIST_STATE) {
			
			drawChecklist();
		}
		
		// qa
		if (gp.gameState == gp.QA_STATE) {
			
			drawMiniGame();
			drawQAScreen();
			
		}
		
		// minigame
		if (gp.gameState == gp.MINIGAME_STATE) {
			
			drawMiniGame();
			drawMessage();
		}
		
	}
	
	public void drawPlayerLife() {
		
//		gp.player.life = 5;
		
		int x = gp.TILE_SIZE / 2;
		int y = gp.TILE_SIZE / 2;
		int i = 0;
		
		// draw max life
		while (i < gp.player.maxLife / 2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.TILE_SIZE;
		}
		
		// reset
		 x = gp.TILE_SIZE / 2;
		 y = gp.TILE_SIZE / 2;
		 i = 0;
		 
		 // draw current life
		 while (i < gp.player.life) {
			 g2.drawImage(heart_half, x, y, null);
			 i++;
			 if (i < gp.player.life) {
				 g2.drawImage(heart_full, x, y, null);
			 }
			 i++;
			 x += gp.TILE_SIZE;
 		 }
		 
	}
	
	public void drawMessage() {
		
		int messageY = gp.TILE_SIZE * 7;
		
		for (int i = 0; i < message.size(); i++) {
			
			if (message.get(i) != null) {
				
				g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18F));
				FontMetrics fm = g2.getFontMetrics();
				int textWidth = fm.stringWidth(message.get(i));
				int messageX = (gp.SCREEN_WIDTH - textWidth) / 2;
				
				g2.setColor(Color.black);
				hilightText(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1;
				messageCounter.set(i,  counter); // set counter to the array
				messageY += 50;
				
				if (messageCounter.get(i) > 180) {
					
					message.remove(i);
					messageCounter.remove(i);
					hasShowedMessage = false;
				}
			}
		}
	}
	
	public void drawTitleScreen() {
		
		if (titleScreenState == 0) {
			
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
			
			// title image 1
			ImageIcon gifIcon = new ImageIcon(getClass().getResource("/titlescreen/TitleScreen0.gif"));
			Image gifImage = gifIcon.getImage();
			g2.drawImage(gifImage, 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null);
			
			String text = "The Last Live";
			int x = gp.TILE_SIZE * 2;
			int y = gp.TILE_SIZE * 3;
			
			// main color
			g2.setColor(Color.white);
			
			// menu
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			
			text = "เริ่มเกม";
			y += gp.TILE_SIZE * 3.5;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}
			
			text = "ออกเกม";
			y += gp.TILE_SIZE;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}
		} else if (titleScreenState == 1) {
			
			BufferedImage Image = setup("/titlescreen/TitleScreen1");
			g2.drawImage(Image, 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null);
			
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
			
			String folderPath = "gamesave";
			File folder = new File(folderPath);

			int txtFileCount = 0;

			if (folder.exists() && folder.isDirectory()) {
			    File[] files = folder.listFiles();
			    
			    if (files != null) {
			        for (File file : files) {
			            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
			                txtFileCount++;
			            }
			        }
			    }
			}
			
			if (txtFileCount < 4) {
				String text = "เกมใหม่";
				int x = getXforCenteredText(text);
				int y = gp.TILE_SIZE * 4;
				g2.drawString(text, x, y);
				if(commandNum == 0) {
					g2.drawString(">", x - gp.TILE_SIZE, y);
				}
			} else {
				if (!commandNumWasSet) {
			        commandNum = 1;
			        commandNumWasSet = true;
			    }
			}
			
			String text = "เล่นต่อ";
			int x = getXforCenteredText(text);
			int y = gp.TILE_SIZE * 5;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}
			
			text = "ย้อนกลับ";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}
			
		} else if (titleScreenState == 2) { // เกมใหม่
			
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
			
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
			
			String prompt = "กรอกชื่อผู้เล่น:";
		    int x = getXforCenteredText(prompt);
		    int y = gp.TILE_SIZE * 3;
		    g2.drawString(prompt, x, y);

		    // กล่องรับ input
		    int boxX = gp.SCREEN_WIDTH / 2 - 150;
		    int boxY = y + 20;
		    int boxW = 300;
		    int boxH = 40;

		    g2.setColor(Color.gray);
		    g2.fillRect(boxX, boxY, boxW, boxH);

		    g2.setColor(Color.white);
		    g2.drawRect(boxX, boxY, boxW, boxH);

		    g2.setColor(Color.black);
		    g2.drawString(playerName, boxX + 10, boxY + 30);
		    
		    g2.setColor(Color.white);
		    if (commandNum == 0) {
		    	g2.drawString(">", x - gp.TILE_SIZE, y);
			}
		    
		    g2.setColor(Color.red);
		    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
	    	String text = "ห้ามชื่อตัวอักษรเกิน 10 ตัวอักษร!";
	    	 x = getXforCenteredText(text);
			 y += gp.TILE_SIZE * 2;
		    if (errorName) {
		    	g2.drawString(text, x, y);
		    }
		    
		    g2.setColor(Color.white);
		    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
		    text = "เริ่มเกม";
		    x = getXforCenteredText(text);
		    y += gp.TILE_SIZE * 1;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}
			
			text = "ย้อนกลับ";
		    x = getXforCenteredText(text);
		    y += gp.TILE_SIZE * 1;
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}
			
		}  else if (titleScreenState == 3) { // เล่นต่อ
			
			ImageIcon gifIcon = new ImageIcon(getClass().getResource("/titlescreen/TitleScreen3.gif"));
			Image gifImage = gifIcon.getImage();
			g2.drawImage(gifImage, 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
			
			String folderPath = "gamesave";
			File folder = new File(folderPath);

			if (folder.exists() && folder.isDirectory()) {
			    File[] files = folder.listFiles();
			    if (files != null) {
			        menuIndex = 0;
			        boolean hasTxtFile = false;

			        // สร้าง list เพื่อเก็บข้อมูลไฟล์และค่าที่อ่านได้
			        List<FileInfo> fileInfoList = new ArrayList<>();

			        for (File file : files) {
			            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
			                hasValidFiles = true;
			                hasTxtFile = true;

			                String fileName = file.getName();
			                String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));

			                String firstLine = "";
			                int value = 0;
			                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			                    firstLine = reader.readLine();
			                    if (firstLine != null && !firstLine.trim().isEmpty()) {
			                        value = Integer.parseInt(firstLine.trim()) * 20;
			                    }
			                } catch (IOException | NumberFormatException e) {
			                    e.printStackTrace();
			                }

			                // เก็บข้อมูลไว้ใน list
			                fileInfoList.add(new FileInfo(nameWithoutExtension, value));
			            }
			        }

			        // เรียงจากค่ามาก → น้อย
			        fileInfoList.sort((a, b) -> Integer.compare(b.value, a.value));

			        // แสดงผลตามลำดับที่เรียงแล้ว
			        for (FileInfo info : fileInfoList) {
			            String displayText = info.name + " - " + info.value + " %";

			            int x = gp.TILE_SIZE * 2;
			            int y = gp.TILE_SIZE * (3 + menuIndex);
			            g2.drawString(displayText, x, y);

			            if (commandNum == menuIndex) {
			                g2.drawString(">", x - gp.TILE_SIZE, y);
			                PlayerSession.setPlayerName(info.name);
			            }

			            menuIndex++;
			        }

			        // ปุ่มย้อนกลับ
			        int x = gp.TILE_SIZE * 2;
			        int y = gp.TILE_SIZE * (3 + menuIndex);
			        g2.drawString("ย้อนกลับ", x, y);
			        if (commandNum == menuIndex) {
			            g2.drawString(">", x - gp.TILE_SIZE, y);
			        }
			    }
			}



			
			if (!hasValidFiles) {
				commandNum = 0;
				
			    String text = "ยังไม่มีข้อมูล";
			    int x = getXforCenteredText(text);
			    int y = gp.TILE_SIZE * 3;
			    g2.drawString(text, x, y);
			    
			    text = "ย้อนกลับ";
			    x = getXforCenteredText(text);
			    y += gp.TILE_SIZE * 3;
			    g2.drawString(text, x, y);
			    if (commandNum == 0) {
			        g2.drawString(">", x - gp.TILE_SIZE, y);
			    }
			}
			
		} else if (titleScreenState == 4) {
			
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
			
			String message = "แล้วเจอกัน";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
			g2.setColor(Color.white);
			
			int x = getXforCenteredText(message);
			int y = gp.SCREEN_HEIGHT / 2 - gp.TILE_SIZE * 2;

			g2.drawString(message, x, y);
			
			message = "The Last Live Next Chapter!";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
			g2.setColor(Color.white);
			
			x = getXforCenteredText(message);
			y += gp.TILE_SIZE;

			g2.drawString(message, x, y);
			
			message = "ออกเกม";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
			g2.setColor(Color.white);
			
			x = getXforCenteredText(message);
			y += gp.TILE_SIZE * 2;

			g2.drawString(message, x, y);
			if (commandNum == 0) {
		    	g2.drawString(">", x - gp.TILE_SIZE, y);
		    	
			}
		}
		
	}
	
	public void drawLoadingScreen() {
		
		if (loadingScreenState == 0) { // default
			
			ImageIcon gifIcon = new ImageIcon(getClass().getResource("/loadingscreen/Loading0.gif"));
			Image gifImage = gifIcon.getImage();
			g2.drawImage(gifImage, 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null);
			
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			
//			String text = "กำลังโหลด";
//			int x = getXforCenteredText(text);
//			int y = gp.TILE_SIZE * 3;
//			g2.drawString(text, x, y);
			
			loadingCounter++;
		    
		    if (loadingCounter > 300) { // FPS = 60, รอ 5 วินาที (60 * 5 = 300 frame)
		        gp.gameState = gp.PLAY_STATE;
		        loadingCounter = 0;
		    }
		} else if (loadingScreenState == 1) { 
			
			ImageIcon gifIcon = new ImageIcon(getClass().getResource("/loadingscreen/Loading1.gif"));
			Image gifImage = gifIcon.getImage();
			g2.drawImage(gifImage, 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null);
			
			loadingCounter++;
		    
		    if (loadingCounter > 300) { // FPS = 60, รอ 5 วินาที (60 * 5 = 300 frame)
		        gp.gameState = gp.PLAY_STATE;
		        loadingCounter = 0;
		    }
		} else if (loadingScreenState == 2) { // กลาง 
			
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
			
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			
			String text = "กลาง";
			int x = getXforCenteredText(text);
			int y = gp.TILE_SIZE * 3;
			g2.drawString(text, x, y);
			
			loadingCounter++;
		    
		    if (loadingCounter > 300) { // FPS = 60, รอ 5 วินาที (60 * 5 = 300 frame)
		        gp.gameState = gp.PLAY_STATE;
		        loadingCounter = 0;
		    }
		}
	}
	
	public void drawPauseScreen() {
		
		int phoneX = gp.TILE_SIZE * 11;
		int phoneY = gp.TILE_SIZE * 3 + 10;
		int phoneW = gp.TILE_SIZE * 4;
		int phoneH = gp.TILE_SIZE * 6;
		
		if (phoneScreenState == HOME_STATE) {
			
			BufferedImage phoneImage = setup("/phone/phone");
			g2.drawImage(phoneImage, phoneX, phoneY, phoneW, phoneH, null);
			
			int rows = 2;
			int cols = 3;
			int gap = 10; 
			int iconW = gp.TILE_SIZE;
			int iconH = gp.TILE_SIZE;
			
			phoneMenuIndex = 0;
			
			for (int row = 0; row < rows; row++) {
			    for (int col = 0; col < cols; col++) {
			        int iconX = phoneX + gap + col * (iconW + gap) + 4;
			        int iconY = phoneY + gap + row * (iconH + gap) + 35;

			        if (phoneCommandNum == phoneMenuIndex) {
			        	g2.setColor(Color.RED);
			        	g2.fillRoundRect(iconX - 3, iconY - 3, iconW + 6, iconH + 6, 20, 20);
					}
			        
			        g2.setColor(Color.WHITE); // icon
			        g2.fillRoundRect(iconX, iconY, iconW, iconH, 20, 20);
			        
			        BufferedImage iconImage = setup("/phone/icon" + phoneMenuIndex);
					g2.drawImage(iconImage, iconX, iconY, iconW, iconH, null);
			        
			        phoneMenuIndex++;
			    }
			}
			
		} else if (phoneScreenState == PHONE_STATE) {
			
			if (gp.stage.phoneEventTriggered0) { // สายปริศนา ฉาก1
				phoneCall("เบอร์ที่ไม่รู้จัก", "02-528-xxxx");
				
				BufferedImage btnImage = setup("/btn/back");
				g2.drawImage(btnImage, phoneX + 37, phoneY + gp.TILE_SIZE * 5 + 20, 32, 16, null);
				
				btnImage = setup("/btn/enter");
				g2.drawImage(btnImage, phoneX + 125, phoneY + gp.TILE_SIZE * 5 + 20, 32, 16, null);
			}
		}

	}
	
	public void phone() {
		
	}
	
	public void phoneCall(String name, String phonenumber) {
		
		int phoneX = gp.TILE_SIZE * 11;
		int phoneY = gp.TILE_SIZE * 3 + 10;
		int phoneW = gp.TILE_SIZE * 4;
		int phoneH = gp.TILE_SIZE * 6;
		
		BufferedImage phoneImage = setup("/phone/phone_call");
		g2.drawImage(phoneImage, phoneX, phoneY, phoneW, phoneH, null);
		
		g2.setColor(Color.WHITE); 
		g2.setFont(g2.getFont().deriveFont(22F));
		
		int length = (int)g2.getFontMetrics().getStringBounds(name, g2).getWidth();
		int textX = phoneX + (phoneW - length) / 2;
		int textY = phoneY + 80;
		g2.drawString(name, textX, textY);
		
		g2.setFont(g2.getFont().deriveFont(16F));
		length = (int)g2.getFontMetrics().getStringBounds(phonenumber, g2).getWidth();
		textX = phoneX + (phoneW - length) / 2;
		textY += 25;
		g2.drawString(phonenumber, textX, textY);
	}
	
	public void drawDialogueScreen() {
		
		// window
		int x = gp.TILE_SIZE * 2;
	    int y = gp.TILE_SIZE * 5;
	    int width = gp.SCREEN_WIDTH - (gp.TILE_SIZE * 4);
	    int height = gp.TILE_SIZE * 4;

	    drawSubWindow(x, y, width, height);

	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18F));
	    x += 15;
	    y += gp.TILE_SIZE / 2 + 20;

	    String line = dialogueManager.getCurrentLine();
	    String[] splitLines = line.split("\t");

	    for (String l : splitLines) {
	        g2.drawString(l, x, y);
	        y += 25;
	    }
	    
	    BufferedImage btnImage = setup("/btn/enter_blue");
		g2.drawImage(btnImage, gp.TILE_SIZE * 12, gp.TILE_SIZE * 8, 32, 16, null);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12F));
		String text = "ถัดไป";
	    g2.drawString(text,  gp.TILE_SIZE * 12 + 42, gp.TILE_SIZE * 8 + 13);
	}
	
	public void drawQAScreen() {
		
		if (!questionManager.isActive()) return;
		
	    int x = gp.TILE_SIZE;
	    int y = gp.TILE_SIZE * 5;
	    int widthQ = gp.SCREEN_WIDTH - (gp.TILE_SIZE * 7);
	    int height = gp.TILE_SIZE * 3;

	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
	    g2.setColor(Color.white);

	    int textX = x + gp.TILE_SIZE;
	    int textY = y + gp.TILE_SIZE;

	    String question = questionManager.getCurrentQuestion();
	    String[] choices = questionManager.getCurrentChoices();
	    
	    if (questionManager.isActive() && !question.isEmpty()) {
	        drawSubWindow(x, y, widthQ, height);
	        g2.drawString(question, textX, textY);
	    }
	    
	    x += widthQ;
	    y = gp.TILE_SIZE * 5;
	    int widthA = gp.SCREEN_WIDTH - (gp.TILE_SIZE * 11);
	    int heightA = (int)(gp.TILE_SIZE * 1.5) ;
	    
	    textX = x + 20;
	    
	    answerIndex = 0;
	    for (int i = 0; i < choices.length; i++) {
	    	
	    	if (answerNum == answerIndex) {
	        	g2.setColor(Color.RED);
	        	g2.fillRect(x + 10, y + 5, widthA - 20, heightA - 10);
	        	
	        	if (gp.keyH.enterPressed && choices[i] != null && !choices[i].isEmpty()) {
	        	    gp.mSetter.Answer = choices[i];
	        	    questionManager.deactivate();
	        	    gp.gameState = gp.MINIGAME_STATE;
	        	    miniGameScreenState = 0;
	        	    gp.keyH.enterPressed = false;
	        	}

			}
	    	
	    	if (choices[i] != null && !choices[i].isEmpty()) {
	    		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12F));
	    		
	            String choiceText = choices[i];
	            drawSubWindow(x, y, widthA, heightA);
	            g2.drawString(choiceText, textX, textY);
	            
	            y += heightA;
		        textY += heightA;
	    	}
	        
	        answerIndex++;
	    }
	}

	
	public void drawCharacterScreen() {
		
		// create a frame
		final int FRAME_X = gp.TILE_SIZE * 2;
		final int FRAME_Y = gp.TILE_SIZE;
		final int FRAME_WIDTH = gp.TILE_SIZE * 5;
		final int FRAME_HEIGHT = gp.TILE_SIZE * 10;
		drawSubWindow(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);
		
		// text 
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = FRAME_X + 20;
		int textY = FRAME_Y + gp.TILE_SIZE;
		final int LINE_HEIGHT = 32;
		
		// names
		g2.drawString("Level", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Life", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Strength", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Dexterity", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Attack", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Defense", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Exp", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Next Level", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Coin", textX, textY);
		textY += LINE_HEIGHT + 20;
		g2.drawString("Weapon", textX, textY);
		textY += LINE_HEIGHT + 15;
		g2.drawString("Shield", textX, textY);
		textY += LINE_HEIGHT;
		
		// values
		int tailX = (FRAME_X + FRAME_WIDTH) - 30;
		// reset textY
		textY = FRAME_Y + gp.TILE_SIZE;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.TILE_SIZE, textY - 15, null);
		textY += gp.TILE_SIZE;
		
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.TILE_SIZE, textY - 15, null);
	}
	
	public void drawDairyBook() {
		
		int diaryX = gp.TILE_SIZE * 2;
		int diaryY = gp.TILE_SIZE;
		int diaryW = gp.TILE_SIZE * 10;
		int diaryH = gp.TILE_SIZE * 7;
		
		BufferedImage diaryImage = setup("/diary/page");
		g2.drawImage(diaryImage, diaryX, diaryY, diaryW, diaryH, null);
		
		g2.setColor(Color.BLACK);
		
		int textX = diaryX + gp.TILE_SIZE * 6;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("res/diary/content.txt"));
			StringBuilder fullContent = new StringBuilder();
			
	        String line;
	        while ((line = br.readLine()) != null) {
	        	fullContent.append(line).append("\n");
	        }
	        
	        pages = fullContent.toString().split(";");
		
	        String[] lines = pages[currentPage].split("\n");
	        
	        g2.setFont(g2.getFont().deriveFont(16F));
	        int textY = diaryY + 60;
	        
	        for (int i = 0; i < lines.length; i++) {
	            String l = lines[i];

	            if (i > 1) {
	            	g2.setFont(g2.getFont().deriveFont(12F));
	                textY += 20;
	            } 

	            g2.drawString(l, textX, textY);
	        }
	        
	        if (currentPage == 0) {
			} else {
				BufferedImage btnImage = setup("/btn/a_blue");
	 			g2.drawImage(btnImage,textX, diaryY * 7, 16, 16, null);
	 			
	 			g2.setFont(g2.getFont().deriveFont(14F));
	 			g2.drawString("หน้าก่อน", textX + 20, diaryY * 7 + 15);
			}
	        
	        if (currentPage == pages.length - 2) {
			} else {
				BufferedImage btnImage = setup("/btn/d_blue");
				g2.drawImage(btnImage, textX + 90, diaryY * 7, 16, 16, null);
				
				g2.setFont(g2.getFont().deriveFont(14F));
				g2.drawString("หน้าถัดไป", textX + 110, diaryY * 7 + 15);
			}
	        
	       br.close();
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
	   
	}
	
	public void drawInventory() {
		
		BufferedImage inventoryImage = setup("/inventory/inventory");
	    
	    int invWidth = gp.TILE_SIZE * 6;
	    int invHeight = gp.TILE_SIZE * 4;

	    int x = (gp.SCREEN_WIDTH - invWidth) / 2;
	    int y = gp.TILE_SIZE + 40;

	    g2.drawImage(inventoryImage, x, y, invWidth, invHeight, null);

	    // detail
	    inventoryImage = setup("/inventory/detail");

	    int detailWidth = gp.TILE_SIZE * 4;
	    int detailHeight = gp.TILE_SIZE + 25;

	    int detailX = x + gp.TILE_SIZE * 2 - 10;
	    int detailY = y + invHeight + 20;

	    g2.drawImage(inventoryImage, detailX, detailY, detailWidth, detailHeight, null);
	    
	    	
    	g2.setColor(Color.WHITE);
    	
	    int cols = 4;
	    int rows = 4;
	    int gap = 10;

	    int slotW = gp.TILE_SIZE - 8;
	    int slotH = gp.TILE_SIZE - 8;
	    
	    currentSlot = 0;
	    
	    // plater's item
	    for (int row = 0; row < rows; row++) {
	        for (int col = 0; col < cols; col++) {
	            int selectX = x + col * 47 + gp.TILE_SIZE * 2;
	            int selectY = y + row * 44 + 14;
	            
	            if (currentSlot < gp.player.inventory.size()) {
	            	
	                g2.drawImage(gp.player.inventory.get(currentSlot).down1, selectX, selectY, slotW - 8, slotH - 8, null);
	            }

	            currentSlot++;
	        }
	    }
	    
	    g2.setColor(Color.RED);
    	
	    currentSlot = 0;
	    // current select
	    for (int row = 0; row < rows; row++) {
	        for (int col = 0; col < cols; col++) {
	            int selectX = x + col * 47 + gp.TILE_SIZE * 2 - 4;
	            int selectY = y + row * 44 + 10;

	            if (invetorySlot == currentSlot) {
	                g2.setColor(Color.RED);
	                g2.setStroke(new BasicStroke(2));
	                g2.drawRect(selectX, selectY, slotW + 2, slotH);
	                
	                if (enter_invetory && currentSlot < gp.player.inventory.size()) {
	                	if (gp.player.inventory.get(currentSlot).canUse) {
	                		inventory_state = 1;
	                		currentSlot_tabs_inventory = 0;
	                	}
	                	
	                }
	                enter_invetory = false;
	            }
	            
	            // detail
	            if (!gp.player.inventory.isEmpty() && invetorySlot == currentSlot && currentSlot < gp.player.inventory.size()) {
	                String description = gp.player.inventory.get(currentSlot).description;
	                if (description != null && !description.trim().isEmpty()) {
	                    String[] lines = description.split("\n");

	                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 10F));
	                    g2.setColor(Color.WHITE);
	                    int lineHeight = 15;

	                    for (int i = 0; i < lines.length; i++) {
	                        g2.drawString(lines[i], detailX + 15, detailY + 25 + i * lineHeight);
	                    }
	                }
	            }

	            currentSlot++;
	        }
	    }
	    	
    		g2.setColor(Color.WHITE);
	    	
		    cols = 1;
		    rows = 4;
		    gap = 10;

		    slotW = gp.TILE_SIZE - 8;
		    slotH = gp.TILE_SIZE - 8;
		    
		    currentSlot_tabs_inventory = 0;
		    // plater's item tab
		    for (int row = 0; row < rows; row++) {
		        for (int col = 0; col < cols; col++) {
		            int selectX = x + col * 47 + 17;
		            int selectY = y + row * 44 + 14;
		            
			            if (gp.player.tabs_inventory[currentSlot_tabs_inventory] != null) {
			            	
			                g2.drawImage(gp.player.tabs_inventory[currentSlot_tabs_inventory].down1, selectX, selectY, slotW - 8, slotH - 8, null);
			            }

		            currentSlot_tabs_inventory++;
		        }
		    }
		    
		    if (inventory_state == 1) {  // tabs_inventory
		    
		    currentSlot_tabs_inventory = 0;
		    // current select
		    for (int row = 0; row < rows; row++) {
		        for (int col = 0; col < cols; col++) {
		            int selectX = x + col * 47 + 13;
		            int selectY = y + row * 44 + 10;

		            if (tabs_inventorySlot == currentSlot_tabs_inventory) {
		                g2.setColor(Color.RED);
		                g2.setStroke(new BasicStroke(2));
		                g2.drawRect(selectX, selectY, slotW + 2, slotH);
		                
		                if (enter_tabsInventory && gp.player.tabs_inventory[currentSlot_tabs_inventory] == null)  {
		                	gp.player.tabs_inventory[currentSlot_tabs_inventory] = gp.player.inventory.get(invetorySlot);
		                	gp.player.inventory.remove(invetorySlot);
		                	
		                	showedNum = currentSlot_tabs_inventory;
		                	
		                    inventory_state = 0;
		                    enter_tabsInventory = false;
		                }
		                enter_tabsInventory = false;
		            }
		            

		            currentSlot_tabs_inventory++;
		        }
		    }
	    }
	    

	}
	
	public void drawChecklist() {
		
		int checklistyX = 0;
		int checklistyY = gp.TILE_SIZE / 2;
		int checklistyW = gp.TILE_SIZE * 4;
		int checklistyH = gp.TILE_SIZE * 4;
		
		BufferedImage checklistyImage = setup("/diary/checkList");
		g2.drawImage(checklistyImage, checklistyX, checklistyY, checklistyW, checklistyH, null);
		
         g2.setColor(Color.BLACK);
         g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
         
         String text = "รายการ";
         FontMetrics fm = g2.getFontMetrics();
         int textX = checklistyX + (checklistyW - fm.stringWidth(text)) / 2;
         int textY = checklistyY + 40;
         g2.drawString(text, textX, textY);
         
         textY += 30;
         
         g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
         for (int i = 0; i < gp.stage.checklist.size(); i++) {
		    String list = gp.stage.checklist.get(i).text;
		    
		    
		    if (list != null && !list.trim().isEmpty()) {
		    	
		    	text = list; 
		    	int x = checklistyX + 35;
		    	int y = textY + 25 * i;
		    	
		        g2.drawString(list, x, y);
		        
		        if (gp.stage.checklist.get(i).maxCount > 1) {
		        	
		        	g2.drawString(gp.stage.checklist.get(i).count + " / " + gp.stage.checklist.get(i).maxCount, x + 100, y);
		        }
		        
		        ChecklistItem item = gp.stage.checklist.get(i);
		        if (item.count == item.maxCount) {
		            fm = g2.getFontMetrics();
		            int textWidth = fm.stringWidth(list) + 10;
		            int textHeight = fm.getHeight();
		            int middleY = y - fm.getAscent() / 2 + 3;

		            g2.setStroke(new BasicStroke(2));
		            g2.drawLine(x - 5, middleY, x + textWidth, middleY);

		            if (!item.counted) {
		            	gp.stage.missionChecklistCount++;
			            item.counted = true; 
		            }
		       
		        }	        
		        
		        
		    }
		}
	}
	
	public void drawMiniGame() {
		
		BufferedImage miniGameImage;
		
		int x = gp.TILE_SIZE * 2;
	 	int y = gp.TILE_SIZE;
		int Width = gp.TILE_SIZE * 12;
	    int Height = gp.TILE_SIZE * 7;
		
		if (miniGameScreenState == 0) {
		    
		    miniGameImage = setup("/minigames/MiniGame0");
		    g2.drawImage(miniGameImage, x, y, Width, Height, null);
		        
		    int cols = 14;
		    int rows = 8;
		    int diameter = 32; 
		    int spacingX = diameter + 4;
		    int spacingY = diameter + 4;
		    
		    glassIndex = 0;

		    for (int row = 0; row < rows; row++) {
 		    	 
		        for (int col = 0; col < cols; col++) {
		        	
		        	if (glassNum == glassIndex) {
		        		
	    		    	 g2.setColor(Color.WHITE);
		        		
			        	int screenX = x + col * spacingX + 35;
			            int screenY = y + row * spacingY + 25;
			            g2.drawOval(screenX, screenY, diameter, diameter);
					}
		            
		            glassIndex++;
		        }
		    }


		}
		
		else if (miniGameScreenState == 1) { // แคร่
			litter();
		}
		
		else if (miniGameScreenState == 2) { // ซึ้ง
			steamer();
		}
		
		else if (miniGameScreenState == 3) { // กระทะ
			pan();
		}
	}
	
	public void pan() {
		
		BufferedImage miniGameImage;
		int x = gp.TILE_SIZE * 2;
	 	int y = gp.TILE_SIZE;
		int Width = gp.TILE_SIZE * 12;
	    int Height = gp.TILE_SIZE * 7;
		 
		 int num = gp.stage.talkGrandma;
			switch (num) {
				case 1:
					if (panState == 0) {
				    	miniGameImage = setup("/minigames/map_oil");
						g2.drawImage(miniGameImage, x, y, Width, Height, null);
						
						g2.setColor(Color.WHITE); 
						 g2.setStroke(new BasicStroke(3)); 
						 g2.drawRect(x, y, Width, Height); 
						 
						 miniGameImage = setup("/btn/enter");
						g2.drawImage(miniGameImage,x + Width / 2 - 202,y + Height / 2 - 10, 64, 32, null);
						g2.drawString("ทอด", x + Width / 2 - 122, y + Height / 2 + 13);
						
						 miniGameImage = setup("/kanom/dish");
						 g2.drawImage(miniGameImage, x + gp.TILE_SIZE * 7, y + gp.TILE_SIZE, null);
						 
						 if (gp.keyH.enterPressed) {
							 panState = 1;
						 }
					} else if (panState == 1) {
						miniGameImage = setup("/minigames/map_oil");
						g2.drawImage(miniGameImage, x, y, Width, Height, null);
						
						g2.setColor(Color.WHITE); 
						 g2.setStroke(new BasicStroke(3)); 
						 g2.drawRect(x, y, Width, Height); 
						 
						 miniGameImage = setup("/kanom/dish");
						 g2.drawImage(miniGameImage, x + gp.TILE_SIZE * 7, y + gp.TILE_SIZE, null);
						 
						 miniGameImage = setup("/kanom/pong_lv1");
						 g2.drawImage(miniGameImage,x + Width / 2 - 202,y + Height / 2 - 10, 32, 32, null);
						 
						 miniGameImage = setup("/kanom/pong_lv1");
						 g2.drawImage(miniGameImage,x + Width / 2 - 152,y + Height / 2, 32, 32, null);
						 
						 miniGameImage = setup("/kanom/pong_lv1");
						 g2.drawImage(miniGameImage,x + Width / 2 - 102,y + Height / 2 - 10, 32, 32, null);
						 
						 loadingCounter++;
						 
						 if (loadingCounter > 120) {
							
							panState = 2;
							 
					        loadingCounter = 0;
					    }
						
					} else if (panState == 2) {
						
						miniGameImage = setup("/minigames/map_oil");
						g2.drawImage(miniGameImage, x, y, Width, Height, null);
						
						g2.setColor(Color.WHITE); 
						 g2.setStroke(new BasicStroke(3)); 
						 g2.drawRect(x, y, Width, Height); 
						 
						 miniGameImage = setup("/kanom/dish");
						 g2.drawImage(miniGameImage, x + gp.TILE_SIZE * 7, y + gp.TILE_SIZE, null);
						 
						 miniGameImage = setup("/kanom/pong_lv2");
						 g2.drawImage(miniGameImage,x + Width / 2 - 202,y + Height / 2 - 10, 32, 32, null);
						 
						 miniGameImage = setup("/kanom/pong_lv2");
						 g2.drawImage(miniGameImage,x + Width / 2 - 152,y + Height / 2, 32, 32, null);
						 
						 miniGameImage = setup("/kanom/pong_lv2");
						 g2.drawImage(miniGameImage,x + Width / 2 - 102,y + Height / 2 - 10, 32, 32, null);
						 
						 loadingCounter++;
						
						if (loadingCounter > 180) {
							
							panState = 3;
							 
					        loadingCounter = 0;
					    }
					} else if (panState == 3) {
						
						miniGameImage = setup("/minigames/map_oil");
						g2.drawImage(miniGameImage, x, y, Width, Height, null);
						
						g2.setColor(Color.WHITE); 
						 g2.setStroke(new BasicStroke(3)); 
						 g2.drawRect(x, y, Width, Height); 
						 
						 miniGameImage = setup("/kanom/pong_finish3");
						 g2.drawImage(miniGameImage, x + gp.TILE_SIZE * 7, y + gp.TILE_SIZE, null);
						
						loadingCounter++;
						
						if (loadingCounter > 180) {
							
					    	 gp.gameState = gp.PLAY_STATE;
					    	 panState = 0;

							 gp.stage.canUsePan = false;
							 
							 if (!gp.ui.hasShowedMessage) {
							 	addMessage("ได้รับ ขนมพอง");
								gp.ui.hasShowedMessage = true;
							 }
							 gp.player.inventory.removeIf(item ->
							    item instanceof OBJ_StickyriceSun && item.name.equals("StickyriceSun")
							);
							 gp.player.inventory.add(new OBJ_Pong(gp));
							 
							gp.stage.talkGrandma = 6;
							 
					        loadingCounter = 0;
					    }
					}
					break;
			}
	}
	
	public void litter() {
		
		BufferedImage miniGameImage;
		int x = gp.TILE_SIZE * 2;
	 	int y = gp.TILE_SIZE;
		int Width = gp.TILE_SIZE * 12;
	    int Height = gp.TILE_SIZE * 7;
		 
		 int num = gp.stage.talkGrandma;
			switch (num) {
				case 1:
					if (litterState == 0) {
				    	miniGameImage = setup("/minigames/litter");
						g2.drawImage(miniGameImage, x, y, Width, Height, null);
						
						g2.setColor(Color.WHITE); 
						 g2.setStroke(new BasicStroke(3)); 
						 g2.drawRect(x, y, Width, Height); 
						
						miniGameImage = setup("/btn/enter");
						g2.drawImage(miniGameImage,x + Width / 2 - 102,y + Height / 2 - 10, 64, 32, null);
						g2.drawString("ตาก", x + Width / 2 - 22, y + Height / 2 + 13);
						
						if (gp.keyH.enterPressed) {
							litterState = 1;
						}
					} else if (litterState == 1) {
						miniGameImage = setup("/minigames/litter_cloth");
						g2.drawImage(miniGameImage, x, y, Width, Height, null);
						
						g2.setColor(Color.WHITE); 
						 g2.setStroke(new BasicStroke(3)); 
						 g2.drawRect(x, y, Width, Height); 
						
						loadingCounter++;
					    
					    if (loadingCounter > 180) {
					    	
					    	 gp.gameState = gp.PLAY_STATE;
							 litterState = 0;
							
							 gp.stage.canUseLitter = false;
							 gp.stage.canUsePan = true;
							 
							 if (!gp.ui.hasShowedMessage) {
							 	addMessage("ได้รับ ข้าวเหนียวตาก");
								gp.ui.hasShowedMessage = true;
							 }
							 gp.player.inventory.removeIf(item ->
							    item instanceof OBJ_Stickyrice && item.name.equals("Stickyrice")
							);
							 gp.player.inventory.add(new OBJ_StickyriceSun(gp));
							 
					        loadingCounter = 0;
					    }
					}
			}
	}
	
	public void steamer() {
		
		BufferedImage miniGameImage;
		int x = gp.TILE_SIZE * 2;
	 	int y = gp.TILE_SIZE;
		int Width = gp.TILE_SIZE * 12;
	    int Height = gp.TILE_SIZE * 7;
		 
		 int num = gp.stage.talkGrandma;
			switch (num) {
				case 1:
					
					if (steamerState == 0) {
						maxSelectKanom = 1;
						
				    	miniGameImage = setup("/minigames/steameropen");
						g2.drawImage(miniGameImage, x, y, Width, Height, null);
						 
						 g2.setColor(Color.WHITE); 
						 g2.setStroke(new BasicStroke(3)); 
						 g2.drawRect(x, y, Width, Height); 
						 
						 miniGameImage = setup("/kanom/steamerdish");
						 g2.drawImage(miniGameImage, x + gp.TILE_SIZE * 7, y + gp.TILE_SIZE, null);
						 if (selectKanom == 0) {
							 g2.setColor(Color.RED);
							 g2.drawOval(x + gp.TILE_SIZE * 7 - 10, y + gp.TILE_SIZE - 10, gp.TILE_SIZE * 2 + 10, gp.TILE_SIZE + 10);
							 
							 if (gp.keyH.enterPressed) {
								 
								 if (!gp.ui.hasShowedMessage) {
									 	addMessage("ยังไม่ใช้อันนี้");
										gp.ui.hasShowedMessage = true;
									}
								 
								 gp.keyH.enterPressed = false;
							 }
						 }
						 
						 miniGameImage = setup("/kanom/stickyrice");
						 g2.drawImage(miniGameImage, x + gp.TILE_SIZE * 7, y + gp.TILE_SIZE * 2, null);
						 if (selectKanom == 1) {
							 g2.setColor(Color.RED);
							 g2.drawOval(x + gp.TILE_SIZE * 7 - 5, y + gp.TILE_SIZE * 2 - 5, gp.TILE_SIZE + 10, gp.TILE_SIZE + 5);
							 
							 if (gp.keyH.enterPressed) {
								 steamerState = 1;
								 selectKanom = 0;
								 gp.keyH.enterPressed = false;
							 }
						 }
						 
				    } else if (steamerState == 1) {
				    	maxSelectKanom = 0;
						
				    	miniGameImage = setup("/minigames/steamer_stickyrice");
						g2.drawImage(miniGameImage, x, y, Width, Height, null);
						 
						 g2.setColor(Color.WHITE); 
						 g2.setStroke(new BasicStroke(3)); 
						 g2.drawRect(x, y, Width, Height); 
						 
						 miniGameImage = setup("/kanom/steamerdish");
						 g2.drawImage(miniGameImage, x + gp.TILE_SIZE * 7, y + gp.TILE_SIZE, null);
						 if (selectKanom == 0) {
							 g2.setColor(Color.RED);
							 g2.drawOval(x + gp.TILE_SIZE * 7 - 10, y + gp.TILE_SIZE - 10, gp.TILE_SIZE * 2 + 10, gp.TILE_SIZE + 10);
							 
							 if (gp.keyH.enterPressed) {
								 steamerState = 2;
								 gp.keyH.enterPressed = false;
							 }
						 }
				    } else if (steamerState == 2) {
						
				    	miniGameImage = setup("/minigames/steamerclose");
						g2.drawImage(miniGameImage, x, y, Width, Height, null);
						 
						 g2.setColor(Color.WHITE); 
						 g2.setStroke(new BasicStroke(3)); 
						 g2.drawRect(x, y, Width, Height); 
						 
						 loadingCounter++;
						    
					    if (loadingCounter > 180) { 
					    	
					    	 gp.gameState = gp.PLAY_STATE;
							 steamerState = 0;
							 
							 gp.stage.canUseSteamer = false;
							 gp.stage.canUseLitter = true;
							 
							 if (!gp.ui.hasShowedMessage) {
							 	addMessage("ได้รับ ข้าวเหนียว");
								gp.ui.hasShowedMessage = true;
							 }
							 gp.player.inventory.add(new OBJ_Stickyrice(gp));
							 
					        loadingCounter = 0;
					    }
						 
						
				    }
					break;
			}
	}
	
	public void drawBtnGuide() {
		
		g2.setColor(Color.BLACK);
		
		int btnW = 16;
		int btnH = 16;
	    
		
		// ปุ่มโทรศัพท์
	    if (gp.stage.showP) {
	    	
	    	int x = gp.player.worldX + gp.TILE_SIZE;
		    int y = gp.player.worldY - gp.TILE_SIZE / 3;
	    	
	    	BufferedImage btnImage = setup("/btn/p");
		    g2.drawImage(btnImage, x, y, btnW, btnH, null);

		    g2.setFont(g2.getFont().deriveFont(12F));
		    hilightText("เปิดโทรศัพท์", x + btnW + 10,  y + 13);

	    }
	    
	    // ปิด diary
	    if (gp.stage.showCloseD) {
	    	
			int diaryW = gp.TILE_SIZE * 10;
		    int x = gp.TILE_SIZE * 2;
		    int y = gp.TILE_SIZE;
		   
		    int screenX = x + diaryW + 21;
	        int screenY = y  + 20;
	        
	        BufferedImage btnImage = setup("/btn/back");
		    g2.drawImage(btnImage, screenX, screenY, 32, 16, null);
		    
		    g2.setFont(g2.getFont().deriveFont(12F));
		    hilightText("ปิด", screenX + 42,  screenY + 13);
	    }
	    
	    // เปิดกระเป๋า
	    if (gp.stage.showI) {
	    	
	    	int x = gp.player.worldX + gp.TILE_SIZE;
		    int y = gp.player.worldY - gp.TILE_SIZE / 3;
		    int screenX = x - gp.player.worldX + gp.player.SCREEN_X;
	        int screenY = y - gp.player.worldY + gp.player.SCREEN_Y;
	        
	    	BufferedImage btnImage = setup("/btn/i");
		    g2.drawImage(btnImage, screenX, screenY, btnW, btnH, null);

		    g2.setFont(g2.getFont().deriveFont(12F));
		    hilightText("เปิดกระเป๋า", screenX + btnW + 10,  screenY + 13);
		    
		    gp.stage.showSelectInventory = true;

	    }
	    
	    // สอนเลือกของ
	    if (gp.stage.showSelectInventory && gp.gameState == gp.INVENTORY_STATE) {
	    	
	    	int invWidth = gp.TILE_SIZE * 6;
		    int invHeight = gp.TILE_SIZE * 4;
		    int x = (gp.SCREEN_WIDTH - invWidth) / 2;
		    int y = gp.TILE_SIZE + 40;
		   
		    int screenX = x + invWidth;
	        int screenY = y  + 20;
	        
	        String[] keys = {"w", "a", "s", "d"};
	        for (int i = 0; i < keys.length; i++) {
	            BufferedImage btnImage = setup("/btn/" + keys[i]);
	            screenX += btnW + 5;
	            g2.drawImage(btnImage, screenX, screenY, btnW, btnH, null);
	        }

		    g2.setFont(g2.getFont().deriveFont(12F));
		    hilightText("เลื่อน", screenX + btnW + 10,  screenY + 13);
		    
		    screenX = x + invWidth + btnW + 5;
		    
		    BufferedImage btnImage = setup("/btn/enter");
		    g2.drawImage(btnImage, screenX, screenY + 30, 32, 16, null);
		    
		    g2.setFont(g2.getFont().deriveFont(12F));
		    hilightText("เลือก", screenX + 42,  screenY + 43);
		    

		    btnImage = setup("/btn/back");
		    g2.drawImage(btnImage, screenX, screenY + 60, 32, 16, null);
		    
		    g2.setFont(g2.getFont().deriveFont(12F));
		    hilightText("ยกเลิกเลือก", screenX + 42,  screenY + 73);
	    }
	    
	    if (showedNum != -1 && gp.gameState == gp.PLAY_STATE) {
    		
    		int x1 = gp.player.worldX + gp.TILE_SIZE;
		    int y1 = gp.player.worldY - gp.TILE_SIZE / 3;
		    int screenX = x1 - gp.player.worldX + gp.player.SCREEN_X;
	        int screenY = y1 - gp.player.worldY + gp.player.SCREEN_Y;
	        int sum = showedNum + 1;
	    	BufferedImage btnImage = setup("/btn/" + sum);
		    g2.drawImage(btnImage, screenX, screenY, 16, 16, null);

		    g2.setFont(g2.getFont().deriveFont(12F));
		    hilightText("ใช้", screenX + 16 + 10,  screenY + 13);
    	}
	    
	    
	    // glass
	    if (gp.eHandler.near(29, 9, gp.TILE_SIZE * 2) && gp.stage.hospitalFloor == 1 && gp.stage.mapId == 1) {
	    	
	    	int x = gp.TILE_SIZE * 29;
		    int y = gp.TILE_SIZE * 9;
		    int screenX = x - gp.player.worldX + gp.player.SCREEN_X;
	        int screenY = y - gp.player.worldY + gp.player.SCREEN_Y;
	        
	    	BufferedImage btnImage = setup("/btn/e");
		    g2.drawImage(btnImage, screenX - 20, screenY - 50, btnW, btnH, null);

		    g2.setFont(g2.getFont().deriveFont(12F));
		    hilightText("เล่นผีถ้วยแก้ว", screenX + btnW - 10,  screenY - 37);
	    	
	    }
	    
	    if (gp.stage.showMrap && gp.stage.mapId == 3) {
	    	
	    	int x = gp.TILE_SIZE * 12;
		    int y = gp.TILE_SIZE * 9;
		    int screenX = x - gp.player.worldX + gp.player.SCREEN_X;
	        int screenY = y - gp.player.worldY + gp.player.SCREEN_Y;
	        
	    	BufferedImage btnImage = setup("/btn/e");
		    g2.drawImage(btnImage, screenX - 20, screenY - 50, btnW, btnH, null);

		    g2.setFont(g2.getFont().deriveFont(12F));
		    hilightText("วางหมรับ", screenX + btnW - 10,  screenY - 37);
	    	
	    }
	    
	    // ปุ่มเปิด ปิด
	    if (gp.stage.showOpenDoorBed && gp.stage.mapId == 2) {
	    	
	    	int screenX = 4 * gp.TILE_SIZE + 25;
	    	int screenY = gp.TILE_SIZE + 25;
	    	
	    	BufferedImage btnImage = setup("/btn/e");
	        g2.drawImage(btnImage, screenX, screenY + 10, btnW, btnH, null);

	        g2.setFont(g2.getFont().deriveFont(12F));
	        hilightText("ออก", screenX + btnW + 10, screenY + 23);
	    }
	    
//	    if (door instanceof OBJ_Locker1 || door instanceof OBJ_Locker2) {
//	        ...
//	    }
	    int nearDoorIndex = gp.eHandler.nearDoor(gp.TILE_SIZE * 2);
	    
	    if (nearDoorIndex != -1 && gp.door[gp.stage.mapId][nearDoorIndex].showE == true && gp.door[gp.stage.mapId][nearDoorIndex].isDoor == true) {
	    	
	    	Entity door = gp.door[gp.stage.mapId][nearDoorIndex];

	        int x = door.worldX;
	        int y = door.worldY;
	        int screenX = x;
	        int screenY = y;
	        
	        if (gp.cameraMode == CameraMode.FOLLOW) {
	        	screenX = x - gp.player.worldX + gp.player.SCREEN_X;
		        screenY = y - gp.player.worldY + gp.player.SCREEN_Y;
	        }

	        BufferedImage btnImage = setup("/btn/e");
	        g2.drawImage(btnImage, screenX, screenY + 10, btnW, btnH, null);

	        g2.setFont(g2.getFont().deriveFont(12F));
	        String displayText = door.isVisible ? "เปิด" : "ปิด";
	        hilightText(displayText, screenX + btnW + 10, screenY + 23);
	        
	        if (gp.keyH.ePressed && gp.stage.coolDownOC == 0) {
                door.isVisible = !door.isVisible;
                
                gp.stage.coolDownOC = 20;
                
                if (nearDoorIndex == 5 && gp.eHandler.itemsAdded) {
                	door.showE = false;
                	
                	gp.obj[1][2] = new OBJ_Ouijaglass(gp);
    		        gp.obj[1][2].worldX = door.worldX;
    		        gp.obj[1][2].worldY = door.worldY;
                }
	        }
	    }
	    
	    // ปุ่มเก็บ
	    int nearObjIndex = gp.cChecker.checkNearObject(gp.player, 48);

	    if (nearObjIndex != 999 && gp.obj[gp.stage.mapId][nearObjIndex].showE == true && gp.obj[gp.stage.mapId][nearObjIndex].isDoor == false) {
	    	
	    	Entity obj = gp.obj[gp.stage.mapId][nearObjIndex];

	        int x = obj.worldX;
	        int y = obj.worldY;
	        int screenX = x;
	        int screenY = y;
	        
	        if (gp.cameraMode == CameraMode.FOLLOW) {
	        	screenX = x - gp.player.worldX + gp.player.SCREEN_X;
		        screenY = y - gp.player.worldY + gp.player.SCREEN_Y;
	        }
	        
	        if (obj.name == "Incense") {
	        	screenY += 42;
	        }

	        BufferedImage btnImage = setup("/btn/e");
	        g2.drawImage(btnImage, screenX, screenY - 32, btnW, btnH, null);

	        g2.setFont(g2.getFont().deriveFont(12F));
	        hilightText(obj.showTextBtn, screenX + btnW + 10, screenY - 32 + 13);

	    } 
	}
	
	public void drawOptionScreen() {
		
		int phoneX = gp.TILE_SIZE * 11;
		int phoneY = gp.TILE_SIZE * 3 + 10;
		int phoneW = gp.TILE_SIZE * 4;
		int phoneH = gp.TILE_SIZE * 6;
		
		BufferedImage phoneImage = setup("/phone/phone_black");
		g2.drawImage(phoneImage, phoneX, phoneY, phoneW, phoneH, null);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(18F));
		
		switch(subState) {
		case 0: options_top(phoneX, phoneY, phoneW, phoneH); break;
		case 1: options_fullScreeNotification(phoneX, phoneY); break;
		case 2: options_endGameConfirmation(phoneX, phoneY); break;
		}

		gp.keyH.enterPressed = false;
	}
	
	public void options_top(int phoneX, int phoneY, int phoneW, int phoneH) {
		
		int textX;
		int textY;
		
		// title
		String text = "การตั้งค่า";
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		textX = phoneX + (phoneW - length) / 2;
		textY = phoneY + 60;
		g2.drawString(text, textX, textY);
		g2.drawLine(textX, textY + 5, textX + length, textY + 5);
		
		textX = phoneX + 15;
		
		// full screen on/off
		text = "โหมดเต็มหน้าจอ";
		textY += 35;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			strokeText(text, textX, textY, "white", "red", 2);
			
			if (gp.keyH.enterPressed == true) {
				subState = 1;
				if (gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				} else if (gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
			}		
		}
		
		textY += 25;
		g2.drawString("เสียง", textX, textY);
		g2.drawLine(textX, textY + 5, phoneX + 170, textY + 5);
		
		// music
		text = "เพลง";
		textY += 25;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			strokeText(text, textX, textY, "white", "red", 2);
		}
		
		// se
		text = "เอฟเฟค";
		textY += 25;
		g2.drawString(text, textX, textY);
		if (commandNum == 2) {
			strokeText(text, textX, textY, "white", "red", 2);
		}
		
		g2.drawLine(textX, textY + 5, phoneX + 170, textY + 5);
		
		// end game
		text = "ออกเกม";
		textY += 35;
		g2.drawString(text, textX, textY);
		if (commandNum == 3) {
			strokeText(text, textX, textY, "white", "red", 2);
			if (gp.keyH.enterPressed == true) {
				commandNum = 0;
				subState = 2;
			}
		}
		
		// back
		text = "ย้อนกลับ";
		textY += 50;
		g2.drawString(text, textX, textY);
		if (commandNum == 4) {
			strokeText(text, textX, textY, "white", "red", 2);
			
			if (gp.keyH.enterPressed == true) {
				gp.ui.phoneCommandNum = 5;
				gp.ui.commandNum = 0;
				gp.gameState = gp.PAUSE_STATE;
			}
		}
		
		// full screen check box
		textX = phoneX + 150;
		textY = phoneY + 80;
		
		g2.drawRect(textX, textY, 15, 15);
		if (gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 15, 15);
		}
		
		textX = phoneX + 90;
		
		// music volume
		textY += 50;
		g2.drawRect(textX, textY, 80, 15);
		int volumeWidth = 16 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 15);
		
		// se 
		textY += 25;
		g2.drawRect(textX, textY, 80, 15);
		volumeWidth = 16 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 15);
	
		gp.config.saveConfig();
	}
	
	public void options_fullScreeNotification(int phoneX, int phoneY) {
		
		int textX = phoneX + 10;
		int textY = phoneY + 70;
		
		g2.setFont(g2.getFont().deriveFont(14F));
		currentDialogue = "การเปลี่ยนแปลงจะ \nเริ่มทำงานหลังจากรีสตาร์ทเกม";
		
		for (String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 20;
		}
		
		// back
		String text = "ย้อนกลับ";
		textX = phoneX + 15;
		textY += 100;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			strokeText(text, textX, textY, "white", "red", 2);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
	}
	
	
	public void options_endGameConfirmation(int phoneX, int phoneY) {
		
		int textX = phoneX + 10;
		int textY = phoneY + 70;
		
		g2.setFont(g2.getFont().deriveFont(14F));
		currentDialogue = "ออกจากเกม และ กลับหน้าหลัก \nหมายเหตุ: ข้อมูลจะถูกบันทึก \nจากด่านปัจจุบัน";
		
		for (String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 20;
		}
		
		// yes
		String text = "ตกลง";
		textX = phoneX + 15;
		textY += 80;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			strokeText(text, textX, textY, "white", "red", 2);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				titleScreenState = 0;
				gp.gameState = gp.TITLE_STATE;
				System.exit(0);
			}
		}
		
		// no
		text = "ยกเลิก";
		textY += 30;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			strokeText(text, textX, textY, "white", "red", 2);
			if (gp.keyH.enterPressed == true) {
				commandNum = 3;
				subState = 0;
			}
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}
	
	public int getXforCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.SCREEN_WIDTH / 2 - length / 2;
		return x;
	}
	
	public int getXforAlignToRightText(String text, int tailX) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
	
	public BufferedImage setup(String imagePath) {

		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
//			image = uTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void strokeText(String text, int textX, int textY, String colorText, String colorStroke, int bold) {

		FontRenderContext frc = g2.getFontRenderContext();
		TextLayout layout = new TextLayout(text, g2.getFont(), frc);
		Shape outline = layout.getOutline(AffineTransform.getTranslateInstance(textX, textY));
		
		g2.setStroke(new BasicStroke(bold));
		
		g2.setColor(parseColor(colorStroke));
		g2.draw(outline);

		g2.setColor(parseColor(colorText));
		g2.fill(outline);

		
	}
	
	private Color parseColor(String colorName) {
	    switch (colorName.toUpperCase()) {
	        case "BLACK": return Color.BLACK;
	        case "WHITE": return Color.WHITE;
	        case "RED": return Color.RED;
	        case "BLUE": return Color.BLUE;
	        case "GREEN": return Color.GREEN;
	        case "YELLOW": return Color.YELLOW;
	        case "ORANGE": return Color.ORANGE;
	        case "GRAY": return Color.GRAY;
	        case "PINK": return Color.PINK;
	        case "CYAN": return Color.CYAN;
	        case "MAGENTA": return Color.MAGENTA;
	        default:
	            try {
	                return Color.decode(colorName);
	            } catch (NumberFormatException e) {
	                return Color.BLACK;
	            }
	    }
	}
	
	public void hilightText(String text, int textX, int textY) {
		
	    FontMetrics fm = g2.getFontMetrics();
	    int textWidth = fm.stringWidth(text);
	    int textHeight = fm.getHeight();
	    int ascent = fm.getAscent();

	    int paddingX = 6;
	    int paddingY = 4;

	    Composite originalComposite = g2.getComposite();
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));

	    g2.setColor(Color.WHITE);
	    RoundRectangle2D roundedRect = new RoundRectangle2D.Float(
	        textX - paddingX,
	        textY - ascent - paddingY,
	        textWidth + paddingX * 2,
	        textHeight + paddingY * 2,
	        12, 12 
	    );
	    g2.fill(roundedRect);

	    g2.setComposite(originalComposite);

	    g2.setColor(Color.BLACK);
	    g2.drawString(text, textX, textY);
	}
}
	