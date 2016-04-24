package cop290.a4.pingpong;

import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by pankaj on 12/4/16.
 */
public class Ball extends Spirit {
    double vx, vy, theta, w;
    double r;
    Ellipse2D e2d;


    int lastid = -1;

    Ball(animPanel parent) {
        super(parent);
        x = 25 + Math.random() * 450;
        y = 25 + Math.random() * 450;

        vx = (Math.random() - 0.5) * 500.0;
        vy = (Math.random() - 0.5) * 500.0;
        theta = Math.random();
        w = 15 * (Math.random() - 0.5);
        r = 10;
        e2d = new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
    }

    @Override
    public void updateSpirit(double dt) {
        e2d.setFrame(x - r, y - r, 2 * r, 2 * r);
    }

    animPanel parent() {
        return parent;
    }

    @Override
    public void renderSpirit(Graphics2D g) {
        g.setColor(Color.red);
        g.fillArc((int) e2d.getX(), (int) e2d.getY(), (int) e2d.getWidth(), (int) e2d.getHeight(), (int) (theta * 180 / Math.PI), 180);
        g.setColor(Color.yellow);
        g.fillArc((int) e2d.getX(), (int) e2d.getY(), (int) e2d.getWidth(), (int) e2d.getHeight(), 180 + (int) (theta * 180 / Math.PI), 180);
    }
}
