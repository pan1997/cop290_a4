package cop290.a4.pingpong;

import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;

import java.awt.*;

/**
 * Created by pankaj on 13/4/16.
 */
public class block extends Spirit {
    int width, thickness;
    int x, y, l, b;
    Color color;

    public block(animPanel parent) {
        super(parent);
        color = Color.black;
    }

    @Override
    public void updateSpirit() {

    }

    @Override
    public void renderSpirit(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, l, b);
    }
}
