package chatApp;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

 
public class ServerGUI extends JFrame implements ActionListener {
 
    TextArea ta = new TextArea(40, 25);
    TextField tf = new TextField(25);
    Server server = new Server();
    
    public ServerGUI() throws IOException {
 
    	add(ta, BorderLayout.CENTER);
        add(tf, BorderLayout.SOUTH);
        tf.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(200, 100, 300, 400);
        setTitle("Server");
 
        server.setGui(this);
        server.setting();
    }
 
    public static void main(String[] args) throws IOException {
        new ServerGUI();
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = "¼­¹ö : " + tf.getText() + "\n";
        System.out.print(msg);
        ta.append(msg);
        server.sendMessage(msg);
        tf.setText("");
    }
 
    public void appendMsg(String msg) {
        ta.append(msg);
    }
 
}


