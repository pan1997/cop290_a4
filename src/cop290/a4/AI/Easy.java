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

    //easy AI follows the nearest ball and doesn't look at the speed or ball that'll reach faster.
    //easy AI's bat speed is also 0.6 times compared to fastest possible bat speed

    ArrayList<Ball> balls;
    int numBalls;
    double vBat = 0.6;

    public Easy(animPanel parent, int orientation) {
        super(parent, orientation);
        ai=true;
        balls = ((board)parent).getph().getBalls();
    }

    @Override
    public void updateSpirit(double dt) {
        if(ai) {
            numBalls = balls.size();

            switch (or) { //choosing direction acc to orientation
                case 0: {
                    double xp = 0;
                    int ym = 0;
                    for (int i = 1; i < numBalls; i++) {
                        if (balls.get(i).getVy() > 0 && balls.get(i).y > balls.get(ym).y) {
                            ym = i;
                            xp = balls.get(i).x;
                        }
                    } //choosing nearest ball coming towards bat
                    if(balls.get(ym).getVy() > 0) {
                        if (x + 50.0 / 3 < xp) {
                            vel = vBat;
                        } else if (x - 50.0 / 3 > xp) {
                            vel = -vBat;
                        } else {
                            vel = 0;
                        }
                    }//aligning bat with nearest ball
                    break;
                }

                case 1: {
                    double yp = 0;
                    int xm = 0;
                    for (int i = 1; i < numBalls; i++) {
                        if (balls.get(i).getVx() > 0 && balls.get(i).x > balls.get(xm).x) {
                            xm = i;
                            yp = balls.get(i).y;
                        }
                    }
                    if(balls.get(xm).getVx() > 0) {
                        if (y + 50.0 / 3 < yp) {
                            vel = -vBat;
                        } else if (y - 50.0 / 3 > yp) {
                            vel = vBat;
                        } else {
                            vel = 0;
                        }
                    }
                    break;
                }

                case 2: {
                    double xp = 0;
                    int ym = 0;
                    for (int i = 1; i < numBalls; i++) {
                        if (balls.get(i).getVy() < 0 && balls.get(i).y < balls.get(ym).y) {
                            ym = i;
                            xp = balls.get(i).x;
                        }
                    }
                    if(balls.get(ym).getVy() < 0) {
                        if (x + 50.0 / 3 < xp) {
                            vel = -vBat;
                        } else if (x - 50.0 / 3 > xp) {
                            vel = vBat;
                        } else {
                            vel = 0;
                        }
                    }
                    break;
                }

                case 3: {
                    double yp = 0;
                    int xm = 0;
                    for (int i = 1; i < numBalls; i++) {
                        if (balls.get(i).getVx() < 0 && balls.get(i).x < balls.get(xm).x) {
                            xm = i;
                            yp = balls.get(i).y;
                        }
                    }
                    if(balls.get(xm).getVx() < 0) {
                        if (y + 50.0 / 3 < yp) {
                            vel = vBat;
                        } else if (y - 50.0 / 3 > yp) {
                            vel = -vBat;
                        } else {
                            vel = 0;
                        }
                    }
                    break;
                }

            }
        }
        super.updateSpirit(dt);
    }
}
