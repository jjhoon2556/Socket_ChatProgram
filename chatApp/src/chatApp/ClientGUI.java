package chatApp;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;




public class ClientGUI extends JFrame implements ActionListener {
	 
    TextArea ta = new TextArea();
    JTextField tf = new JTextField(10);
    static JTextField txtId = new JTextField();
    JTextArea nameList = new JTextArea();
    Client client = new Client();
    static Button btn;
    static Button btn2;
    static String nickName;
   
    public ClientGUI() {
    	//Frame layout
    	setLayout( null );
    	JLabel label=new JLabel("��ȭ�� :   ");
    	label.setBounds(80, 10, 100, 30);
    	add(label);
    	//text field
    	txtId.setBounds(150, 10,150,30);
    	add(txtId);
    	txtId.setEditable(false);
    	tf.setBounds(10,325,190,30);
    	add(tf);
    	//text area
    	ta.setBounds(10,50,340,270);
    	ta.setBackground(Color.CYAN);
    	ta.setEditable(false);
    	add(ta);
    	//������ ��ư �߰�
    	btn=new Button("������");
    	btn.setBounds(207,325,70,30);
    	add(btn);
    	btn2=new Button("��������");
    	btn2.setBounds(285,325,70,30);
    	add(btn2);
    	//������ list
    	JLabel label2=new JLabel("<<Member>>");
    	label2.setBounds(370,20, 100, 30);
    	add(label2);
    	add(nameList);
    	nameList.setEditable(false);
    	nameList.setBounds(360,50,100,270);
    	//�׼Ǹ�����
        tf.addActionListener(this);
        btn.addActionListener(this);
        btn2.addActionListener(this);
        
        //�����ư
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //frame set
        setVisible(true);
        setBounds(900, 100, 490, 400);
        setTitle("Multi Chatting App");
        
        client.setGui(this);
        client.setNickname(nickName);
        client.connet();
    }
 //////////////////////////////////////////////////////////////
    public static void main(String[] args) {
		nickName = JOptionPane.showInputDialog("��ȭ�� �Է�");
		txtId.setText(nickName);
        new ClientGUI();
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btn) {
        	String msg = "[ "+nickName+" ]" + " : " + tf.getText() + "\n";
            client.sendMessage(msg);
            tf.setText("");
        }
        else if(e.getSource()==btn2) {
        	String msg = "exit";
            client.sendMessage(msg);
        } 
        else {
        	String msg = "[ "+nickName+" ]" + " : " + tf.getText() + "\n";
            client.sendMessage(msg);
            tf.setText("");
        }
    	
    }
   public void appendMsg(String msg) {
        ta.append(msg);
    }

   

   
}



