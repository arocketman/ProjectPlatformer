package org.platformer.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Localization
{
	public static ArrayList<Locale> localizationTypes = new ArrayList<Locale>();
	public static HashMap<String,String> localization = new HashMap<String,String>();
	public static String currentLanguage = "en_US";
	
	public static void init()
	{
		Locale locales[] = SimpleDateFormat.getAvailableLocales();
		for (int i = 0; i < locales.length; i++)
        {
        	InputStream instream = Localization.class.getClassLoader().getResourceAsStream("resources/lang/"+locales[i]+".lang");
    		if(instream != null)
    		{
    			localizationTypes.add(locales[i]);
    		}
		}
		
		loadLanguage("en_US");
	}

	public static void loadLanguage(String lang)
	{
		currentLanguage = lang;
		InputStream instream = Localization.class.getClassLoader().getResourceAsStream("resources/lang/"+lang+".lang");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(instream));
		String line = null;

		ArrayList<String> lines = new ArrayList<String>();
		try
		{
			while((line = in.readLine()) != null)
			{
				lines.add(line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		loadToHashmap(lines);
	}

	private static void loadToHashmap(ArrayList<String> lines)
	{
		for(String line : lines)
		{
			if(line.contains("="))
			{
				String[] split = line.split("\\=");
				if(split.length>=2)
				{
					String loc = split[0];
					String text = split[1];
					localization.put(loc, text);
				}
			}
		}
	}
	
	public static String get(String loc)
	{
		String res = localization.get(loc);
		if(res == null || res.isEmpty())
		{
			return loc;
		}
		return res;
	}
}
