package cop290.a4.pingpong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cop290.a4.AI.Medium;
import cop290.a4.AI.PanAI;
import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;
import cop290.a4.network.broadcasting;

/**
 * Created by pankaj on 12/4/16.
 */
public class board extends animPanel implements KeyListener {
    ArrayList<Spirit> spirits;
    broadcasting bds;
    public ArrayList<bat> bats;
    physics ph;
    int[] lives;
    boolean closed[];

    int userId;

    public board(int l, int b, int ups, int skp, physics physics, broadcasting bds, int s) {
        super(l, b, ups, skp);
        lives = new int[4];
        closed=new boolean[4];
        lives[0] = lives[1] = lives[2] = lives[3] = 5;
        spirits = new ArrayList<>();
        bats = new ArrayList<>();
        if (bds != null) {
            bds.setSpirits(spirits);
            bds.parent = this;
            userId = 0;
            bds.users = 1;
        }
        this.bds = bds;
        ph = physics;
        ArrayList<block> corners = new ArrayList<block>();
        for (int i = 0; i < 3; i++) {
            Ball ball = new Ball(this);
            spirits.add(ball);
            ph.add(ball);
        }
        for (int i = 0; i < 4; i++) {
            block blck = new block(this);
            blck.l = blck.b = 15;
            corners.add(blck);
        }
        corners.get(0).x = corners.get(0).y = 0;
        corners.get(1).x = corners.get(1).y = l - 15;
        corners.get(2).x = 0;
        corners.get(2).y = l - 15;
        corners.get(3).x = l - 15;
        corners.get(3).y = 0;
        for (int i = 0; i < 4; i++) {
            spirits.add(corners.get(i));
            ph.add(corners.get(i));
        }
        if (ph instanceof Nphysics) {
            bat bt = new bat(this, 0);
            spirits.add(bt);
            ph.add(bt);
            bats.add(bt);
            bt = new bat(this, 1);
            spirits.add(bt);
            ph.add(bt);
            bats.add(bt);
            bt = new bat(this, 2);
            spirits.add(bt);
            ph.add(bt);
            bats.add(bt);
            bt = new bat(this, 3);
            spirits.add(bt);
            ph.add(bt);
            bats.add(bt);
        } else {
            bat bt = new PanAI(this, 0);
            spirits.add(bt);
            ph.add(bt);
            bats.add(bt);
            bt.ai = false;
            bt = new Medium(this, 1);
            spirits.add(bt);
            ph.add(bt);
            bats.add(bt);
            bt = new PanAI(this, 02);
            spirits.add(bt);
            ph.add(bt);
            bats.add(bt);
            bt = new Medium(this, 3);
            spirits.add(bt);
            ph.add(bt);
            bats.add(bt);
        }
        if (s > 0) {
            setStage(s);
            if (bds != null) bds.setInitMessage("stage " + s);
        }
    }

    public physics getph() {
        return ph;
    }

    public void setStage(int stg) {
        switch (stg) {
            case 1: {
                block blk = new block(this);
                blk.x = l / 2 - 35;
                blk.y = b / 2 - 35;
                blk.l = blk.b = 70;
                spirits.add(blk);
                ph.add(blk);
            }
            break;
            case 2: {
                block blk = new block(this);
                blk.x = l / 2 - 40;
                blk.y = b / 2 - 40;
                blk.l = 80;
                blk.b = 10;
                spirits.add(blk);
                ph.add(blk);
                blk = new block(this);
                blk.x = l / 2 - 40;
                blk.y = b / 2 + 30;
                blk.l = 80;
                blk.b = 10;
                spirits.add(blk);
                ph.add(blk);
            }
            break;
            case 3: {
                circularObstacle circ = new circularObstacle(this, Color.ORANGE, null, null);
                circ.x = l / 2;
                circ.y = b / 2;
                circ.r = 35;
                spirits.add(circ);
                ph.add(circ);
            }
            break;
            case 4: {
                circularObstacle c1 = new circularObstacle(this, null, Color.MAGENTA, Color.MAGENTA);
                circularObstacle c2 = new circularObstacle(this, null, Color.MAGENTA, Color.MAGENTA);
                c1.r = c2.r = 10;
                c1.x = 180;
                c1.y = 180;
                c2.x = c2.y = 420;
                spirits.add(c1);
                spirits.add(c2);
                ph.addTeleportPair(c1, c2);
            }
            break;
            case 5: {
                block blk = new block(this);
                blk.x = l / 2 - 35;
                blk.y = b / 2 - 35;
                blk.l = blk.b = 70;
                spirits.add(blk);
                ph.add(blk);
                circularObstacle c1 = new circularObstacle(this, null, Color.MAGENTA, Color.MAGENTA);
                circularObstacle c2 = new circularObstacle(this, null, Color.MAGENTA, Color.MAGENTA);
                c1.r = c2.r = 10;
                c1.x = 180;
                c1.y = 180;
                c2.x = c2.y = 420;
                spirits.add(c1);
                spirits.add(c2);
                ph.addTeleportPair(c1, c2);
                c1 = new circularObstacle(this, null, Color.CYAN, Color.CYAN);
                c2 = new circularObstacle(this, null, Color.CYAN, Color.CYAN);
                c1.r = c2.r = 10;
                c1.x = 420;
                c1.y = 180;
                c2.x = 180;
                c2.y = 420;
                spirits.add(c1);
                spirits.add(c2);
                ph.addTeleportPair(c1, c2);
            }
        }
    }

    public void closeSide(int or) {
        lives[or]--;
        bds.broadcast("life " + lives[0] + " " + lives[1] + " " + lives[2] + " " + lives[3]);
    }

    @Override
    public void start() {
        ph.last = 0;
        super.start();
    }

    @Override
    protected void createBackground() {
        Graphics g = background.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, l, b);
        g.setColor(Color.WHITE);
        g.drawLine(15, 0, 15, b);
        g.drawLine(l - 15, 0, l - 15, b);
        g.drawLine(0, 15, l, 15);
        g.drawLine(0, b - 15, l, b - 15);
    }

    @Override
    protected void update() {
        super.update();
        ph.update();
        try {
            if (bds != null) {
                bds.broadcast();
                //
                // bds.broadcast("bats " + bats.get(0).loc + " " + bats.get(1).loc + " " + bats.get(2).loc + " " + bats.get(3).loc);
            }
            for (int i = 0; i < bats.size(); i++) {
                bat b = bats.get(i);
                if (b.ai || i == userId) {
                    if (bds != null)
                        bds.broadcast("bat " + i + " " + b.loc);
                    if (ph instanceof Nphysics)
                        ((Nphysics) ph).broadcast("bat " + i + " " + b.loc);
                }
            }
            for(int or=0;or<4;or++)
                if (lives[or] == 0&&!closed[or]) {
                    block blk = new block(this);
                    blk.x = or == 1 ? l - 20 : 0;
                    blk.y = or == 0 ? b - 20 : 0;
                    blk.l = or % 2 == 0 ? l : 20;
                    blk.b = or % 2 == 0 ? 20 : b;
                    spirits.add(blk);
                    ph.add(blk);
                    closed[or]=true;
                    int nc=0,p=0;
                    for(int i=0;i<4;i++)
                        if(closed[i])
                            nc++;
                        else p=i;
                    if(nc==3){
                        JOptionPane.showMessageDialog(null,"Player "+p+" Wins","Game Ends",JOptionPane.INFORMATION_MESSAGE);
                        stop();
                        //stop();
                    }
                }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void render(Graphics2D g) {
        super.render(g);
        g.drawString("lives " + lives[0] + " " + lives[1] + " " + lives[2] + " " + lives[3], 20, 60);
        spirits.forEach(e -> e.renderSpirit(g));
    }

    static double bd = 1;

    void setVel(int bt, double v) {
        bats.get(bt).vel = v;
        bats.get(bt).updateSpirit(0);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'g':
                bats.get(userId).vel = -bd;
                System.out.println("vset");
                //if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " "+userId+" " + bats.get(userId).vel);
                break;
            case 'h':
                bats.get(userId).vel = bd;
                System.out.println("vset");
                //if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " "+userId+" " + bats.get(userId).vel);
                break;
        }
        /*
        switch (e.getKeyChar()) {
            case 'd':
                bats.get(0).vel = bd;
                //if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 0 " + bats.get(0).vel);
                break;
            case 's':
                bats.get(0).vel = -bd;
                //if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 0 " + bats.get(0).vel);
                break;
            case 'r':
                bats.get(1).vel = bd;
                //if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 1 " + bats.get(1).vel);
                break;
            case 'f':
                bats.get(1).vel = -bd;
                //if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 1 " + bats.get(1).vel);
                break;
            case 'w':
                bats.get(2).vel = bd;
                //if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 2 " + bats.get(2).vel);
                break;
            case 'e':
                bats.get(2).vel = -bd;
                if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 2 " + bats.get(2).vel);
                break;
            case 'a':
                bats.get(3).vel = bd;
                if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 3 " + bats.get(3).vel);
                break;
            case 'q':
                bats.get(3).vel = -bd;
                if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 3 " + bats.get(3).vel);
                break;
        }*/
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'g':
                if (bats.get(userId).vel < 0) bats.get(userId).vel = 0;
                System.out.println("vunset");
                //if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " "+userId+" " + bats.get(userId).vel);
                break;
            case 'h':
                if (bats.get(userId).vel > 0) bats.get(userId).vel = 0;
                System.out.println("vunset");
                //if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " "+userId+" " + bats.get(userId).vel);
                break;
        }
        /*
        switch (e.getKeyChar()) {
            case 'd':
                if (bats.get(0).vel > 0) bats.get(0).vel = 0;
                if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 0 " + bats.get(0).vel);
                break;
            case 's':
                if (bats.get(0).vel < 0) bats.get(0).vel = 0;
                if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 0 " + bats.get(0).vel);
                break;
            case 'r':
                if (bats.get(1).vel > 0) bats.get(1).vel = 0;
                if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 1 " + bats.get(1).vel);
                break;
            case 'f':
                if (bats.get(1).vel < 0) bats.get(1).vel = 0;
                if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 1 " + bats.get(1).vel);
                break;
            case 'w':
                if (bats.get(2).vel > 0) bats.get(2).vel = 0;
                if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 2 " + bats.get(2).vel);
                break;
            case 'e':
                if (bats.get(2).vel < 0) bats.get(2).vel = 0;
                if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 2 " + bats.get(2).vel);
                break;
            case 'a':
                if (bats.get(3).vel > 0) bats.get(3).vel = 0;
                if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 3 " + bats.get(3).vel);
                break;
            case 'q':
                if (bats.get(3).vel < 0) bats.get(3).vel = 0;
                if (ph instanceof Nphysics) ((Nphysics) ph).broadcast("bmov " + userId + " 3 " + bats.get(3).vel);
                break;
        }*/
    }
}