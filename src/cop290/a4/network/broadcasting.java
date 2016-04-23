package cop290.a4.network;

import cop290.a4.animation.Spirit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by pankaj on 23/4/16.
 */
public class broadcasting implements Runnable{
    int port;
    ArrayList<Spirit> spirits;
    DataOutputStream out;
    public broadcasting(int p,ArrayList<Spirit> as){
        //out=new DataOutputStream(o);
        port=p;
        new Thread(this).start();
        spirits=as;
    }
    public void broadcast() throws Exception {
        if (out != null)
            for (Spirit s : spirits)
                out.writeUTF(s.toString());
    }

    @Override
    public void run() {
        try {
            ServerSocket skt=new ServerSocket(port);
            System.out.println("Waiting on" + skt.getLocalSocketAddress());
            Socket s = skt.accept();
            System.out.println("Connected to "+s.getRemoteSocketAddress());
            out=new DataOutputStream(s.getOutputStream());
            out.writeUTF("Connected\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}