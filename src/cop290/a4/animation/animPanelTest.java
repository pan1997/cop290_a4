package cop290.a4.animation;

import java.awt.*;

/**
 * Created by pankaj on 29/3/16.
 */
public class animPanelTest extends animPanel {
    double theta;
    int x,y;
    public animPanelTest(int l, int b, int ups, int skp) {
        super(l, b, ups, skp);
        theta=0;
    }
    protected void update(){
        theta+=0.1;
        x=200+(int)(Math.cos(theta)*100);
        y=200+(int)(Math.sin(theta)*100);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.fillOval(x,y,50,50);
    }
}
