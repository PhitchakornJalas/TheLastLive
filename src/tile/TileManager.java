package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.GamePanel.CameraMode;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	ArrayList<String> fileNames = new ArrayList<>();
	ArrayList<String> collisionStatus = new ArrayList<>();
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void getTileImage(String folderName) {
		
		for (int i = 0; i < fileNames.size(); i++) {
			
			String fileName;
			boolean collision;
			
			// get a file name
			fileName = fileNames.get(i);
			
			// get a collision status
			if (collisionStatus.get(i).equals("true")) {
				collision = true;
			} else {
				collision = false;
			}
			
			setup(i, folderName, fileName, collision);
			
		}
	}
	
	public void setup(int index, String folderName, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/" + folderName + "/" + imageName));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.TILE_SIZE, gp.TILE_SIZE);
			tile[index].collision = collision;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void setup(int index, String folderName, String imageName, boolean collision) {
//	    UtilityTool uTool = new UtilityTool();
//
//	    try {
//	        String path = "/" + folderName + "/" + imageName;
//	        System.out.println("กำลังโหลด tile: " + path);
//	        
//	        InputStream is = getClass().getResourceAsStream(path);
//	        if (is == null) {
//	            System.err.println("❌ ไม่พบไฟล์ tile: " + path);
//	            return; // อย่าพังเกม ให้ข้ามไป
//	        }
//
//	        tile[index] = new Tile();
//	        tile[index].image = ImageIO.read(is);
//	        tile[index].image = uTool.scaleImage(tile[index].image, gp.TILE_SIZE, gp.TILE_SIZE);
//	        tile[index].collision = collision;
//
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}

	
	public void loadMap(String folderName, String fileName) {
		
		 fileNames.clear();
		 collisionStatus.clear();

	    try {
	        // โหลด tile data
	        InputStream is = getClass().getResourceAsStream("/maps/tile_" + fileName + ".txt");
	        if (is == null) {
	            throw new FileNotFoundException("ไม่พบไฟล์ tile: /maps/tile_" + fileName + ".txt");
	        }
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        String line;
	        while ((line = br.readLine()) != null) {
	            fileNames.add(line);
	            collisionStatus.add(br.readLine());
	        }
	        br.close();
	
	        tile = new Tile[fileNames.size()];
	        getTileImage(folderName);  // โหลดรูปตามชื่อและ collision
	
	        // โหลดแผนที่ (map layout)
	        is = getClass().getResourceAsStream("/maps/map_" + fileName + ".txt");
	        br = new BufferedReader(new InputStreamReader(is));
	        List<String> lines = new ArrayList<>();
	
	        while ((line = br.readLine()) != null) {
	            lines.add(line);
	        }
	        br.close();
	
	        gp.MAX_WORLD_ROW = lines.size();
	        gp.MAX_WORLD_COL = lines.get(0).split(" ").length;
	        mapTileNum = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
	
	        for (int row = 0; row < gp.MAX_WORLD_ROW; row++) {
	            String[] numbers = lines.get(row).split(" ");
	            for (int col = 0; col < gp.MAX_WORLD_COL; col++) {
	                int num = Integer.parseInt(numbers[col]);
	                mapTileNum[col][row] = num;
	            }
	        }
	
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void draw(Graphics2D g2) {
	    int worldCol = 0;
	    int worldRow = 0;

	    while (worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW) {

	        int tileNum = mapTileNum[worldCol][worldRow];

	        int worldX = worldCol * gp.TILE_SIZE;
	        int worldY = worldRow * gp.TILE_SIZE;

	        int screenX;
	        int screenY;

	        if (gp.cameraMode == CameraMode.FOLLOW) {
	        	
	            screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
	            screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

	            if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X &&
	                worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X &&
	                worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y &&
	                worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
	                
	                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
	            }
	        } else {
	            screenX = worldX;
	            screenY = worldY;
	            g2.drawImage(tile[tileNum].image, screenX, screenY, null);
	        }

	        worldCol++;

	        if (worldCol == gp.MAX_WORLD_COL) {
	            worldCol = 0;
	            worldRow++;
	        }
	    }
	}

}
