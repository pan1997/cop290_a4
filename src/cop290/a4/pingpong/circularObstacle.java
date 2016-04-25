package cop290.a4.pingpong;

import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;

import java.awt.*;

/**
 * Created by pankaj on 24/4/16.
 */
public class circularObstacle extends Spirit {
    double r;
    Color fill;
    Color boundry;
    Color center;
    public circularObstacle(animPanel parent,Color c1,Color c2,Color c3) {
        super(parent);
        System.out.println(getID()+" circular obstacle");
        fill = c1;
        boundry = c2;
        center=c3;
    }

    @Override
    public void updateSpirit(double dt) {

    }

    @Override
    public void renderSpirit(Graphics2D g) {
        if (fill != null) {
            g.setColor(fill);
            g.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
        }
        if (boundry != null) {
            g.setColor(boundry);
            g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
        }
        if(center!=null){
            g.setColor(center);
            g.fillOval((int)(x-2),(int)(y-2),4,4);
        }
    }
}
