package cop290.a4.animation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by pankaj on 29/3/16.
 */

public class animPanel extends JPanel implements Runnable {
    protected int b, l;
    protected int u, f;
    private long nskip, delay;
    private int upsr;
    private double ups, fps;
    private Image screen;
    private Graphics2D gscreen;
    private long pt;
    protected Image background;
    Thread th;

    public animPanel(int l, int b, int ups, int skp) {
        super();
        this.l = l;
        this.b = b;
        screen = new BufferedImage(l, b, BufferedImage.TYPE_INT_ARGB);
        background=new BufferedImage(l,b,BufferedImage.TYPE_INT_ARGB);
        gscreen = (Graphics2D) screen.getGraphics();
        upsr = ups;
        nskip = skp;
        createBackground();
    }
    protected void createBackground(){
        Graphics g=background.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0,l,b);
    }
    protected void update() {
        if (u == 50) {
            long c = System.nanoTime();
            ups = u * 1000000000.0 / (c - pt + delay * 25);
            fps = (f * 1000000000.0) / (c - pt + delay * 25);
            u = f = 25;
            pt = c;
        }
    }
    public int getL(){
        return l;
    }
    public int getB(){
        return b;
    }
    protected void render(Graphics g) {
        g.drawImage(background,0,0,this);
        g.setColor(Color.BLACK);
        g.drawString("ups " + ups, 20, 30);
        g.drawString("fps " + fps, 20, 45);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (screen != null)
            g.drawImage(screen, 0, 0, null);
    }

    private void paintRender() {
        Graphics g = this.getGraphics();
        if (g != null && screen != null)
            g.drawImage(screen, 0, 0, null);
    }

    public void start() {
        pt = System.nanoTime();
        th = new Thread(this);
        running = true;
        th.start();
    }

    public void stop() {
        running = false;
    }

    boolean running;

    public void run() {
        long st = System.nanoTime();
        long td, slt;
        delay = 1000000000 / upsr;
        int sk = 0;
        boolean pin = true;
        u = 1;
        while (running) {
            u++;
            update();
            if (pin || sk == nskip) {
                f++;
                render(gscreen);
                paintRender();
                sk = 0;
            } else
                sk++;
            td = System.nanoTime() - st;
            slt = delay - td;
            if (slt > 0) {
                pin = true;
                try {
                    Thread.sleep(slt / 1000000);
                } catch (Exception e) {
                }
            } else pin = false;
            st = System.nanoTime();
        }
    }
}