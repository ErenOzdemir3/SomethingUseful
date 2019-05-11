import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Client extends JFrame {

	private JTextField enterField;
	private JTextArea displayArea;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String chatServer;
	private Socket client;
		
	public Client(String host) {
		super("Client");
		
		chatServer = host;
		
		enterField = new JTextField();
		enterField.setEditable(false);
		enterField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendData(e.getActionCommand());
				enterField.setText("");
				
			}
		});
		
		add(enterField,BorderLayout.NORTH);
		
		displayArea = new JTextArea();
		add(new JScrollPane(displayArea),BorderLayout.CENTER);
		
		setSize(300,100);
		setVisible(true);
	}
	
	public void runClient() {
				
		
				try {
					connectToServer();
					getStreams();
					processConnection();
				}
				catch (EOFException e) {
					displayMessage("\nClient terminated connection");
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				finally {
					closeConnection();					
				}		
	}
	
	private void connectToServer() throws IOException{
		displayMessage("Attempting connection\n");
		
		client = new Socket(InetAddress.getByName(chatServer),12345);
		
		displayMessage("Connected to: " + client.getInetAddress().getHostName());
	}
	
	private void getStreams() throws IOException{
		output = new ObjectOutputStream(client.getOutputStream());
		output.flush();
		
		input = new ObjectInputStream(client.getInputStream());
		
		displayMessage("\nGot I/O streams\n");
	}
	
	private void processConnection() throws IOException{
		String message = "Connection successful";
		sendData(message);
		
		setTextFieldEditable(true);
		
		do {
			try {
				message = (String) input.readObject();
				displayMessage("\n" + message);
			} catch (ClassNotFoundException e) {
				displayMessage("\nUnknown object type received");
			}
		}while(!message.equals("SERVER>>> TERMINATE"));
	}
	
	private void closeConnection() {
		displayMessage("\nClosing connection\n");
		setTextFieldEditable(false);
		
		try {
			output.close();
			input.close();
			client.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendData(String message) {
		try {
			output.writeObject("CLIENT>>> " + message);
			output.flush();
			displayMessage("\nCLIENT>>> " + message);
		}
		catch (IOException e) {
			displayArea.append("\nError writing object");
		}
	}
	
	private void displayMessage(final String messageToDisplay) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				displayArea.append(messageToDisplay);				
			}
		});
	}
	
	private void setTextFieldEditable(final boolean editable) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				enterField.setEditable(editable);				
			}
		});
	}
}





