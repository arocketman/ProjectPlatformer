package org.platformer.register;

import java.util.HashMap;

import org.newdawn.slick.Input;

public class RegisterKeybinds
{
	public static HashMap<String,Keybind> keyMap = new HashMap<String,Keybind>();
	
	public static Keybind move_up = new Keybind("move_up",Input.KEY_W);
	public static Keybind move_down = new Keybind("move_down",Input.KEY_S);
	public static Keybind move_left = new Keybind("move_left",Input.KEY_A);
	public static Keybind move_right = new Keybind("move_right",Input.KEY_D);
	public static Keybind rotate_left = new Keybind("rotate_left",Input.KEY_Q);
	public static Keybind rotate_right = new Keybind("rotate_right",Input.KEY_E);
	
	public static Keybind escape = new Keybind("escape",Input.KEY_ESCAPE);
	
	public static Keybind space = new Keybind("key_space",Input.KEY_SPACE);
	public static Keybind backspace = new Keybind("key_backspace",Input.KEY_BACK);
	public static Keybind lshift = new Keybind("key_lshift",Input.KEY_LSHIFT);
	public static Keybind rshift = new Keybind("key_rshift",Input.KEY_RSHIFT);
	
	public static HashMap<String,Keybind> letters = new HashMap<String,Keybind> ();
	
	public static void init()
	{
		String[] alphabet = ("a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9").split("\\,");
		int[] allkeys = new int[]
		{Input.KEY_A,Input.KEY_B,Input.KEY_C,Input.KEY_D,Input.KEY_E,Input.KEY_F,Input.KEY_G,Input.KEY_H,Input.KEY_I
		,Input.KEY_J,Input.KEY_K,Input.KEY_L,Input.KEY_M,Input.KEY_N,Input.KEY_O,Input.KEY_P,Input.KEY_Q,Input.KEY_R
		,Input.KEY_S,Input.KEY_T,Input.KEY_U,Input.KEY_V,Input.KEY_W,Input.KEY_X,Input.KEY_Y,Input.KEY_Z,Input.KEY_0
		,Input.KEY_1,Input.KEY_2,Input.KEY_3,Input.KEY_4,Input.KEY_5,Input.KEY_6,Input.KEY_7,Input.KEY_8,Input.KEY_9};
		
		int i=0;
		for(String letter : alphabet)
		{
			Keybind lk = new Keybind("key_"+letter,allkeys[i]);
			letters.put(letter.toLowerCase(), lk);
			i++;
		}
	}
	
	public static Keybind letter(String letter)
	{
		return letters.get(letter);
	}
}
