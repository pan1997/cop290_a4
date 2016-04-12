package cop290.a4.terminal;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pankaj on 12/4/16.
 */
public class terminal{
    JTextArea display;
    JTextField input;
    public terminal(JTextArea txt,JTextField in){
        display=txt;
        input=in;
    }
    public static void main(String argv[]){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){}
        JFrame jf=new JFrame("tst");
        JTextArea jt=new JTextArea();
        JTextField jtf=new JTextField();
        jtf.addActionListener(e -> {
            jt.append(e.getActionCommand()+"\n");
            jtf.setText("");
        });
        jf.add(new JScrollPane(jt),BorderLayout.CENTER);
        jf.add(jtf,BorderLayout.SOUTH);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(500,500);
        jf.setVisible(true);
    }
}
