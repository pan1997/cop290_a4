package cop290.a4;

import cop290.a4.animation.animPanel;
import cop290.a4.network.broadcasting;
import cop290.a4.pingpong.Nphysics;
import cop290.a4.pingpong.board;
import cop290.a4.pingpong.physics;


import javax.swing.*;
import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.IntSummaryStatistics;
import java.util.StringTokenizer;

public class Main {
    static board animPanel = null;
    static broadcasting bds = new broadcasting(1245, null);

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
                int stage = 1;
                jt.append(e.getActionCommand() + "\n");
                switch (e.getActionCommand().split(" ")[0]) {
                    case "exit":
                        System.exit(0);
                        break;
                    case "host":
                        try {
                            stage = Integer.parseInt(e.getActionCommand().split(" ")[1]);
                        } catch (Exception ew) {
                            stage = (int) (Math.random() * 4 + 1);
                        }
                        if (animPanel != null) {
                            for (int i = 1; i < JFrame.getFrames().length; i++) {
                                JFrame.getFrames()[i].dispose();
                            }
                        }
                        JFrame main = new JFrame("Game");
                        animPanel = new board(600, 600, 100, 2, new physics(), bds, stage);
                        main.setContentPane(animPanel);
                        main.addKeyListener(animPanel);
                        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        main.setSize(600, 700);
                        main.setVisible(true);
                        animPanel.singleFrame();
                        break;
                    case "start":
                        animPanel.start();
                        break;
                    case "rot":
                        animPanel.rot++;
                        break;
                    case "stop":
                        animPanel.stop();
                        break;
                    case "restart":
                        System.out.println("Restart");
                        if (animPanel != null) {
                            for (int i = 1; i < JFrame.getFrames().length; i++) {
                                JFrame.getFrames()[i].dispose();
                            }
                        }
                        JFrame main2 = new JFrame("Game");
                        animPanel = new board(600, 600, 100, 2, new physics(), bds, stage);
                        main2.setContentPane(animPanel);
                        main2.addKeyListener(animPanel);
                        main2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        main2.setSize(600, 700);
                        main2.setVisible(true);

                        animPanel.start();
                        break;
                    default: {
                        StringTokenizer st = new StringTokenizer(e.getActionCommand());
                        if (st.nextToken().equals("connect")) {
                            String add = st.nextToken();
                            int port = Integer.parseInt(st.nextToken());
                            if (animPanel == null) {
                                JFrame main3 = new JFrame("Game");
                                animPanel = new board(600, 600, 100, 2, new Nphysics(port, add), null, 0);
                                main3.setContentPane(animPanel);
                                main3.addKeyListener(animPanel);
                                main3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                main3.setSize(600, 700);
                                main3.setVisible(true);
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