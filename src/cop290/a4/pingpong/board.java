package cop290.a4.pingpong;

import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;
import cop290.a4.network.broadcasting;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by pankaj on 12/4/16.
 */
public class board extends animPanel implements KeyListener{
    ArrayList<Spirit> spirits;
    broadcasting bds;
    ArrayList<bat> bats;
    physics ph;

    int[] lives;
    public board(int l, int b, int ups, int skp,physics physics,broadcasting bds) {
        super(l, b, ups, skp);
        lives=new int[4];
        lives[0]=lives[1]=lives[2]=lives[3]=5;
        spirits =new ArrayList<>();
        bats=new ArrayList<>();
        if(bds!=null)
            bds.setSpirits(spirits);
        this.bds=bds;
        ph=physics;
        ArrayList<block> corners = new ArrayList<block>();
        for(int i=0;i<3;i++){
            Ball ball=new Ball(this);
            spirits.add(ball);
            ph.add(ball);
        }
        for(int i=0;i<4;i++){
            block blck = new block(this);
            blck.l = blck.b = 15;
            corners.add(blck);
        }
        corners.get(0).x = corners.get(0).y = 0;
        corners.get(1).x = corners.get(1).y = l-15;
        corners.get(2).x = 0; corners.get(2).y = l-15;
        corners.get(3).x = l-15; corners.get(3).y = 0;
        for(int i = 0; i < 4; i++){
            spirits.add(corners.get(i));
            ph.add(corners.get(i));
        }
        bat bt=new bat(this,0);
        spirits.add(bt);
        ph.add(bt);
        bats.add(bt);
        bt=new bat(this,1);
        spirits.add(bt);
        ph.add(bt);
        bats.add(bt);
        bt=new bat(this,2);
        spirits.add(bt);
        ph.add(bt);
        bats.add(bt);
        bt=new bat(this,3);
        spirits.add(bt);
        ph.add(bt);
        bats.add(bt);
        setStage(2);
        if(bds!=null)bds.setInitMessage("WELCOME\nstage "+5);
    }

    private void setStage(int stg){
        switch (stg){
            case 1:{
                block blk=new block(this);
                blk.x=l/2-35;
                blk.y=b/2-35;
                blk.l=blk.b=70;
                spirits.add(blk);
                ph.add(blk);
            }break;
            case 2:{
                block blk=new block(this);
                blk.x=l/2-40;
                blk.y=b/2-40;
                blk.l=80;
                blk.b=10;
                spirits.add(blk);
                ph.add(blk);
                blk=new block(this);
                blk.x=l/2-40;
                blk.y=b/2+30;
                blk.l=80;
                blk.b=10;
                spirits.add(blk);
                ph.add(blk);
            }break;
            case 3:{
                circularObstacle circ=new circularObstacle(this,Color.ORANGE,null,null);
                circ.x=l/2;
                circ.y=b/2;
                circ.r=25;
                spirits.add(circ);
                ph.add(circ);
            }
            case 4:{
                circularObstacle c1=new circularObstacle(this,null,Color.MAGENTA,Color.MAGENTA);
                circularObstacle c2=new circularObstacle(this,null,Color.MAGENTA,Color.MAGENTA);
                c1.r=c2.r=10;
                c1.x=150;c1.y=150;
                c2.x=c2.y=450;
                spirits.add(c1);
                spirits.add(c2);
                ph.addTeleportPair(c1,c2);
            }
            case 5:{
                block blk=new block(this);
                blk.x=l/2-35;
                blk.y=b/2-35;
                blk.l=blk.b=70;
                spirits.add(blk);
                ph.add(blk);
                circularObstacle c1=new circularObstacle(this,null,Color.MAGENTA,Color.MAGENTA);
                circularObstacle c2=new circularObstacle(this,null,Color.MAGENTA,Color.MAGENTA);
                c1.r=c2.r=10;
                c1.x=150;c1.y=150;
                c2.x=c2.y=450;
                spirits.add(c1);
                spirits.add(c2);
                ph.addTeleportPair(c1,c2);
                c1=new circularObstacle(this,null,Color.CYAN,Color.CYAN);
                c2=new circularObstacle(this,null,Color.CYAN,Color.CYAN);
                c1.r=c2.r=10;
                c1.x=450;c1.y=150;
                c2.x=150;
                c2.y=450;
                spirits.add(c1);
                spirits.add(c2);
                ph.addTeleportPair(c1,c2);
            }
        }
    }

    public void closeSide(int or){
        lives[or]--;
        if(lives[or]==0) {
            block blk = new block(this);
            blk.x = or == 1 ? l - 20 : 0;
            blk.y = or == 0 ? b - 20 : 0;
            blk.l = or % 2 == 0 ? l : 20;
            blk.b = or % 2 == 0 ? 20 : b;
            spirits.add(blk);
            ph.add(blk);
        }

    }
    @Override
    protected void createBackground() {
        Graphics g=background.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,l,b);
        g.setColor(Color.WHITE);
        g.drawLine(15,0,15,b);
        g.drawLine(l-15,0,l-15,b);
        g.drawLine(0,15,l,15);
        g.drawLine(0,b-15,l,b-15);
    }

    @Override
    protected void update() {
        super.update();
        ph.update();
        try {
            if(bds!=null) {
                bds.broadcast();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    protected void render(Graphics2D g) {
        super.render(g);
        g.drawString("lives "+lives[0]+" "+lives[1]+" "+lives[2]+" "+lives[3],20,60);
        spirits.forEach(e->e.renderSpirit(g));
    }

    static double bd=1;
    @Override
    public void keyTyped(KeyEvent e) {

        switch (e.getKeyChar()){
            case 'd':bats.get(0).vel=bd;break;
            case 's':bats.get(0).vel=-bd;break;
            case 'r':bats.get(1).vel=bd;break;
            case 'f':bats.get(1).vel=-bd;break;
            case 'w':bats.get(2).vel=bd;break;
            case 'e':bats.get(2).vel=-bd;break;
            case 'a':bats.get(3).vel=bd;break;
            case 'q':bats.get(3).vel=-bd;break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()){
            case 'd':if(bats.get(0).vel>0)bats.get(0).vel=0;break;
            case 's':if(bats.get(0).vel<0)bats.get(0).vel=0;break;
            case 'r':if(bats.get(1).vel>0)bats.get(1).vel=0;break;
            case 'f':if(bats.get(1).vel<0)bats.get(1).vel=0;break;
            case 'w':if(bats.get(2).vel>0)bats.get(2).vel=0;break;
            case 'e':if(bats.get(2).vel<0)bats.get(2).vel=0;break;
            case 'a':if(bats.get(3).vel>0)bats.get(3).vel=0;break;
            case 'q':if(bats.get(3).vel<0)bats.get(3).vel=0;break;
        }
    }
}
