import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ServerUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String defaultUser = "root";
	private final String defaultPass = "1234";
	private final int defaultPort = 3333;
	
	private JTextField textUser;
	private JTextField textPass;
	private JButton btnPutOnline;
	private JButton btnPutOffline;

	ServerIMP server;
	private JLabel lblPort;
	private JTextField textPort;
	
	private JTextArea console;
	/**
	 * Create the application.
	 */
	public ServerUI(ServerIMP s) {
		server = s;
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 414, 240);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DB user name:");
		lblNewLabel.setBounds(10, 14, 115, 14);
		panel.add(lblNewLabel);
		
		JLabel lblDbPassword = new JLabel("DB password:");
		lblDbPassword.setBounds(10, 39, 72, 14);
		panel.add(lblDbPassword);
		
		textUser = new JTextField(defaultUser);
		textUser.setBounds(110, 11, 179, 20);
		panel.add(textUser);
		textUser.setColumns(10);
		
		textPass = new JTextField(defaultPass);
		textPass.setBounds(110, 39, 179, 20);
		panel.add(textPass);
		textPass.setColumns(10);
		
		btnPutOnline = new JButton("put online");
		btnPutOnline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.startServer(Integer.parseInt(textPort.getText()), textUser.getText(), textPass.getText());
			}
		});
		btnPutOnline.setBounds(10, 97, 170, 20);
		panel.add(btnPutOnline);
		
		btnPutOffline = new JButton("put offline");
		btnPutOffline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.stopServer();
			}
		});
		btnPutOffline.setBounds(234, 96, 170, 23);
		panel.add(btnPutOffline);
		
		console = new JTextArea();
		console.setBounds(10, 128, 394, 101);
		panel.add(console);
		
		lblPort = new JLabel("port:");
		lblPort.setBounds(10, 64, 46, 14);
		panel.add(lblPort);
		
		textPort = new JTextField(defaultPort + "");
		textPort.setBounds(110, 66, 179, 20);
		panel.add(textPort);
		textPort.setColumns(10);
	}
	
	public void writeToConsole(String s) {
		console.append(s + "\n");
	}
}
