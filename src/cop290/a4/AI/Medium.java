package cop290.a4.AI;

import java.util.ArrayList;

import cop290.a4.animation.animPanel;
import cop290.a4.pingpong.Ball;
import cop290.a4.pingpong.bat;
import cop290.a4.pingpong.board;

/**
 * Created by rishubh on 27/4/16.
 */
public class Medium extends bat {

    ArrayList<Ball> balls;
    int numBalls;

    public Medium(animPanel parent, int orientation) {
        super(parent, orientation);

        balls = ((board)parent).getph().getBalls();
    }

    @Override
    public void updateSpirit(double dt) {

        numBalls = balls.size();

        switch (or){
            case 0 :{
                
            }
        }

        super.updateSpirit(dt);
    }
}
