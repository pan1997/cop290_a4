package cop290.a4.pingpong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;

/**
 * Created by pankaj on 13/4/16.
 */
public class block extends Spirit {
    int width, thickness;
    int l, b;
    Rectangle rect;
    Color color; //block class for all rectangular objects

    public block(animPanel parent) {
        super(parent);
        color = Color.WHITE;
        rect=new Rectangle();
    }

    @Override
    public void updateSpirit(double dt) {
        rect.setRect(x,y,l,b);
    } //sets the rectangle block at the new place

    @Override
    public void renderSpirit(Graphics2D g) {
        g.setColor(color);
        g.fill(rect);
        //paints the rect at every frame change

        //g.fillRect((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
    }

}
