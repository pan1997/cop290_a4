package cop290.a4.pingpong;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by pankaj on 12/4/16.
 */
public class physics {
    ArrayList<Ball> balls;
    ArrayList<block> blocks;

    physics() {
        balls = new ArrayList<>();
        blocks = new ArrayList<>();
    }

    void add(Ball b) {
        balls.add(b);
    }

    void add(block b) {
        blocks.add(b);
    }

    static final int wall = 1000;

    void update() {
        blocks.forEach(e -> e.updateSpirit());
        balls.forEach(e -> e.updateSpirit());
        for (Ball b : balls) {
            if (b.x + b.r >= b.parent().getB() || b.x <= b.r) {
                b.lastid = wall;
                b.vx = -b.vx;
            }
            if (b.y + b.r >= b.parent().getL() || b.y <= b.r) {
                b.lastid = wall;
                b.vy = -b.vy;
            }
            b.updateSpirit();
        }
        for (int i = 0; i < balls.size(); i++) {
            Ball b1 = balls.get(i);
            for (int j = i + 1; j < balls.size(); j++) {
                Ball b2 = balls.get(j);
                double r = Math.sqrt((b1.x - b2.x) * (b1.x - b2.x) + (b1.y - b2.y) * (b1.y - b2.y));
                if (r <= b1.r + b2.r) {
                    b1.lastid=b2.getID();
                    b2.lastid=b1.getID();
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
        for (Ball ball : balls) {
            for (block bat : blocks)
                if (ball.lastid != bat.getID() && ball.e2d.intersects(bat.x, bat.y, bat.l, bat.b)) {
                    ball.lastid=bat.getID();
                    //boolean top = ball.e2d.intersects(bat.x, bat.y, bat.l, 0.01);
                    //boolean bottom = ball.e2d.intersects(bat.x, bat.y + bat.b - 0.01, bat.l, 0.01);
                    //boolean left = ball.e2d.intersects(bat.x, bat.y, 0.01, bat.b);
                    //boolean right = ball.e2d.intersects(bat.x + bat.l - 0.01, bat.y, 0.01, bat.b);

                    boolean wx=ball.x>=bat.x&&ball.x<=bat.x+bat.l;
                    boolean wy=ball.y>=bat.y&&ball.y<=bat.y+bat.b;

                    //if ((top || bottom) && !(left || right)) {
                    if(wx&&!wy){
                        System.out.println("y");
                        ball.vy = -ball.vy;
                    } else if (wy&&!wx) {
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
                        /*
                        double rxu = (left?-1:1)*Math.sqrt(0.5);
                        double ryu = (top?-1:1)*Math.sqrt(0.5);
*/
                        double v1_along = ball.vx * rxu + ball.vy * ryu;
                        ball.vx -= 2 * v1_along * rxu;
                        ball.vy -= 2 * v1_along * ryu;
                        //ball.vx=-ball.vx;
                        //ball.vy=-ball.vy;
                    }

                }
        }

    }
}