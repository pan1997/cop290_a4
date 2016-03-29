package cop290.a4;

import cop290.a4.animation.animPanel;
import cop290.a4.animation.animPanelTest;

import javax.swing.*;
import javax.swing.tree.ExpandVetoException;

public class Main {

    public static void main(String[] args) {
        JFrame main=new JFrame("TEST");
        animPanel animPanel=new animPanelTest(500,500,60,1);
        main.setContentPane(animPanel);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(500,500);
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
