package cop290.a4.animation;

import java.awt.*;

/**
 * Created by pankaj on 29/3/16.
 */
public class animPanelTest extends animPanel {
    double x, y;
    double vx, vy;
    int dia;

    public animPanelTest(int l, int b, int ups, int skp) {
        super(l, b, ups, skp);
        double theta = Math.random() * Math.PI * 2;
        vx = (Math.cos(theta) * 2);
        vy = (Math.sin(theta) * 2);
        dia = 20;
        x = y = 250;
    }

    protected void update() {
        super.update();
        x += vx;
        y += vy;
        if (x + dia / 2 >= b || x <= dia / 2)
            vx = -vx;
        if (y + dia / 2 >= l || y <= dia / 2)
            vy = -vy;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        try {
            Thread.sleep(10);
        }catch (Exception e){}
        g.fillOval((int) x - dia / 2, (int) y - dia / 2, dia, dia);
    }
}