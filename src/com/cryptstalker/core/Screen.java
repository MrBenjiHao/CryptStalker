package com.cryptstalker.core;

import java.awt.Color;

import com.cryptstalker.entities.Entity;
import com.cryptstalker.entities.SpriteSheet;

public class Screen {
	private String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789.,!?'\"-+=/\\%()<>: ";
	private int width, height;
	private int[] pixels;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void renderSprite(int xOffset, int yOffset, int w, int h, Entity e) {
		for (int x = 0; x < w; x++){
			int xx = x + xOffset;
			if(xx < 0 || xx >= width) continue;	
			for (int y = 0; y < h; y++){
				int yy = y + yOffset ;
				if(yy < 0 || yy >= height) continue;
				int pixel = e.pixels[x + y * e.width];
				if(pixel != 0xffff00ff) pixels[xx + yy * width] = pixel;
			}
		}
	}

	public void renderSprite(int xOffset, int yOffset, int w, int h, int x0, int y0) {
		for (int x = 0; x < w; x++){
			int xx = x + xOffset;
			if(xx < 0 || xx >= width) continue;
			for (int y = 0; y < h; y++){
				int yy = y + yOffset;
				if(yy < 0 || yy >= height) continue;
				int pixel = SpriteSheet.sheet.pixels[(x + (x0 * w)) + (y + (y0 * h)) * 256];
				if(pixel != 0xffff00ff) pixels[xx + yy * width] = pixel;
			}
		}
	}

	public void drawString(String msg, int x, int y, int color) {
		msg = msg.toUpperCase();
		for (int i = 0; i < msg.length(); i++){
			int z = chars.indexOf(msg.charAt(i));
			renderString(x + (i * 8), y, z, 8, 8, color, SpriteSheet.font);
		}
	}

	private void renderString(int xOffset, int yOffset, int z, int w, int h, int color, SpriteSheet sheet) {
		for (int x = 0; x < w; x++){
			int xx = x + xOffset;
			if(xx < 0 || xx >= width) continue;
			for (int y = 0; y < h; y++){
				int yy = y + yOffset;
				if(yy < 0 || yy >= height) continue;
				if(z < 26){
					int pixel = sheet.pixels[(x + (z * w)) + (y + (w * 0)) * sheet.width];
					if(pixel != 0xffff00ff && pixel == 0xffffffff) pixels[xx + yy * width] = color;
				}
				if(z >= 26){
					int pixel = sheet.pixels[(x + ((z - 26) * w)) + (y + (w * 1)) * sheet.width];
					if(pixel != 0xffff00ff && pixel == 0xffffffff) pixels[xx + yy * width] = color;
				}
			}
		}
	}

	public void renderLight(int x, int y, int r) {
		double factor = .25;
		for (int yy = 0; yy < height; yy++){
			int yd = yy - y;
			yd = yd * yd;
			for (int xx = 0; xx < width; xx++){
				int xd = xx - x;
				int dist = xd * xd + yd;
				int pixel = pixels[xx + yy * width];
				if(dist > r * r){
					Color c = Color.decode(Integer.toString(pixel));
					int red = (int) (c.getRed() * factor);
					int blue = (int) (c.getBlue() * factor);
					int green = (int) (c.getGreen() * factor);
					c = new Color(red, green, blue);
					pixels[xx + yy * width] = c.getRGB();
				}
			}
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++){
			pixels[i] = 0;
		}
	}

	public void overwriteData() {
		for (int i = 0; i < pixels.length; i++){
			Engine.pixels[i] = pixels[i];
		}
	}
}
