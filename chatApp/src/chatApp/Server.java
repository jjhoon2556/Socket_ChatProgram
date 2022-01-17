package chatApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

 
public class Server {
 
	static ServerSocket serverSocket;
    static Socket socket;
    static ServerGUI gui;
    static String msg;
   
    //����ڵ��� ������ ����(MAP, ArrayList ���)
     Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
     ArrayList<String> userList = new ArrayList<>();
    
    
    public final void setGui(ServerGUI gui) {
        this.gui = gui;
    }
 
    public void setting() throws IOException {
        serverSocket = new ServerSocket(5000);
        gui.appendMsg("���� �����...\n");
        while (true) {
        	// client ���ӽñ��� �ݺ�
        	socket = serverSocket.accept(); 
        	gui.appendMsg(socket.getInetAddress()+"���� ����...\n");
            Receiver receiver = new Receiver(socket);
            receiver.start();
            
        }
    }
 
    public static void main(String[] args) throws IOException {
        Server Server = new Server();
        Server.setting();
    }
   
    // ���ǳ��� ����� ���� �޼ҵ�
    public void addClient(String nick, DataOutputStream out) throws IOException {
    	String msg="["+nick+"]" + " ���� �����ϼ̽��ϴ�.\n";
    	sendMessage(msg);
        gui.appendMsg(msg);
        clientsMap.put(nick, out);
        userList.add(nick);
        userList();
    	}
    public void removeClient(String nick) throws IOException {
    	String msg="["+nick+"]" + " ��������...\n";
    	sendMessage(msg);
        gui.appendMsg(msg);
        clientsMap.remove(nick);
        userList.remove(nick);
        userList();
    }
    //userList �޼ҵ�
    public void userList()  {
    	sendMessage(("@list@\n"));
    	String list=String.join(",",userList);
    	sendMessage(list);
    }
    
    // �޽��� ������ �޼ҵ�
    public void sendMessage(String msg) {
        Iterator<String> it = clientsMap.keySet().iterator();
        String key = "";
        while (it.hasNext()) {
            key = it.next();
            try {
                clientsMap.get(key).writeUTF(msg);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Receiver extends Thread {
        DataInputStream in;
        DataOutputStream out;
        String nick;
		private OutputStream os;
		private InputStream is;
        
        public Receiver(Socket socket) throws IOException {
        	os=socket.getOutputStream();
        	is=socket.getInputStream();
        	out = new DataOutputStream(os);
            in = new DataInputStream(is);
            nick = in.readUTF();
            addClient(nick, out);
        }
        public void run() {
            try {
                while (in != null) {
                	msg = in.readUTF();
                	//��������� out�޼���
                    if(msg.equals("exit")){
                    	removeClient(nick); 
                    	break;}
                    else {
                    	  sendMessage(msg);
                          gui.appendMsg(msg);	
                    }
                }
            } catch (IOException e) {
                try {
					removeClient(nick);
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
            }
        }
    }
}


