package cop290.a4.pingpong;

import java.io.DataInputStream;
import java.net.Socket;

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
                System.out.println(cmd);
            }
        } catch (Exception e) {
        }
    }
}