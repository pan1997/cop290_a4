package cop290.a4.pingpong;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import cop290.a4.animation.Spirit;

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

    void broadcast(String message) {
        try {
            dout.writeUTF(message);
            dout.flush();
            //Main.bds.broadcast(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    long last = 0;

    void update() {
        long current = System.nanoTime();
        double dt = last != 0 ? (current - last) / 1000000000.0 : 0;
        last = current;
        blocks.forEach(e -> e.updateSpirit(dt));
        balls.forEach(e -> e.e2d.setFrame(e.x - e.r, e.y - e.r, 2 * e.r, 2 * e.r));
    }

    Socket cl;

    @Override
    public void run() {
        try {
            if (cl == null)
                cl = new Socket(serverAd, port);
            System.out.println("Connected");
            din = new DataInputStream(cl.getInputStream());
            dout = new DataOutputStream(cl.getOutputStream());
            cl = null;
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
                        } else if (type.equals("bat")) {
                            board bd = (board) (balls.get(0).parent());
                            int xx = Integer.parseInt(st.nextToken());
                            if (xx == bd.userId) {
                                //System.err.println("Error");
                            } else
                                bd.bats.get(xx).loc = Double.parseDouble(st.nextToken());
                            //bd.setVel(xx,Double.parseDouble(st.nextToken()));
                        } else if (type.equals("userId")) {
                            board bd = (board) (balls.get(0).parent());
                            bd.userId = Integer.parseInt(st.nextToken());
                            bd.rot = bd.userId;
                        } else if (type.equals("Other_Users")) {
                            /*System.out.println(cmd);
                            serverAd = st.nextToken();
                            StringTokenizer t = new StringTokenizer(serverAd, ":");
                            serverAd = t.nextToken();
                            serverAd = serverAd.substring(1);
                            port = Integer.parseInt(t.nextToken());
                            System.out.println(serverAd + " " + port);
                            new Thread(this).start();
                            */
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