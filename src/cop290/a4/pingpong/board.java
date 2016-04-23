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
    public board(int l, int b, int ups, int skp,physics physics) {
        super(l, b, ups, skp);
        spirits =new ArrayList<>();
        bats=new ArrayList<>();
        bds=new broadcasting(1234,spirits);
        ph=physics;
        ArrayList<block> corners = new ArrayList<block>();
        for(int i=0;i<4;i++){
            Ball ball=new Ball(this);
            spirits.add(ball);
            ph.add(ball);
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
        block blk=new block(this);
        blk.x=250;
        blk.y=250;
        blk.l=blk.b=70;
        spirits.add(blk);
        ph.add(blk);
    }

    @Override
    protected void createBackground() {
        Graphics g=background.getGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,0,l,b);
        g.setColor(Color.black);
        g.drawLine(15,0,15,b);
        g.drawLine(l-15,0,l-15,b);
        g.drawLine(0,15,l,15);
        g.drawLine(0,b-15,l,b-15);
    }

    @Override
    protected void update() {
        super.update();
        //spirits.forEach(e->e.updateSpirit());
        ph.update();
        try {
            bds.broadcast();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    protected void render(Graphics2D g) {
        super.render(g);
        spirits.forEach(e->e.renderSpirit(g));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()){
            case 'd':bats.get(0).vel=0.005;break;
            case 's':bats.get(0).vel=-0.005;break;
            case 'r':bats.get(1).vel=0.005;break;
            case 'f':bats.get(1).vel=-0.005;break;
            case 'w':bats.get(2).vel=0.005;break;
            case 'e':bats.get(2).vel=-0.005;break;
            case 'a':bats.get(3).vel=0.005;break;
            case 'q':bats.get(3).vel=-0.005;break;
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
