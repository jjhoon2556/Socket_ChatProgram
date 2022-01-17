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
   
    //사용자들의 정보를 저장(MAP, ArrayList 사용)
     Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
     ArrayList<String> userList = new ArrayList<>();
    
    
    public final void setGui(ServerGUI gui) {
        this.gui = gui;
    }
 
    public void setting() throws IOException {
        serverSocket = new ServerSocket(5000);
        gui.appendMsg("서버 대기중...\n");
        while (true) {
        	// client 접속시까지 반복
        	socket = serverSocket.accept(); 
        	gui.appendMsg(socket.getInetAddress()+"에서 접속...\n");
            Receiver receiver = new Receiver(socket);
            receiver.start();
            
        }
    }
 
    public static void main(String[] args) throws IOException {
        Server Server = new Server();
        Server.setting();
    }
   
    // 맵의내용 저장과 삭제 메소드
    public void addClient(String nick, DataOutputStream out) throws IOException {
    	String msg="["+nick+"]" + " 님이 접속하셨습니다.\n";
    	sendMessage(msg);
        gui.appendMsg(msg);
        clientsMap.put(nick, out);
        userList.add(nick);
        userList();
    	}
    public void removeClient(String nick) throws IOException {
    	String msg="["+nick+"]" + " 접속종료...\n";
    	sendMessage(msg);
        gui.appendMsg(msg);
        clientsMap.remove(nick);
        userList.remove(nick);
        userList();
    }
    //userList 메소드
    public void userList()  {
    	sendMessage(("@list@\n"));
    	String list=String.join(",",userList);
    	sendMessage(list);
    }
    
    // 메시지 보내기 메소드
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
                	//접속종료시 out메세지
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


