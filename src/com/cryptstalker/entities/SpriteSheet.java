package com.cryptstalker.entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
	public int[] pixels;
	public int width, height;

	public static SpriteSheet sheet = new SpriteSheet("sheet.png");
	public static SpriteSheet font = new SpriteSheet("font.png");

	public SpriteSheet(String filePath) {
		try{
			BufferedImage image = ImageIO.read(SpriteSheet.class.getClassLoader().getResource("res/" + filePath));
            width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			pixels = image.getRGB(0, 0, width, height, null, 0, width);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
