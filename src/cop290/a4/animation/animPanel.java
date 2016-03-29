package cop290.a4.animation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by pankaj on 29/3/16.
 */

public class animPanel extends JPanel {
    protected int b, l;
    protected long u, f;
    private long nskip;
    private int upsr;
    private java.util.Timer t;
    private TimerTask k;
    private Image screen;
    private Graphics2D gscreen;
    private long start;
    public animPanel(int l, int b, int ups, int skp) {
        super();
        this.l = l;
        this.b = b;
        screen=new BufferedImage(l,b,BufferedImage.TYPE_INT_ARGB);
        gscreen=(Graphics2D)screen.getGraphics();
        upsr = ups;
        nskip = skp;
        t = new Timer();
        k = new TimerTask() {
            @Override
            public void run() {
                u++;
                update();
                if (u % nskip == 0) {
                    f++;
                    render(gscreen);
                    paintRender();
                }
            }
        };
    }

    protected void update() {
    }
    protected void render(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, b, l);
        g.setColor(Color.BLACK);
        long c = System.currentTimeMillis() - start;
        g.drawString("ups " + (u * 1000 / c), 10, 20);
        g.drawString("fps " + (f * 1000 / c), 10, 40);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (screen != null)
            g.drawImage(screen, 0, 0, null);
    }
    private void paintRender(){
        Graphics g=this.getGraphics();
        if(g!=null&&screen!=null)
            g.drawImage(screen,0,0,null);
    }
    public void start() {
        long del = 1000 / upsr;
        start = System.currentTimeMillis();
        t.scheduleAtFixedRate(k, del, del);
    }
}