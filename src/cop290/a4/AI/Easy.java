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
        ai=true;
        balls = ((board)parent).getph().getBalls();
    }

    @Override
    public void updateSpirit(double dt) {
        if(ai) {
            numBalls = balls.size();

            switch (or) {
                case 0: {
                    double xp = 0;
                    int ym = 0;
                    for (int i = 1; i < numBalls; i++) {
                        if (balls.get(i).getVy() > 0 && balls.get(i).y > balls.get(ym).y) {
                            ym = i;
                            xp = balls.get(i).x;
                        }
                    }
                    if (x + 50.0 / 3 < xp) {
                        vel = 1;
                    } else if (x - 50.0 / 3 > xp) {
                        vel = -1;
                    }
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
                    if (y + 50.0 / 3 < yp) {
                        vel = -1;
                    } else if (y - 50.0 / 3 > yp) {
                        vel = 1;
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
                    if (x + 50.0 / 3 < xp) {
                        vel = -1;
                    } else if (x - 50.0 / 3 > xp) {
                        vel = 1;
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
                    if (y + 50.0 / 3 < yp) {
                        vel = 1;
                    } else if (y - 50.0 / 3 > yp) {
                        vel = -1;
                    }
                    break;
                }

            }
        }
        super.updateSpirit(dt);
    }
}
