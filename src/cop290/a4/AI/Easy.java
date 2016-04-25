package cop290.a4.AI;

import java.util.ArrayList;

import cop290.a4.animation.animPanel;
import cop290.a4.pingpong.Ball;
import cop290.a4.pingpong.bat;
import cop290.a4.pingpong.board;

/**
 * Created by rishubh on 25/4/16.
 */
public class Easy extends bat {

    ArrayList<Ball> balls;
    int numBalls;

    public Easy(animPanel parent, int orientation) {
        super(parent, orientation);

        balls = ((board)parent).getph().getBalls();
    }

    @Override
    public void updateSpirit(double dt) {

        numBalls = balls.size();

        super.updateSpirit(dt);
    }
}
