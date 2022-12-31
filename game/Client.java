package chess.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	InetAddress iPAddress;
	Socket socket;

	public Client(String IPAddress, int port) {
		try {
			iPAddress = InetAddress.getByName(IPAddress);
			socket = new Socket(iPAddress, port);
		} catch (Exception e) {}
	}

	public boolean close() {
		try {
			socket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public String receiveString() {
		String returnString = null;
		do {
			try{
				DataInputStream din=new DataInputStream(socket.getInputStream());   
				returnString = din.readUTF();   
			}catch(Exception e){
				returnString =null;
			}
		}while(returnString==null);
		return returnString;
	}
	
	public boolean sendString(String input) {
		boolean complete=false;
		do {
			try{
				DataOutputStream dout=new DataOutputStream(socket.getOutputStream()); 
				dout.writeUTF(input);  
				complete=true;
			}catch(Exception e){
				complete=false;
			}
		}while(!complete);
		return true;
	}
}
