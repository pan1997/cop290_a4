package cop290.a4.pingpong;

import cop290.a4.*;
import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;

import java.awt.*;

/**
 * Created by pankaj on 13/4/16.
 */
public class block extends Spirit {
    int width, thickness;
    int l, b;
    Rectangle rect;
    Color color;

    public block(animPanel parent) {
        super(parent);
        color = Color.WHITE;
        rect=new Rectangle();
    }

    @Override
    public void updateSpirit(double dt) {
        rect.setRect(x,y,l,b);
    }

    @Override
    public void renderSpirit(Graphics2D g) {
        g.setColor(color);
        g.fill(rect);

        //g.fillRect((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
    }

}
