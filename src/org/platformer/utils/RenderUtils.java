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

	public static void renderBlock(boolean[] blocksAt, float[] pos, float[] uv, Block block)
	{
		boolean flag = false;
		
		if(!flag && (blocksAt[0] && blocksAt[1] && blocksAt[2] && blocksAt[3]))flag = true;
		if(!flag && (blocksAt[0] && blocksAt[1] && blocksAt[2] && !blocksAt[3]))flag = true;
		if(!flag && (blocksAt[0] && blocksAt[1] && !blocksAt[2] && blocksAt[3]))flag = true;
		if(!flag && (blocksAt[0] && !blocksAt[1] && blocksAt[2] && blocksAt[3]))flag = true;
		if(!flag && (!blocksAt[0] && blocksAt[1] && blocksAt[2] && blocksAt[3]))flag = true;
		if(!flag && (blocksAt[0] && blocksAt[1] && !blocksAt[2] && !blocksAt[3]))flag = true;
		if(!flag && (!blocksAt[0] && !blocksAt[1] && blocksAt[2] && blocksAt[3]))flag = true;
		if(!flag && (!blocksAt[0] && !blocksAt[1] && !blocksAt[2] && blocksAt[3]))flag = true;
		if(!flag && (!blocksAt[0] && !blocksAt[1] && blocksAt[2] && !blocksAt[3]))flag = true;
		if(!flag && (!blocksAt[0] && blocksAt[1] && !blocksAt[2] && !blocksAt[3]))flag = true;
		if(!flag && (blocksAt[0] && !blocksAt[1] && !blocksAt[2] && !blocksAt[3]))flag = true;
		if(!flag && (!blocksAt[0] && !blocksAt[1] && !blocksAt[2] && !blocksAt[3]))flag = true;
		
		if(!block.canSlope() || flag)
		{
			drawTexturedQuad(pos, uv);
		}
		
		if(blocksAt[0] && blocksAt[2] && !blocksAt[1] && !blocksAt[3]) //up and left
		{
			float[] pos2 = new float[]{pos[0],pos[1],pos[2],pos[1],pos[0],pos[3]};
			float[] uv2 = new float[]{uv[0],uv[1],uv[2],uv[1],uv[0],uv[3]};
			drawTexturedTriangle(pos2, uv2);
		}
		
		if(blocksAt[0] && blocksAt[3] && !blocksAt[1] && !blocksAt[2]) //up and right
		{
			float[] pos2 = new float[]{pos[0],pos[1],pos[2],pos[1],pos[2],pos[3]};
			float[] uv2 = new float[]{uv[0],uv[1],uv[2],uv[1],uv[2],uv[3]};
			drawTexturedTriangle(pos2, uv2);
		}
		
		if(blocksAt[1] && blocksAt[2] && !blocksAt[0] && !blocksAt[3]) //down and left
		{
			float[] pos2 = new float[]{pos[0],pos[1],pos[2],pos[3],pos[0],pos[3]};
			float[] uv2 = new float[]{uv[0],uv[1],uv[2],uv[3],uv[0],uv[3]};
			drawTexturedTriangle(pos2, uv2);
		}
		
		if(blocksAt[1] && blocksAt[3] && !blocksAt[0] && !blocksAt[2]) //down and right
		{
			float[] pos2 = new float[]{pos[2],pos[1],pos[2],pos[3],pos[0],pos[3]};
			float[] uv2 = new float[]{uv[0],uv[1],uv[2],uv[3],uv[0],uv[3]};
			drawTexturedTriangle(pos2, uv2);
		}
	}
}
