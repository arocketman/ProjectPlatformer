package org.platformer.utils;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.GL11;
import org.platformer.block.Block;

public class RenderUtils
{
	/**
	 * Draw a quad with given position and uv values
	 * @param pos - Position Array[4]{x,y,x1,y1}
	 * @param uv - Texture UV Array[4]{x,y,x1,y1}
	 */
	public static void drawTexturedQuad(float[] pos, float[] uv)
	{
		glBegin(GL_QUADS);
		glTexCoord2f(uv[0], uv[1]);
		glVertex2f(pos[0], pos[1]); //0f,0f - top left
		
		glTexCoord2f(uv[2], uv[1]);
		glVertex2f(pos[2], pos[1]); //1f, 0f - top right
		
		glTexCoord2f(uv[2], uv[3]);
		glVertex2f(pos[2], pos[3]); //1f, 1f - bottom right
		
		glTexCoord2f(uv[0], uv[3]);
		glVertex2f(pos[0], pos[3]); //0f, 1f - bottom left
		glEnd();
	}

	@SuppressWarnings("unused")
	private static void drawTexturedTriangle(float[] pos, float[] uv)
	{
		glBegin(GL11.GL_TRIANGLES);
		glTexCoord2f(uv[0], uv[1]);
		glVertex2f(pos[0], pos[1]);
		
		glTexCoord2f(uv[2], uv[3]);
		glVertex2f(pos[2], pos[3]);
		
		glTexCoord2f(uv[4], uv[5]);
		glVertex2f(pos[4], pos[5]);
		glEnd();
	}

	public static void renderBlock(float[] pos, float[] uv, Block block)
	{
		drawTexturedQuad(pos, uv);
	}
}
