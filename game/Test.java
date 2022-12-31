package chess.game;

import javax.swing.JOptionPane;

public class Test {
	public static void main(String[] args) {
		Server server = new Server(3333);
		JOptionPane.showMessageDialog(null, "IP Address is "+server.getIPAddress());
		server.initialize();
		String tex =JOptionPane.showInputDialog("Enter Text to send!");
		//System.out.println(server.receiveString());
		
		
	server.sendString(tex);
	server.close();
	}
}
