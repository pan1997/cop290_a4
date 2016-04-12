package cop290.a4;

import cop290.a4.animation.animPanel;
import cop290.a4.animation.animPanelTest;
import cop290.a4.pingpong.board;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        JFrame main = new JFrame("Game");
        {
            JFrame terminal = new JFrame("terminal");
            JTextArea jt = new JTextArea();
            JTextField jtf = new JTextField();
            jtf.addActionListener(e -> {
                jt.append(e.getActionCommand() + "\n");
                if (e.getActionCommand().equalsIgnoreCase("exit"))
                    System.exit(0);
                jtf.setText("");
            });
            terminal.add(new JScrollPane(jt), BorderLayout.CENTER);
            terminal.add(jtf, BorderLayout.SOUTH);
            terminal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            terminal.setSize(600, 200);
            terminal.setVisible(true);
        }
        //animPanelTest animPanel = new animPanelTest(500, 500, 100, 1);
        animPanel animPanel = new board(500, 500, 100, 1);
        main.setContentPane(animPanel);
        //animPanel.addMouseMotionListener(animPanel);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(600, 700);
        main.setVisible(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                animPanel.start();
            }
        }).start();
    }
}