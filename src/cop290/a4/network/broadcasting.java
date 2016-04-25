package cop290.a4.network;

import cop290.a4.animation.Spirit;
import cop290.a4.pingpong.bat;
import cop290.a4.pingpong.block;
import cop290.a4.pingpong.circularObstacle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by pankaj on 23/4/16.
 */
public class broadcasting implements Runnable {
    int port;
    ServerSocket skt;
    ArrayList<Socket> sockets;
    ArrayList<Spirit> spirits;
    ArrayList<DataOutputStream> out;
    ArrayList<DataInputStream> in;
    String initMessage;
    public void setInitMessage(String s){
        initMessage=s;
    }
    public broadcasting(int p, ArrayList<Spirit> as) {
        //out=new DataOutputStream(o);
        port = p;
        new Thread(this).start();
        spirits = as;
        out = new ArrayList<>();
        sockets = new ArrayList<>();
        in=new ArrayList<>();
    }
    public void broadcast(String message){
        for (int i = 0; i < out.size(); i++) {
            DataOutputStream o = out.get(i);
            try {
                o.writeUTF(message);
                o.flush();
            } catch (Exception e) {
                out.remove(i);
                i--;
                System.out.println("Socket exception closed " + o);
            }
        }
    }
    public void broadcast() throws Exception {
        for (int i = 0; i < out.size(); i++) {
            DataOutputStream o = out.get(i);
            try {
                for (Spirit s : spirits)
                    if(!(s instanceof block)||(s instanceof bat)||!(s instanceof circularObstacle))
                    o.writeUTF("loc "+s.toString());
                o.flush();
            } catch (SocketException e) {
                out.remove(i);
                i--;
                System.out.println("Socket exception closed " + o);
            }
        }
    }

    public void setSpirits(ArrayList<Spirit> as) {
        spirits = as;
    }

    @Override
    protected void finalize() throws Throwable {
        sockets.forEach(e -> {
            try {
                e.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        });
        super.finalize();
    }

    @Override
    public void run() {
        try {
            skt = new ServerSocket(port);
            while (true) {
                Socket s;
                System.out.println("Waiting on" + skt.getLocalSocketAddress());
                s = skt.accept();
                System.out.println("Connected to " + s.getRemoteSocketAddress());
                DataOutputStream dout;
                out.add(dout = new DataOutputStream(s.getOutputStream()));
                dout.writeUTF("GREETINGS from server\n");
                for(Socket ss:sockets){
                    dout.writeUTF("Other_Users "+ss.getRemoteSocketAddress());
                }
                sockets.add(s);
                dout.writeUTF(initMessage);
                dout.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
