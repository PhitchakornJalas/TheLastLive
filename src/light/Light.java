package light;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.GamePanel;
import object.OBJ_Diarybook;

public class Light {

	GamePanel gp;
	
	public String name;
	public int worldX, worldY, size;
	
	public Light(GamePanel gp) {
		this.gp = gp;
	}
	
	public void drawCandle(Graphics2D gFilter, Light light) {
		
		int screenX = light.worldX - gp.player.worldX + gp.player.SCREEN_X;
		int screenY = light.worldY - gp.player.worldY + gp.player.SCREEN_Y;
		int radius = light.size;
		int centerX = screenX + 23;
		int centerY = screenY + 10;
		
		Random rand = new Random();
		float flicker = 0.9f + rand.nextFloat() * 0.1f;
		int flickerRadius = (int)(radius * flicker);
		float alphaOffset = rand.nextFloat() * 0.05f;

		Color[] color = new Color[12];
		float[] fraction = new float[12];

//		float[] alphas = {0.1f, 0.42f, 0.52f, 0.61f, 0.69f, 0.76f, 0.82f, 0.87f, 0.91f, 0.94f, 0.96f, 0.98f};
//		for (int i = 0; i < color.length; i++) {
//		    float adjustedAlpha = Math.min(1f, Math.max(0f, alphas[i] + alphaOffset));
//		    color[i] = new Color(0, 0, 0, adjustedAlpha);
//		}
		float[] alphas = {1.0f, 0.9f, 0.8f, 0.7f, 0.6f, 0.5f, 0.4f, 0.3f, 0.2f, 0.1f, 0.05f, 0f};
		for (int i = 0; i < color.length; i++) {
		    float adjustedAlpha = Math.min(1f, Math.max(0f, alphas[i] + alphaOffset));
		    color[i] = new Color(0, 0, 0, adjustedAlpha);
		}

		fraction[0] = 0f;
		fraction[1] = 0.4f;
		fraction[2] = 0.5f;
		fraction[3] = 0.6f;
		fraction[4] = 0.65f;
		fraction[5] = 0.7f;
		fraction[6] = 0.75f;
		fraction[7] = 0.8f;
		fraction[8] = 0.85f;
		fraction[9] = 0.9f;
		fraction[10] = 0.95f;
		fraction[11] = 1f;

		RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, flickerRadius / 2f, fraction, color);
		gFilter.setComposite(AlphaComposite.DstOut);
		gFilter.setPaint(gPaint);
		gFilter.fillOval(centerX - flickerRadius / 2, centerY - flickerRadius / 2, flickerRadius, flickerRadius);
	}
	
	public void draw(Graphics2D g2) {
		
		gp.lightSources.clear();
		
		// เพิ่มแสงจากผู้เล่น
		if (gp.player.useLantern) {
			Light playerLight = new Light(gp);
			playerLight.worldX = gp.player.worldX;
			playerLight.worldY = gp.player.worldY;
			playerLight.size = 150;
			gp.lightSources.add(playerLight);
		}
	
		
		if (gp.lights[gp.stage.mapId][0] != null || gp.lightSources.size() > 0) {
			
			for (Light light : gp.lights[gp.stage.mapId]) {
		        if (light != null) gp.lightSources.add(light);
		    }
			
			BufferedImage darknessFilter = new BufferedImage(gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D gFilter = (Graphics2D) darknessFilter.getGraphics();

		    gFilter.setColor(new Color(0, 0, 0, 225));
		    gFilter.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

		    
	    	for (Light light : gp.lightSources) {
	    		
	    		if (light.name == "candle") {
	    			drawCandle(gFilter, light);
	    		} else {
	    			int screenX = light.worldX - gp.player.worldX + gp.player.SCREEN_X;
		    	    int screenY = light.worldY - gp.player.worldY + gp.player.SCREEN_Y;
		    	    int radius = light.size;
		    	    int centerX = screenX + 23;
		    	    int centerY = screenY + 10;

		    	    Color[] color = new Color[12];
		    	    float[] fraction = new float[12];
		    
		    	    color[0] = new Color(0, 0, 0, 1.0f);
		    	    color[1] = new Color(0, 0, 0, 0.94f);
		    	    color[2] = new Color(0, 0, 0, 0.91f);
		    	    color[3] = new Color(0, 0, 0, 0.87f);
		    	    color[4] = new Color(0, 0, 0, 0.82f);
		    	    color[5] = new Color(0, 0, 0, 0.76f);
		    	    color[6] = new Color(0, 0, 0, 0.69f);
		    	    color[7] = new Color(0, 0, 0, 0.61f);
		    	    color[8] = new Color(0, 0, 0, 0.52f);
		    	    color[9] = new Color(0, 0, 0, 0.42f);
		    	    color[10] = new Color(0, 0, 0, 0.2f);
		    	    color[11] = new Color(0, 0, 0, 0f); 
		    
		    	    fraction[0] = 0f;
		    	    fraction[1] = 0.4f;
		    	    fraction[2] = 0.5f;
		    	    fraction[3] = 0.6f;
		    	    fraction[4] = 0.65f;
		    	    fraction[5] = 0.7f;
		    	    fraction[6] = 0.75f;
		    	    fraction[7] = 0.8f;
		    	    fraction[8] = 0.85f;
		    	    fraction[9] = 0.9f;
		    	    fraction[10] = 0.95f;
		    	    fraction[11] = 1f;

		    	    RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, radius / 2f, fraction, color);
		    	    gFilter.setComposite(AlphaComposite.DstOut);
		    	    gFilter.setPaint(gPaint);
		    	    gFilter.fillOval(centerX - radius / 2, centerY - radius / 2, radius, radius);
	    		}

	    	}

		    gFilter.dispose();
		    g2.drawImage(darknessFilter, 0, 0, null);
		}
 
	}

}
