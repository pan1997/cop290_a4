package cop290.a4.pingpong;

import java.util.ArrayList;

/**
 * Created by pankaj on 12/4/16.
 */
public class physics {
    ArrayList<Ball> balls;
    physics(){
        balls=new ArrayList<>();
    }
    void addBall(Ball b){
        balls.add(b);
    }
    void update(){
        for(Ball b:balls){
            b.updateSpirit();
            if(b.x+b.r>=b.parent().getB()||b.x<=b.r)
                b.vx=-b.vx;
            if(b.y+b.r>=b.parent().getL()||b.y<=b.r)
                b.vy=-b.vy;
        }
        for(int i=0;i<balls.size();i++){
            Ball b1=balls.get(i);
            for(int j=i+1;j<balls.size();j++){
                Ball b2=balls.get(j);
                double r=Math.sqrt((b1.x-b2.x)*(b1.x-b2.x)+(b1.y-b2.y)*(b1.y-b2.y));
                if(r<=b1.r+b2.r){
                    double rxu=(b1.x-b2.x)/r;
                    double ryu=(b1.y-b2.y)/r;
                    double v1_along=b1.vx*rxu+b1.vy*ryu;
                    double v2_along=b2.vx*rxu+b2.vy*ryu;
                    b1.vx+=(v2_along-v1_along)*rxu;
                    b1.vy+=(v2_along-v1_along)*ryu;
                    b2.vx+=(v1_along-v2_along)*rxu;
                    b2.vy+=(v1_along-v2_along)*ryu;
                }
            }
        }
    }
}
