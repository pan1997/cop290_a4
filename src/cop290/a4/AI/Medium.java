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
    double boardSize = 600;
    int numBalls;

    public Medium(animPanel parent, int orientation) {
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
                    double xp = 0, t = 10000, temp = 0;
                    int ym = 0;
                    if (balls.get(0).getVy() > 0)
                        t = (boardSize - balls.get(0).y) / balls.get(0).getVy();
                    else
                        t = -(boardSize + balls.get(0).y) / balls.get(0).getVy();

                    for (int i = 1; i < numBalls; i++) {
                        if (balls.get(i).getVy() > 0)
                            temp = (boardSize - balls.get(i).y) / balls.get(i).getVy();
                        else
                            temp = -(boardSize + balls.get(i).y) / balls.get(i).getVy();

                        if (t > temp) {
                            t = temp;
                            ym = i;
                        }
                    }

                    double d = t * Math.abs(balls.get(ym).getVx());
                    //double q = d/boardSize;
                    double rem = d % boardSize;

                    if (balls.get(ym).getVx() > 0) {
                        xp = balls.get(ym).x + rem;
                        if (xp > boardSize) xp = 2 * boardSize - xp;
                    } else {
                        xp = balls.get(ym).x - rem;
                        if (xp < 0) xp = -1 * xp;
                    }

                    if (x + 50.0 / 2 < xp) {
                        vel = 1;
                    } else if (x - 50.0 / 2 > xp) {
                        vel = -1;
                    }

                    break;
                }

                case 1: {
                    double yp = 0, t = 10000, temp = 0;
                    int ym = 0;
                    if (balls.get(0).getVx() > 0)
                        t = (boardSize - balls.get(0).x) / balls.get(0).getVx();
                    else
                        t = -(boardSize + balls.get(0).x) / balls.get(0).getVx();

                    for (int i = 1; i < numBalls; i++) {
                        if (balls.get(i).getVx() > 0)
                            temp = (boardSize - balls.get(i).x) / balls.get(i).getVx();
                        else
                            temp = -(boardSize + balls.get(i).x) / balls.get(i).getVx();

                        if (t > temp) {
                            t = temp;
                            ym = i;
                        }
                    }

                    double d = t * Math.abs(balls.get(ym).getVx());
                    //double q = d/boardSize;
                    double rem = d % boardSize;

                    if (balls.get(ym).getVy() > 0) {
                        yp = balls.get(ym).y + rem;
                        if (yp > boardSize) yp = 2 * boardSize - yp;
                    } else {
                        yp = balls.get(ym).y - rem;
                        if (yp < 0) yp = -1 * yp;
                    }

                    if (y + 50.0 / 2 < yp) {
                        vel = -1;
                    } else if (y - 50.0 / 2 > yp) {
                        vel = 1;
                    }

                    break;
                }

                case 2: {
                    double xp = 0, t = 10000, temp = 0;
                    int ym = 0;
                    if (balls.get(0).getVy() > 0)
                        t = (2 * boardSize - balls.get(0).y) / balls.get(0).getVy();
                    else
                        t = -(balls.get(0).y) / balls.get(0).getVy();

                    for (int i = 1; i < numBalls; i++) {
                        if (balls.get(i).getVy() > 0)
                            temp = (2 * boardSize - balls.get(i).y) / balls.get(i).getVy();
                        else
                            temp = -(balls.get(i).y) / balls.get(i).getVy();

                        if (t > temp) {
                            t = temp;
                            ym = i;
                        }
                    }

                    double d = t * Math.abs(balls.get(ym).getVx());
                    //double q = d/boardSize;
                    double rem = d % boardSize;

                    if (balls.get(ym).getVx() > 0) {
                        xp = balls.get(ym).x + rem;
                        if (xp > boardSize) xp = 2 * boardSize - xp;
                    } else {
                        xp = balls.get(ym).x - rem;
                        if (xp < 0) xp = -1 * xp;
                    }

                    if (x + 50.0 / 2 < xp) {
                        vel = -1;
                    } else if (x - 50.0 / 2 > xp) {
                        vel = 1;
                    }

                    break;
                }

                case 3: {
                    double yp = 0, t = 10000, temp = 0;
                    int ym = 0;
                    if (balls.get(0).getVx() > 0)
                        t = (2 * boardSize - balls.get(0).x) / balls.get(0).getVx();
                    else
                        t = -(balls.get(0).x) / balls.get(0).getVx();

                    for (int i = 1; i < numBalls; i++) {
                        if (balls.get(i).getVx() > 0)
                            temp = (2 * boardSize - balls.get(i).x) / balls.get(i).getVx();
                        else
                            temp = -(balls.get(i).x) / balls.get(i).getVx();

                        if (t > temp) {
                            t = temp;
                            ym = i;
                        }
                    }

                    double d = t * Math.abs(balls.get(ym).getVx());
                    //double q = d/boardSize;
                    double rem = d % boardSize;

                    if (balls.get(ym).getVy() > 0) {
                        yp = balls.get(ym).y + rem;
                        if (yp > boardSize) yp = 2 * boardSize - yp;
                    } else {
                        yp = balls.get(ym).y - rem;
                        if (yp < 0) yp = -1 * yp;
                    }

                    if (y + 50.0 / 2 < yp) {
                        vel = 1;
                    } else if (y - 50.0 / 2 > yp) {
                        vel = -1;
                    }

                    break;
                }
            }
        }
        super.updateSpirit(dt);
    }
}
