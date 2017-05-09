package org.platformer.render;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.platformer.entity.Entity;
import org.platformer.entity.EntityItem;
import org.platformer.register.RegisterTextures;

public class RenderItem extends RenderEntity
{

    @Override
    public void render(Entity ent, Graphics g)
    {

        if(ent instanceof EntityItem)
        {

            EntityItem entItem = (EntityItem)ent;
            Image tex = RegisterTextures.getTexture(entItem.getTexture());
            tex.draw(ent.posX-(ent.colBox.getWidth()/2), ent.posY-(ent.colBox.getHeight()/2),ent.colBox.getWidth(),ent.colBox.getHeight());

        }

    }

}
