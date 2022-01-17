package chatApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
 
public class Client {
 
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    InputStream is;
    OutputStream os;
    ClientGUI gui;
    String msg;
    String nickName;
    String list;
    
 
    public final void setGui(ClientGUI gui) {
        this.gui = gui;
    }
 
    public void connet() {
        try {
            socket = new Socket("0.0.0.0", 5000);
            gui.appendMsg("���� ����Ǿ����ϴ�.\n");
            is = socket.getInputStream();
			os = socket.getOutputStream();
			in = new DataInputStream(is);
            out = new DataOutputStream(os);
            //�г��� ������ ����
            out.writeUTF(nickName);
            
            //���ù�
            while (in != null) {
            	msg = in.readUTF();
            	//user list �߰�
            	if(msg.equals("@list@\n")) {
            		gui.nameList.setText("");
            		list = in.readUTF();
            		String[] arr=list.split(",");
            		for(int i=0; i<arr.length; i++) {
            		gui.nameList.append(arr[i]);
            		gui.nameList.append("\n");
            		}
            	}
            	else {
            		gui.appendMsg(msg);	
        		}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
			try {
				out.close();
				in.close();
				in.close();
				os.close();
				is.close();
				socket.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
 
    public static void main(String[] args) {
        Client Client = new Client();
        Client.connet();
    }
 
    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void setNickname(String nickName) {
        this.nickName = nickName;
    }

}


