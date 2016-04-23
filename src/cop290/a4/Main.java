package cop290.a4;

import cop290.a4.pingpong.board;
import cop290.a4.pingpong.physics;


import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        JFrame main = new JFrame("Game");
        //animPanelTest animPanel = new animPanelTest(500, 500, 100, 1);
        board animPanel = new board(600, 600, 100, 2,new physics());
        main.setContentPane(animPanel);
        main.addKeyListener(animPanel);
        //animPanel.addMouseMotionListener(animPanel);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(600, 700);
        main.setVisible(true);
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
                        animPanel.start();
                        break;
                    case "stop":
                        animPanel.stop();
                        break;
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