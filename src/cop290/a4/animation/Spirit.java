package cop290.a4.animation;

import java.awt.*;

/**
 * Created by pankaj on 30/3/16.
 * This is the main spirit class. it is the base for all balls boards and others.
 */

public abstract class Spirit {
    //total number of spirits
    private static int ns;
    //x and y coord of the spirit
    public double x, y;
    //spirit id
    int id;
    //parent component
    protected animPanel parent;
    //return id
    public int getID() {
        return id;
    }
    //constructor
    public Spirit(animPanel parent) {
        this.parent = parent;
        id = ns++;
    }
    /*
        call to pudate spirit
     */
    public abstract void updateSpirit(double dt);
    /*
    call to render spirit
     */
    public abstract void renderSpirit(Graphics2D g);

    @Override
    public String toString() {
        return getID() + " " + x + " " + y;
    }
}