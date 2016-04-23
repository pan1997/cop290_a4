package cop290.a4;

import cop290.a4.animation.animPanel;
import cop290.a4.network.broadcasting;
import cop290.a4.pingpong.Nphysics;
import cop290.a4.pingpong.board;
import cop290.a4.pingpong.physics;


import javax.swing.*;
import java.awt.*;
import java.util.IntSummaryStatistics;
import java.util.StringTokenizer;

public class Main {
    static board animPanel=null;
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        {
            JFrame terminal = new JFrame("terminal");
            JTextArea jt = new JTextArea();
            jt.append("Legal commands:\nstart\nstop\nrestart\nexit\n\n\n");
            jt.setEditable(false);
            JTextField jtf = new JTextField();
            jtf.addActionListener(e -> {
                jt.append(e.getActionCommand() + "\n");
                switch (e.getActionCommand()){
                    case "exit":
                        System.exit(0);
                        break;
                    case "start":
                        if(animPanel==null){
                            JFrame main = new JFrame("Game");
                            animPanel=new board(600, 600, 100, 2,new physics(),new broadcasting(1245,null));
                            main.setContentPane(animPanel);
                            main.addKeyListener(animPanel);
                            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            main.setSize(600, 700);
                            main.setVisible(true);
                        }
                        animPanel.start();
                        break;
                    case "rot":
                        animPanel.rot++;
                        break;
                    case "stop":
                        animPanel.stop();
                        break;
                    default:{
                        StringTokenizer st=new StringTokenizer(e.getActionCommand());
                        if(st.nextToken().equals("connect")){
                            String add=st.nextToken();
                            int port= Integer.parseInt(st.nextToken());
                            if(animPanel==null){
                                JFrame main = new JFrame("Game");
                                animPanel=new board(600, 600, 100, 2,new Nphysics(port,add),null);
                                main.setContentPane(animPanel);
                                main.addKeyListener(animPanel);
                                main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                main.setSize(600, 700);
                                main.setVisible(true);
                            }
                            animPanel.start();
                        }
                    }
                }
                if (e.getActionCommand().equalsIgnoreCase("exit"))
                    System.exit(0);
                jtf.setText("");
            });
            terminal.add(new JScrollPane(jt), BorderLayout.CENTER);
            terminal.add(jtf, BorderLayout.SOUTH);
            terminal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            terminal.setSize(600, 200);
            //terminal.pack();
            terminal.setLocationRelativeTo(null);
            terminal.setVisible(true);



        }
    }

}