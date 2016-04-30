package cop290.a4.AI;

import java.util.ArrayList;

import cop290.a4.animation.animPanel;
import cop290.a4.pingpong.Ball;
import cop290.a4.pingpong.bat;
import cop290.a4.pingpong.board;
/**
 * Created by pankaj on 28/4/16.
 */
public class PanAI extends bat {
    ArrayList<Ball> balls;

    //computes best possible selections of balls to hit and also maintains a near to center positions whenever it cam
    //for better reachability to incoming balls

    public PanAI(animPanel parent, int orientation) {
        super(parent, orientation);
        ai=true;
        balls = ((board) parent).getph().getBalls();
    }

    @Override
    public void updateSpirit(double dt) {
        if(ai) {
            double mind = Double.MAX_VALUE, mina = 0;
            int mini = -1;
            for (Ball b : balls) {
                double d = Double.MAX_VALUE, a = 0.5;
                switch (or) {
                    case 0:
                        if (b.getVy() < 0) break;
                        d = (parent.getB() - b.y) / b.getVy();
                        a = b.x / parent.getL();
                        break;
                    case 1:
                        if (b.getVx() < 0) break;
                        d = (parent.getL() - b.x) / b.getVx();
                        a = 1 - b.y / parent.getB();
                        break;
                    case 2:
                        if (b.getVy() > 0) break;
                        d = -b.y / b.getVy();
                        a = 1 - b.x / parent.getL();
                        break;
                    case 3:
                        if (b.getVx() > 0) break;
                        d = -b.x / b.getVx();
                        a = b.y / parent.getB();
                        break;
                }
                if (d <= mind) {
                    mind = d;
                    mina = a;
                }
            }
            //System.out.println("mind "+mind+" mina "+mina);
            if (Math.abs(loc - mina) > 0.02)
                if (mina < loc)
                    vel = -0.8;
                else vel = 0.8;
            else
                vel = 0;
        }
        super.updateSpirit(dt);
    }
}
