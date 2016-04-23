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
                    s.x=x;
                    s.y=y;
                }catch (Exception e){

                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}