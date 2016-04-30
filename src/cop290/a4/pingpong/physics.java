package cop290.a4.pingpong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cop290.a4.animation.Spirit;

/**
 * Created by pankaj on 12/4/16.
 * It performs the physics/world updates
 */
public class physics {
    // the set of balls
    ArrayList<Ball> balls;
    //set of blocks including bats
    ArrayList<block> blocks;
    //set of circular objects and holes
    ArrayList<circularObstacle> circs, holes;
    //teleport maps
    Map<Integer, circularObstacle> teleport;
    //map of id to spirits
    Map<Integer, Spirit> map;
    double rv = 0.2;
    //constructor
    public physics() {
        balls = new ArrayList<>();
        blocks = new ArrayList<>();
        map = new HashMap<>();
        teleport = new HashMap<>();
        circs = new ArrayList<>();
        holes = new ArrayList<>();
    }
    //add balls to the simulation
    void add(Ball b) {
        balls.add(b);
        map.put(b.getID(), b);
    }
    //add teleport pair to the simulation
    void addTeleportPair(circularObstacle c1, circularObstacle c2) {
        holes.add(c1);
        holes.add(c2);
        teleport.put(c1.getID(), c2);
        teleport.put(c2.getID(), c1);
        map.put(c1.getID(), c1);
        map.put(c2.getID(), c2);
    }
    //get balls
    public ArrayList<Ball> getBalls() {
        return balls;
    }
    //add block or bat to the simulation
    void add(block b) {
        blocks.add(b);
        map.put(b.getID(), b);
    }
    //add simple circular obstacle to the world
    void add(circularObstacle c) {
        circs.add(c);
        map.put(c.getID(), c);
    }
    //id of wall
    static final int wall = 1000;

    long last = 0;
    //single discrete frame update of world
    void update() {
        long current = System.nanoTime();
        double dt = last != 0 ? (current - last) / 1000000000.0 : 0;
        //System.out.println(dt);
        last = current;
        blocks.forEach(e -> e.updateSpirit(dt));
        for (Ball b : balls) {
            b.x += b.vx * dt / 2;
            b.y += b.vy * dt / 2;
            b.theta += b.w * dt / 2;
            double vr=b.vx*b.vx+b.vy*b.vy;
            if(vr>250000){
                b.vx*=0.95;
                b.vy*=0.95;
            }
        }
        for (Ball b : balls) {
            if (b.x + b.r >= b.parent().getB() || b.x <= b.r) {
                b.lastid = wall;
                b.vx = -b.vx;
                if(b.x + b.r >= b.parent().getB()){
                    //b.vy = b.vy + (b.w*b.r)*rv;
                }
                else {
                    //b.vy = b.vy - (b.w * b.r)*rv;
                }
                if (b.x <= b.r)
                    ((board) b.parent()).closeSide(3);
                else ((board) b.parent()).closeSide(1);
            }
            if (b.y + b.r >= b.parent().getL() || b.y <= b.r) {
                b.lastid = wall;
                b.vy = -b.vy;
                if(b.y + b.r >= b.parent().getL()){
                    //b.vx = b.vx - (b.w*b.r)*rv;
                }
                else {
                    //b.vx = b.vx + (b.w * b.r)*rv;
                }
                if (b.y <= b.r)
                    ((board) b.parent()).closeSide(2);
                else ((board) b.parent()).closeSide(0);
            }
            b.x += b.vx * dt / 2;
            b.y += b.vy * dt / 2;
            b.theta += b.w * dt / 2;
        }
        balls.forEach(e -> e.updateSpirit(dt));
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

                    double wTemp = b1.w;
                    b1.w = -b2.w;
                    b2.w = -wTemp;
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
                    if (r <= bl.r + c.r) {
                        circularObstacle to = teleport.get(c.getID());
                        bl.lastid = to.getID();
                        bl.x = to.x;
                        bl.y = to.y;
                    }
                }
        for (Ball ball : balls) {
            for (block bat : blocks)
                if (ball.lastid != bat.getID() && ball.e2d.intersects(bat.x, bat.y, bat.l, bat.b)) {

                    if(bat instanceof bat) {
                        Sounds sounds = new Sounds();
                        sounds.initSound();
                        sounds.playSound("bat");
                    }

                    ball.lastid = bat.getID();

                    boolean wx = ball.x >= bat.x && ball.x <= bat.x + bat.l;
                    boolean wy = ball.y >= bat.y && ball.y <= bat.y + bat.b;

                    //if ((top || bottom) && !(left || right)) {
                    if (wx && !wy) {
                        //System.out.println("y");
                        ball.vy = -ball.vy;
                        /*if(bat instanceof bat){
                            bat b = (bat) bat;
                            switch (bat.b){
                                case 0 :
                                    ball.vx = ball.vx - (ball.w*ball.r)*rv;
                                    break;
                                case 2 :
                                    ball.vx = ball.vx + (ball.w*ball.r)*rv;
                                    break;
                            }
                        }*/
                    } else if (wy && !wx) {
                        ball.vx = -ball.vx;
                        /*if(bat instanceof bat){
                            bat b = (bat) bat;
                            switch (bat.b){
                                case 0 :
                                    ball.vy = ball.vy + (ball.w*ball.r)*rv;
                                    break;
                                case 2 :
                                    ball.vy = ball.vy - (ball.w*ball.r)*rv;
                                    break;
                            }
                        }*/
                        //System.out.println("x");
                    } else {//edge
                        //System.out.println("Edge");
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
                        ball.vx += -2 * (v1_along) * rxu + v2_along * rxu;
                        ball.vy += -2 * (v1_along) * ryu + v2_along * ryu;
                    }
                }
        }
    }
}