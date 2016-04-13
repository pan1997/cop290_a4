package cop290.a4.pingpong;

import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by pankaj on 12/4/16.
 */
public class board extends animPanel {

    ArrayList<Spirit> spirits;
    physics ph;
    public board(int l, int b, int ups, int skp) {
        super(l, b, ups, skp);
        spirits =new ArrayList<>();
        ph=new physics();
        for(int i=0;i<4;i++){
            Ball ball=new Ball(this);
            spirits.add(ball);
            ph.addBall(ball);
        }
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
    }

    @Override
    protected void render(Graphics g) {
        super.render(g);
        spirits.forEach(e->e.renderSpirit(g));
    }
}
