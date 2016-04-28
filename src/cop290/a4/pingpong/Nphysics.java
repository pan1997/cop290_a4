package cop290.a4.pingpong;

import cop290.a4.animation.Spirit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Created by pankaj on 23/4/16.
 */
public class Nphysics extends physics implements Runnable {
    DataInputStream din;
    DataOutputStream dout;
    int port;
    String serverAd;

    public Nphysics(int p, String s) {
        serverAd = s;
        port = p;
        new Thread(this).start();
    }
    void broadcast(String message){
        try {
            dout.writeUTF(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void update() {
        blocks.forEach(e -> e.rect.setRect(e.x, e.y, e.l, e.b));
        balls.forEach(e -> e.e2d.setFrame(e.x - e.r, e.y - e.r, 2 * e.r, 2 * e.r));
    }

    @Override
    public void run() {
        try {
            Socket cl = new Socket(serverAd, port);
            System.out.println("Connected");
            din = new DataInputStream(cl.getInputStream());
            dout=new DataOutputStream(cl.getOutputStream());
            for (; ; ) {
                String cmd = din.readUTF();
                //System.out.println(cmd);
                int id;
                double x, y;
                try {
                    StringTokenizer st = new StringTokenizer(cmd);
                    String type = st.nextToken();
                    if (type.equals("loc")) {
                        id = Integer.parseInt(st.nextToken());
                        x = Double.parseDouble(st.nextToken());
                        y = Double.parseDouble(st.nextToken());
                        Spirit s = map.get(id);
                        if (s != null) {
                            s.x = x;
                            s.y = y;
                        } else {
                            //System.out.println("id "+id);
                        }
                    } else {
                        if (type.equals("life")) {
                            board bd = (board) (balls.get(0).parent());
                            bd.lives[0] = Integer.parseInt(st.nextToken());
                            bd.lives[1] = Integer.parseInt(st.nextToken());
                            bd.lives[2] = Integer.parseInt(st.nextToken());
                            bd.lives[3] = Integer.parseInt(st.nextToken());
                        } else if (type.equals("stage")) {
                            System.out.println("Stage message");
                            board bd = (board) (balls.get(0).parent());
                            bd.setStage(Integer.parseInt(st.nextToken()));
                        } else if (type.equals("bats")) {
                            board bd = (board) (balls.get(0).parent());
                            bd.bats.get(0).loc=Double.parseDouble(st.nextToken());
                            bd.bats.get(1).loc=Double.parseDouble(st.nextToken());
                            bd.bats.get(2).loc=Double.parseDouble(st.nextToken());
                            bd.bats.get(3).loc=Double.parseDouble(st.nextToken());
                            for(bat bt:bd.bats)
                                bt.updateSpirit(0);
                        } else if(type.equals("userId")){
                            board bd = (board) (balls.get(0).parent());
                            bd.userId=Integer.parseInt(st.nextToken());
                            bd.rot=bd.userId;
                        }
                        //System.out.println(cmd+"---"+type);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}