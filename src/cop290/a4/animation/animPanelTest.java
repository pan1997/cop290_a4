package cop290.a4.animation;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by pankaj on 29/3/16.
 */
public class animPanelTest extends animPanel implements MouseMotionListener {
    ballSpirit ball;
    batSpirit bat;
    batSpirit cpu1,cpu2,cpu3;
    boolean play;
    public animPanelTest(int l, int b, int ups, int skp) {
        super(l, b, ups, skp);
        ball=new ballSpirit(this);
        bat=new batSpirit(this,0);
        cpu1=new batSpirit(this,1);
        cpu2=new batSpirit(this,2);
        cpu3=new batSpirit(this,3);
        play=true;
    }

    protected void update() {
        super.update();
        if(play) {
            ball.updateSpirit();
            bat.updateSpirit();
            cpu1.updateSpirit();
            cpu2.updateSpirit();
            cpu3.updateSpirit();
            cpu1.updateLoc(ball.x, ball.y);
            cpu2.updateLoc(ball.x, ball.y);
            cpu3.updateLoc(ball.x, ball.y);
            if (ball.x <= cpu2.px + 50 && ball.x >= cpu2.px && (ball.y + ball.r >= cpu2.py && ball.y - ball.r <= cpu2.py + 10))
                ball.vy = -ball.vy;
            if (ball.y <= cpu1.py + 50 && ball.y >= cpu1.py && (ball.x + ball.r >= cpu1.px && ball.x - ball.r <= cpu1.px + 10))
                ball.vx = -ball.vx;
            if (ball.y <= cpu3.py + 50 && ball.y >= cpu3.py && (ball.x + ball.r >= cpu3.px && ball.x - ball.r <= cpu3.px + 10))
                ball.vx = -ball.vx;
            if (ball.x <= bat.px + 50 && ball.x >= bat.px && (ball.y + ball.r >= bat.py && ball.y - ball.r <= bat.py + 10))
                ball.vy = -ball.vy;
            if (ball.x + ball.r >= b || ball.x <= ball.r || ball.y + ball.r >= l || ball.y <= ball.r)
                play=false;
        }

    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        if(!play){
            g.drawString("Game over!!!",200,250);
        }
        ball.renderSpirit(g);
        bat.renderSpirit(g);
        cpu1.renderSpirit(g);
        cpu2.renderSpirit(g);
        cpu3.renderSpirit(g);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(play)
            bat.updateLoc(e.getX(),e.getY());
    }
}