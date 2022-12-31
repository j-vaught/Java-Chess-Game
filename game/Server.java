package chess.game;

import java.io.*;
import java.net.*;


public class Server
{

	InetAddress iPAddress;
	Socket socket;
	ServerSocket serverSocket;  
	
	public Server(int port) {
		try {
			serverSocket=new ServerSocket(port);
		}catch (IOException e) {}
	}

	public String getIPAddress() {
		try {
			iPAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {}
	      return iPAddress.getHostAddress();
	}
	
	public boolean initialize() {
		try {
			socket =serverSocket.accept();
			return true;
		} catch (IOException e) {}
		return false;
	}
	
	public boolean close() {
		try {
			serverSocket.close();
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