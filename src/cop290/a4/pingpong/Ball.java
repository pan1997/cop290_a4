package cop290.a4.pingpong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;

/**
 * Created by pankaj on 12/4/16.
 *
 */
public class Ball extends Spirit {
    double vx, vy, theta, w;
    double r;
    Ellipse2D e2d;


    int lastid = -1;

    Ball(animPanel parent) {
        super(parent);

        //System.out.println(getID()+" bat");
        x = Math.random() * 150 + ((Math.round(Math.random()) == 1) ? 100 : 350); //assign initial values to spawn of ball
        y = Math.random() * 150 + ((Math.round(Math.random()) == 1) ? 100 : 350);
        double vr=0;
        do {
            vx = (Math.random() - 0.5) * 500.0;
            vy = (Math.random() - 0.5) * 500.0;
            vr=vx*vx+vy*vy;
        }while (vr<300*300.0); //assign initial velocity to ball till a min speed is assinged at least
        theta = Math.random();
        double wr = 0;
        do{
            w = 15 * (Math.random() - 0.5);
            wr = Math.abs(w);
        }while (wr < 5); //assign initial spin to ball

        r = 10;
        e2d = new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
    }

    public double getVx(){
        return vx;
    } //return velocities of ball

    public double getVy(){
        return vy;
    }

    @Override
    public void updateSpirit(double dt) {
        e2d.setFrame(x - r, y - r, 2 * r, 2 * r);
    }

    animPanel parent() {
        return parent;
    }

    @Override
    public void renderSpirit(Graphics2D g) { //render spirit at new location of ball at frame rate
        g.setColor(Color.red);
        g.fillArc((int) e2d.getX(), (int) e2d.getY(), (int) e2d.getWidth(), (int) e2d.getHeight(), (int) (theta * 180 / Math.PI), 180);
        g.setColor(Color.yellow);
        g.fillArc((int) e2d.getX(), (int) e2d.getY(), (int) e2d.getWidth(), (int) e2d.getHeight(), 180 + (int) (theta * 180 / Math.PI), 180);
    }
}
