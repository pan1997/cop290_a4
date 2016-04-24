package cop290.a4.pingpong;

import cop290.a4.animation.Spirit;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pankaj on 12/4/16.
 */
public class physics {
    ArrayList<Ball> balls;
    ArrayList<block> blocks;
    ArrayList<circularObstacle> circs, holes;
    Map<Integer, circularObstacle> teleport;
    Map<Integer, Spirit> map;

    public physics() {
        balls = new ArrayList<>();
        blocks = new ArrayList<>();
        map = new HashMap<>();
        teleport = new HashMap<>();
        circs = new ArrayList<>();
        holes = new ArrayList<>();
    }

    void add(Ball b) {
        balls.add(b);
        map.put(b.getID(), b);
    }

    void addTeleportPair(circularObstacle c1, circularObstacle c2) {
        holes.add(c1);
        holes.add(c2);
        teleport.put(c1.getID(), c2);
        teleport.put(c2.getID(), c1);
    }

    void add(block b) {
        blocks.add(b);
        map.put(b.getID(), b);
    }

    void add(circularObstacle c) {
        circs.add(c);
        map.put(c.getID(), c);
    }

    static final int wall = 1000;

    long last = 0;

    void update() {
        long current = System.nanoTime();
        double dt = last != 0 ? (current - last) / 1000000000.0 : 0;
        //System.out.println(dt);
        last = current;
        blocks.forEach(e -> e.updateSpirit(dt));
        balls.forEach(e -> e.updateSpirit(dt / 2));
        for (Ball b : balls) {
            if (b.x + b.r >= b.parent().getB() || b.x <= b.r) {
                b.lastid = wall;
                b.vx = -b.vx;
            }
            if (b.y + b.r >= b.parent().getL() || b.y <= b.r) {
                b.lastid = wall;
                b.vy = -b.vy;
            }
            b.updateSpirit(dt / 2);
        }
        for (int i = 0; i < balls.size(); i++) {
            Ball b1 = balls.get(i);
            for (int j = i + 1; j < balls.size(); j++) {
                Ball b2 = balls.get(j);
                double r = Math.sqrt((b1.x - b2.x) * (b1.x - b2.x) + (b1.y - b2.y) * (b1.y - b2.y));
                if (r <= b1.r + b2.r) {
                    b1.lastid = b2.getID();
                    b2.lastid = b1.getID();
                    double rxu = (b1.x - b2.x) / r;
                    double ryu = (b1.y - b2.y) / r;
                    double v1_along = b1.vx * rxu + b1.vy * ryu;
                    double v2_along = b2.vx * rxu + b2.vy * ryu;
                    b1.vx += (v2_along - v1_along) * rxu;
                    b1.vy += (v2_along - v1_along) * ryu;
                    b2.vx += (v1_along - v2_along) * rxu;
                    b2.vy += (v1_along - v2_along) * ryu;
                }
            }
        }

        for (Ball bl : balls)
            for (circularObstacle c : circs) {
                double r = Math.sqrt((bl.x - c.x) * (bl.x - c.x) + (bl.y - c.y) * (bl.y - c.y));
                if (r <= bl.r + c.r) {
                    bl.lastid = c.getID();
                    double rxu = (bl.x - c.x) / r;
                    double ryu = (bl.y - c.y) / r;
                    double v1_along = bl.vx * rxu + bl.vy * ryu;
                    bl.vx -= 2 * v1_along * rxu;
                    bl.vy -= 2 * v1_along * ryu;
                }
            }

        for (Ball bl : balls)
            for (circularObstacle c : holes)
                if (bl.lastid != c.getID()) {
                    double r = Math.sqrt((bl.x - c.x) * (bl.x - c.x) + (bl.y - c.y) * (bl.y - c.y));
                    if (r <= bl.r+c.r) {
                        circularObstacle to = teleport.get(c.getID());
                        bl.lastid = to.getID();
                        bl.x = to.x;
                        bl.y = to.y;
                    }
                }
        for (Ball ball : balls) {
            for (block bat : blocks)
                if (ball.lastid != bat.getID() && ball.e2d.intersects(bat.x, bat.y, bat.l, bat.b)) {
                    ball.lastid = bat.getID();

                    boolean wx = ball.x >= bat.x && ball.x <= bat.x + bat.l;
                    boolean wy = ball.y >= bat.y && ball.y <= bat.y + bat.b;

                    //if ((top || bottom) && !(left || right)) {
                    if (wx && !wy) {
                        System.out.println("y");
                        ball.vy = -ball.vy;
                    } else if (wy && !wx) {
                        ball.vx = -ball.vx;
                        System.out.println("x");
                    } else {//edge
                        System.out.println("Edge");
                        double x, y;
                        x = ball.x < bat.x ? bat.x : bat.x + bat.l;
                        y = ball.y < bat.y ? bat.y : bat.y + bat.b;
                        double r = Math.sqrt((ball.x - x) * (ball.x - x) + (ball.y - y) * (ball.y - y));
                        double rxu = (ball.x - x) / r;
                        double ryu = (ball.y - y) / r;
                        double v1_along = ball.vx * rxu + ball.vy * ryu;
                        double v2_along = 0;
                        if (bat instanceof bat) {
                            bat b = (bat) bat;
                            double bv = b.vel * ball.parent().getL();
                            double vx = 0, vy = 0;
                            switch (b.or) {
                                case 0:
                                    vx = bv;
                                    break;
                                case 1:
                                    vy = -bv;
                                    break;
                                case 2:
                                    vx = -bv;
                                    break;
                                case 3:
                                    vy = bv;
                                    break;
                            }
                            v2_along = vx * rxu + vy * ryu;
                        }
                        ball.vx += -2 * (v1_along) * rxu+v2_along*rxu;
                        ball.vy += -2 * (v1_along) * ryu+v2_along*ryu;
                    }

                }
        }

    }
}