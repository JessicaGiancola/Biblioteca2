package Views;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controllers.ControllerLogin;
import Controllers.ControllerNuovaRecensione;
import Controllers.FrontController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewNuovaRecensione {

	private JFrame frame;
	private JComboBox comboBox;
	private JButton btnInserisci = new JButton("Inserisci Recensione");
	private JTextArea textArea = new JTextArea();
	private FrontController cnr = new ControllerNuovaRecensione();
	private String query = "SELECT DISTINCT pubblicazione.ISBN from pubblicazione LEFT JOIN recensioni on pubblicazione.ISBN=recensioni.ISBN WHERE  pubblicazione.ISBN NOt IN (SELECT ISBN FROM utenti INNER JOIN recensioni ON utenti.ID=recensioni.ID_Utente WHERE recensioni.ID_Utente="+ControllerLogin.getUser().getID()+");";


	public ViewNuovaRecensione() {
		initialize();
		frame.setVisible(true);
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		comboBox = new JComboBox(cnr.GetData(query).toArray());
		comboBox.setBounds(60, 12, 160, 20);
		frame.getContentPane().add(comboBox);
		btnInserisci.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String testo = textArea.getText();
				
				if(testo.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Non puoi inserire una recensione vuota", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					Object [] nuovarec = new Object [] {testo, comboBox.getSelectedItem(),ControllerLogin.getUser().getID()};
					
					boolean insert = cnr.SetData(nuovarec);
					
					if(insert)
					{
						 JOptionPane.showMessageDialog(null, "Recensione inserita", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
						 textArea.setText("");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Inserimento fallito", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		btnInserisci.setBounds(264, 11, 160, 23);
		frame.getContentPane().add(btnInserisci);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 414, 200);
		frame.getContentPane().add(scrollPane);
		
		scrollPane.setViewportView(textArea);
		
		JLabel lblNewLabel = new JLabel("ISBN");
		lblNewLabel.setBounds(10, 17, 86, 14);
		frame.getContentPane().add(lblNewLabel);
	}

}
