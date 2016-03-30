package cop290.a4;

import cop290.a4.animation.animPanel;
import cop290.a4.animation.animPanelTest;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame main=new JFrame("TEST");
        animPanelTest animPanel=new animPanelTest(500,500,100,1);
        main.setContentPane(animPanel);
        animPanel.addMouseMotionListener(animPanel);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(600,700);
        main.setVisible(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                }
                animPanel.start();
            }
        }).start();
    }
}
