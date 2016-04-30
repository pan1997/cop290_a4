package cop290.a4.animation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by pankaj on 29/3/16.
 * It is the base class containing animation framework
 * It has double buffering and supports variable fps targets maintaing constant ups.
 */

public class animPanel extends JPanel implements Runnable {
    // length and breadth of the diosplay
    protected int b, l;
    // count updates and frames rendered
    protected int u, f;
    //initital infromation containing fps vs ups targets
    private long nskip, delay;
    //requested updates per second
    private int upsr;
    //current ups and fps
    private double ups, fps;
    //The double buffer
    private Image screen;
    // the graphics of the image
    private Graphics2D gscreen;
    private long pt;
    //background
    protected Image background;
    //runner thread
    Thread th;
    //rotation
    public int rot=0;
    //constructor
    public animPanel(int l, int b, int ups, int skp) {
        super();
        this.l = l;
        this.b = b;
        screen = new BufferedImage(l, b, BufferedImage.TYPE_INT_ARGB);
        background=new BufferedImage(l,b,BufferedImage.TYPE_INT_ARGB);
        gscreen = (Graphics2D) screen.getGraphics();
        upsr = ups;
        nskip = skp;
        setPreferredSize(new Dimension(l,b));
        createBackground();
    }
    /*
    creates the background for the worls
     */
    protected void createBackground(){
        Graphics g=background.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0,l,b);
    }
    /*
    single update called upsr times per second
     */
    protected void update() {
        if (u == 50) {
            long c = System.nanoTime();
            ups = u * 1000000000.0 / (c - pt + delay * 25);
            fps = (f * 1000000000.0) / (c - pt + delay * 25);
            u = f = 25;
            pt = c;
        }
    }
    /*
    return lsnget
     */
    public int getL(){
        return l;
    }
    /*
    return breadth
     */
    public int getB(){
        return b;
    }
    /*
    single frame render on the internal buffer
     */
    protected void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(background,0,0,this);
        g.setColor(Color.white);
        g.drawString("ups " + ups, 20, 30);
        g.drawString("fps " + fps, 20, 45);
    }
    /*
    single frame render on the screen
     */
    public void paintComponent(Graphics gd) {
        super.paintComponent(gd);
        Graphics2D g = (Graphics2D)gd;
        if (g != null && screen != null) {
            g.rotate(Math.PI/2*rot,getL()/2,getB()/2);
            g.drawImage(screen, 0, 0, null);
        }else{
            System.out.println("No screen or img");
        }
    }
    /*
    single frame update + render
     */
    public void singleFrame(){
        u++;
        f++;
        pt=System.nanoTime();
        update();
        render(gscreen);
        paintRender();
    }
    /*
    active render the frame
     */
    private void paintRender() {
        Graphics2D g = (Graphics2D)this.getGraphics();
        if (g != null && screen != null) {
            g.rotate(Math.PI/2*rot,getL()/2,getB()/2);
            g.drawImage(screen, 0, 0, null);
        }else if(screen==null){
            System.out.println("No screen or img1");
        }
    }
    /*
    start or restart the animation
     */
    public void start() {
        if(!running) {
            pt = System.nanoTime();
            th = new Thread(this);
            running = true;
            th.start();
        }
    }
    /*
    Stop/pause the game
     */
    public void stop() {
        running = false;
    }
    //true when game is on
    boolean running;
    //the animation update render loop
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
            if (ups+2>=upsr || sk == nskip) {
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