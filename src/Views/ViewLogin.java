package Views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import Controllers.ControllerLogin;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewLogin {

	private JFrame frmLogin;
	private JPasswordField password;
	private JButton btnLogin = new JButton("Login");
	private JTextField nomeutente;

	public ViewLogin() {
		initialize();
		frmLogin.setVisible(true);
	}

	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 311, 207);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.WEST, btnLogin, 95, SpringLayout.WEST, frmLogin.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnLogin, -13, SpringLayout.SOUTH, frmLogin.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnLogin, -114, SpringLayout.EAST, frmLogin.getContentPane());
		frmLogin.getContentPane().setLayout(springLayout);
		
		nomeutente = new JTextField();
		nomeutente.setText("lisa.rossi@gmail.com");
		springLayout.putConstraint(SpringLayout.NORTH, nomeutente, 30, SpringLayout.NORTH, frmLogin.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, nomeutente, 72, SpringLayout.WEST, frmLogin.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, nomeutente, -66, SpringLayout.EAST, frmLogin.getContentPane());
		frmLogin.getContentPane().add(nomeutente);
		nomeutente.setColumns(10);
		
		password = new JPasswordField();
		password.setText("abcd");
		springLayout.putConstraint(SpringLayout.NORTH, password, 86, SpringLayout.NORTH, frmLogin.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnLogin, 6, SpringLayout.SOUTH, password);
		springLayout.putConstraint(SpringLayout.EAST, password, 0, SpringLayout.EAST, nomeutente);
		springLayout.putConstraint(SpringLayout.WEST, password, 0, SpringLayout.WEST, nomeutente);
		frmLogin.getContentPane().add(password);
		password.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Email");
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, nomeutente);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -6, SpringLayout.NORTH, nomeutente);
		frmLogin.getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		springLayout.putConstraint(SpringLayout.WEST, lblPassword, 0, SpringLayout.WEST, nomeutente);
		springLayout.putConstraint(SpringLayout.SOUTH, lblPassword, -6, SpringLayout.NORTH, password);
		springLayout.putConstraint(SpringLayout.EAST, lblPassword, -137, SpringLayout.EAST, frmLogin.getContentPane());
		frmLogin.getContentPane().add(lblPassword);
		
		
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				ControllerLogin c = new ControllerLogin(nomeutente.getText(),password.getPassword());
				int autenticazione = c.Login();
				
				switch(autenticazione)
				{
					case 0:
						JOptionPane.showMessageDialog(null, "Autenticazione fallita,utente non trovato", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
						break;
					case 1:
						JOptionPane.showMessageDialog(null, "Autenticazione riuscita", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
						ViewMenu vwmenu = new ViewMenu();
						frmLogin.setVisible(false);
						break;
					case -1: JOptionPane.showMessageDialog(null, "Autenticazione fallita,password errata", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
						break;
					case -2: JOptionPane.showMessageDialog(null, "Autenticazione fallita,connessione non riuscita", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
						break;
						
				}
				
			}
		});
		
		frmLogin.getContentPane().add(btnLogin);		
	}

}
