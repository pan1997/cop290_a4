package cop290.a4.animation;

import java.awt.*;

/**
 * Created by pankaj on 30/3/16.
 */

public abstract class Spirit {
    protected animPanel parent;

    public Spirit(animPanel parent) {
        this.parent = parent;
    }

    public abstract void updateSpirit();

    public abstract void renderSpirit(Graphics2D g);
}

class ballSpirit extends Spirit {
    double x, y, vx, vy;
    double r;

    public ballSpirit(animPanel a) {
        super(a);
        double theta = Math.random() * Math.PI * 2;
        vx = (Math.cos(theta) * 5);
        vy = (Math.sin(theta) * 5);
        r = 10;
        x = y = 250;
    }

    @Override
    public void updateSpirit() {
        x += vx;
        y += vy;
        //if(x+r>=parent.b||x<=r)
        //    vx=-vx;
        //if(y+r>=parent.l||y<=r)
        //    vy=-vy;
    }

    @Override
    public void renderSpirit(Graphics2D g) {
        g.setColor(Color.red);
        g.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
    }
}

class batSpirit extends Spirit {
    int orinetation;
    double px, py;

    batSpirit(animPanel parent, int o) {
        super(parent);
        orinetation = o;
        switch (orinetation) {
            case 0:
                py = parent.l - 10;
                px = 0;
                break;
            case 1:
                px = parent.b - 10;
                py = 0;
                break;
            case 2:
                py = 0;
                px = 0;
                break;
            case 3:
                px = 0;
                py = 0;
                break;
        }
    }

    @Override
    public void updateSpirit() {
    }

    public void updateLoc(double x, double y) {
        switch (orinetation) {
            case 0:
                px = x - 25;
                break;
            case 1:
                py = y - 25;
                break;
            case 2:
                px = x - 25;
                break;
            case 3:
                py = y - 25;
                break;
        }
    }

    @Override
    public void renderSpirit(Graphics2D g) {
        g.setColor(Color.blue);
        if (orinetation % 2 == 0)
            g.fillRect((int) px, (int) py, 50, 10);
        else
            g.fillRect((int) px, (int) py, 10, 50);
    }
}
