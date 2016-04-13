package cop290.a4.pingpong;

import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by pankaj on 12/4/16.
 */
public class Ball extends Spirit {
    double x,y,vx,vy,theta,w;
    double r;
    Ellipse2D e2d;
    Ball(animPanel parent) {
        super(parent);
        x=25+Math.random()*450;
        y=25+Math.random()*450;
        vx=(Math.random()-0.5)*7;
        vy=(Math.random()-0.5)*7;
        theta=Math.random();
        w=0.2*(Math.random()-0.5);
        r=10;
        e2d=new Ellipse2D.Double(x-r,y-r,2*r,2*r);
    }

    @Override
    public void updateSpirit() {
        x+=vx;
        y+=vy;
        theta+=w;
        e2d.setFrame(x-r,y-r,2*r,2*r);
    }
    animPanel parent(){
        return parent;
    }
    @Override
    public void renderSpirit(Graphics2D g) {
        g.setColor(Color.red);
        g.fillArc((int)e2d.getX(),(int)e2d.getY(),(int)e2d.getWidth(),(int)e2d.getHeight(),(int)(theta*180/Math.PI),180);
        g.setColor(Color.white);
        g.fillArc((int)e2d.getX(),(int)e2d.getY(),(int)e2d.getWidth(),(int)e2d.getHeight(),180+(int)(theta*180/Math.PI),180);
    }
}
