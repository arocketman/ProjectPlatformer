package org.platformer.register;

import org.lwjgl.input.Keyboard;

public class Keybind
{
	private boolean isPressed = false;
	public String name;
	public int keycode = 0;
	
	public Keybind(String name, int keycode)
	{
		this.name = name;
		this.keycode = keycode;
		RegisterKeybinds.keyMap.put(name, this);
	}
	
	public boolean isPressedOnce()
	{
		if(Keyboard.isKeyDown(keycode) && !isPressed)
		{
			isPressed = true;
			return true;
		}
		
		if(!Keyboard.isKeyDown(keycode) && isPressed)
		{
			isPressed = false;
		}
		return false;
	}
	
	public boolean isPressed()
	{
		return (Keyboard.isKeyDown(keycode));
	}
}
