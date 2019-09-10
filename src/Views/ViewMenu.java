package Views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewMenu {

	private JFrame frmMenu;
	 private JButton btnUtenti = new JButton("Utenti");
	 private JButton btnPubblicazioni = new JButton("Pubblicazioni");
	 private JButton btnStorico = new JButton("Storico");
	 private JButton btnRecensioni = new JButton("Recensioni");

	public ViewMenu() {
		initialize();
		frmMenu.setVisible(true);
	}

	private void initialize() {
		frmMenu = new JFrame();
		frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMenu.setTitle("Menu");
		frmMenu.setBounds(100, 100, 311, 174);
		SpringLayout springLayout = new SpringLayout();
		frmMenu.getContentPane().setLayout(springLayout);
		
		springLayout.putConstraint(SpringLayout.NORTH, btnUtenti, 26, SpringLayout.NORTH, frmMenu.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnUtenti, 27, SpringLayout.WEST, frmMenu.getContentPane());
		
		btnUtenti.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				ViewUtenze vwutenze = new ViewUtenze();
			}
		});
		frmMenu.getContentPane().add(btnUtenti);	
		btnPubblicazioni.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				ViewPubblicazioni vwpubb = new ViewPubblicazioni();
			}
		});
		
		frmMenu.getContentPane().add(btnPubblicazioni);

		springLayout.putConstraint(SpringLayout.NORTH, btnStorico, 26, SpringLayout.NORTH, frmMenu.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnStorico, 32, SpringLayout.EAST, btnUtenti);
		springLayout.putConstraint(SpringLayout.EAST, btnStorico, 0, SpringLayout.EAST, btnPubblicazioni);
		btnStorico.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				ViewStorico vwstorico = new ViewStorico();
			}
		});
		frmMenu.getContentPane().add(btnStorico);
		
		
		springLayout.putConstraint(SpringLayout.NORTH, btnRecensioni, 32, SpringLayout.SOUTH, btnUtenti);
		springLayout.putConstraint(SpringLayout.WEST, btnRecensioni, 27, SpringLayout.WEST, frmMenu.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnUtenti, 0, SpringLayout.EAST, btnRecensioni);
		springLayout.putConstraint(SpringLayout.WEST, btnPubblicazioni, 32, SpringLayout.EAST, btnRecensioni);
		springLayout.putConstraint(SpringLayout.NORTH, btnPubblicazioni, 0, SpringLayout.NORTH, btnRecensioni);
		btnRecensioni.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				ViewRecensioni vwrecensioni = new ViewRecensioni();
			}
		});
		frmMenu.getContentPane().add(btnRecensioni);
	}

}
