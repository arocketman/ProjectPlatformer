package org.platformer.register;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.BufferedImageUtil;

public class RegisterTextures
{
	private static HashMap<String,Image> textureMap = new HashMap<String,Image>();
	public static TrueTypeFont font_0;
	public static TrueTypeFont font_1;
	
	public static void init()
	{
		setupFont();
		addTexture("missingtexture", "misc/missingtexture");
		addTexture("water", "misc/water");
		addTexture("sky", "misc/sky");
		
		addTexture("terrain", "blocks/terrain");
	}

	private static void setupFont()
	{
		Font font = new Font("Verdana", Font.BOLD, 12);
		font_0 = new TrueTypeFont(font, true);
		font = new Font("Lucida", Font.PLAIN, 16);
		font_1 = new TrueTypeFont(font, true);
	}

	private static void addTexture(String name, String texture)
	{
		try
		{
			String path = "resources/"+texture+".png";
			InputStream u = RegisterTextures.class.getClassLoader().getResourceAsStream(path);
			if (u != null)
			{
				Image img = new Image(BufferedImageUtil.getTexture(path, ImageIO.read(u)));
				img.setFilter(Image.FILTER_NEAREST);
				textureMap.put(name,img);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static Image getTexture(String name)
	{
		if(textureMap.containsKey(name))
		{
			return textureMap.get(name);
		}
		
		return textureMap.get("missingtexture");
	}
}
