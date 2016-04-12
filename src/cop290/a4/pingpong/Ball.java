package cop290.a4.pingpong;

import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;

import java.awt.*;

/**
 * Created by pankaj on 12/4/16.
 */
public class Ball extends Spirit {
    double x,y,vx,vy,theta,w;
    double r;
    Ball(animPanel parent) {
        super(parent);
        x=25+Math.random()*450;
        y=25+Math.random()*450;
        vx=(Math.random()-0.5)*10;
        vy=(Math.random()-0.5)*10;
        theta=Math.random();
        w=0.2*(Math.random()-0.5);
        r=10;
    }

    @Override
    public void updateSpirit() {
        x+=vx;
        y+=vy;
        theta+=w;
    }
    animPanel parent(){
        return parent;
    }
    @Override
    public void renderSpirit(Graphics g) {
        g.setColor(Color.red);
        g.fillArc((int)(x-r),(int)(y-r),(int)(2*r),(int)(2*r),(int)(theta*180/Math.PI),180);
        g.setColor(Color.white);
        g.fillArc((int)(x-r),(int)(y-r),(int)(2*r),(int)(2*r),180+(int)(theta*180/Math.PI),180);
    }
}
