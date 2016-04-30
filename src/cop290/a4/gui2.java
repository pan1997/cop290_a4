package cop290.a4;

/**
 * Created by kritarth on 30/4/16.
 */

import cop290.a4.network.broadcasting;
import cop290.a4.pingpong.Nphysics;
import cop290.a4.pingpong.board;
import cop290.a4.pingpong.physics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import cop290.a4.network.broadcasting;
import cop290.a4.pingpong.Nphysics;
import cop290.a4.pingpong.Sounds;
import cop290.a4.pingpong.board;
import cop290.a4.pingpong.physics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class gui2 extends JDialog {


    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JSlider slider1;
    private JSlider slider2;
    private JSlider slider3;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;

    ButtonGroup group = new ButtonGroup();
    static board animPanel = null;
    static Sounds sounds = new Sounds();
    public static broadcasting bds = new broadcasting(1234, null);

    public gui2() {
        sounds.initSound();
        group.add(radioButton1);
        group.add(radioButton2);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (radioButton1.isSelected()) {
                    //System.out.println("lol" + buttonOK.getName());
                    buttonOK.setText("START");
                    buttonCancel.setText("CANCEL");
                }
            }
        });

        radioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (radioButton2.isSelected()) {
                    //System.out.println("lol" + buttonOK.getName());
                    buttonOK.setText("HOST");
                    buttonCancel.setText("JOIN");
                }
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }


    private void onOK() {
// add your code here
        if (radioButton1.isSelected()) {
            broadcasting bds = new broadcasting(1234, null);
            int stage = slider2.getValue() / 20;
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
            animPanel.start();
        } else {
            broadcasting bds = new broadcasting(1234, null);
            int stage = slider2.getValue() / 20;
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

            start();
        }

        {
            JFrame terminal = new JFrame("terminal");
            JTextArea jt = new JTextArea();
            jt.append("Legal commands:\nstart\nstop\nrestart\nexit\n");
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
                        JFrame.getFrames()[1].toFront();
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
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        animPanel.start();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

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

        dispose();
    }


    private void onCancel() {
// add your code here if necessary
        if (radioButton1.isSelected())
            dispose();
        else {
            if (textField2.getText().length() == 0) System.exit(0);
            //broadcasting bds = new broadcasting(1234, null);
            Sounds sounds = new Sounds();
            if (animPanel == null) {
                JFrame main3 = new JFrame("Game");
                animPanel = new board(600, 600, 100, 2, new Nphysics(Integer.parseInt(textField2.getText().split(":")[1]), textField2.getText().split(":")[0]), null, 0);
                main3.setContentPane(animPanel);
                main3.addKeyListener(animPanel);
                main3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                main3.setSize(600, 700);
                main3.setVisible(true);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        animPanel.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        {
            {
                JFrame terminal = new JFrame("terminal");
                JTextArea jt = new JTextArea();
                jt.append("Legal commands:\nstart\nstop\nrestart\nexit\n");
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
                            JFrame.getFrames()[1].toFront();
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
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);
                                            animPanel.start();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

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

        dispose();
    }

    private void start() {
        //ATimerExample ate = new ATimerExample();
        //ate.timer.start();
        long start = new Date().getTime();
        while (new Date().getTime() - start < 10000L) {

        }
        animPanel.start();
    }

    private void exit() {
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        gui2 dialog = new gui2();
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField2 = new JTextField();
        panel1.add(textField2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("IP");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Name");
        panel3.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("AI");
        panel3.add(label3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Game Stage");
        panel3.add(label4, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Lives");
        panel3.add(label5, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Mode");
        panel3.add(label6, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        panel3.add(textField1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        slider1 = new JSlider();
        panel3.add(slider1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        slider2 = new JSlider();
        panel3.add(slider2, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        slider3 = new JSlider();
        panel3.add(slider3, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButton1 = new JRadioButton();
        radioButton1.setText("SinglePlayer");
        panel3.add(radioButton1, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButton2 = new JRadioButton();
        radioButton2.setText("MultiPlayer");
        panel3.add(radioButton2, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}

