package cop290.a4.pingpong;

import cop290.a4.animation.Spirit;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Created by pankaj on 23/4/16.
 */
public class Nphysics extends physics implements Runnable {
    DataInputStream din;
    int port;
    String serverAd;

    public Nphysics(int p, String s) {
        serverAd = s;
        port = p;
        new Thread(this).start();
    }

    @Override
    public void update() {
        blocks.forEach(e->e.rect.setRect(e.x,e.y,e.l,e.b));
        balls.forEach(e->e.e2d.setFrame(e.x-e.r,e.y-e.r,2*e.r,2*e.r));
    }

    @Override
    public void run() {
        try {
            Socket cl = new Socket(serverAd, port);
            System.out.println("Connected");
            din = new DataInputStream(cl.getInputStream());
            for (; ; ) {
                String cmd = din.readUTF();
                //System.out.println(cmd);
                int id;
                double x,y;
                try {
                    StringTokenizer st = new StringTokenizer(cmd);
                    id = Integer.parseInt(st.nextToken());
                    x=Double.parseDouble(st.nextToken());
                    y=Double.parseDouble(st.nextToken());
                    Spirit s=map.get(id);
                    if(s!=null) {
                        s.x = x;
                        s.y = y;
                    }else{
                        //System.out.println("id "+id);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}