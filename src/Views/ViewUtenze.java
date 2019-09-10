package Views;

import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controllers.ControllerUtenze;
import Controllers.FrontController;
import Models.Utente;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewUtenze {

	private JFrame frameUtenze;
	private JTable table;
	private String[] columnname = {"ID","Nome","Cognome","Tipo"};
	private FrontController cu = new ControllerUtenze();
	private String query = "select ID,Nome,Cognome,Tipo from utenti";
	private JScrollPane scrollPane = new JScrollPane();

	public ViewUtenze() {
		initialize();
		frameUtenze.setVisible(true);
	}

	private void initialize() {
		frameUtenze = new JFrame();
		frameUtenze.setBounds(100, 100, 700, 400);
		frameUtenze.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameUtenze.getContentPane().setLayout(null);
		
		
		scrollPane.setBounds(10, 76, 664, 274);
		
		CreateTable();
		
		JCheckBox chckbxelencoattivi = new JCheckBox("Elenco utenti pi\u00F9 attivi");
		
		chckbxelencoattivi.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(chckbxelencoattivi.isSelected())
				{
					query = "SELECT DISTINCT utenti.ID,Nome,Cognome,DataNascita,email,Tipo FROM utenti INNER JOIN storico on storico.ID_Utente=utenti.ID WHERE storico.Operazione='Inserimento' ORDER BY storico.DataOra";
					CreateTable();
				}
				else
				{
					query = "select ID,Nome,Cognome,Tipo from utenti";
					CreateTable();
				}
			}
		});
		chckbxelencoattivi.setBounds(10, 7, 177, 23);
		frameUtenze.getContentPane().add(chckbxelencoattivi);
		
		JButton btnModtipo = new JButton("Modifica tipo");
		btnModtipo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int row = table.getSelectedRow();
				
				if (row>=0)
				{
					int idselected = (int) table.getModel().getValueAt(row, 0);
					String tipoutente = (String) table.getModel().getValueAt(row, 3);
					int tipodb;
					if(tipoutente.equals("Passivo"))
					{
						tipoutente="Attivo";
						tipodb=1;
					}
					else
					{
						tipoutente="Passivo";
						tipodb=0;
					}
					
					Object[] filtro = new Object[]{tipodb,idselected};
					
					boolean modifica = cu.SetData(filtro);
					
					if(modifica)
					{
						DefaultTableModel model = (DefaultTableModel)table.getModel();
						model.setValueAt(tipoutente, row, 3);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Modifica fallita", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
					}
					
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Devi selezionare prima una riga", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnModtipo.setBounds(236, 7, 177, 23);
		frameUtenze.getContentPane().add(btnModtipo);
		
		JButton btnElencopubb = new JButton("Elenco pubblicazioni");
		btnElencopubb.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int row = table.getSelectedRow();
				
				if (row>=0)
				{
					int idutente = (int) table.getModel().getValueAt(row, 0);
					String tipo = (String) table.getModel().getValueAt(row, 3);
					if(tipo.equals("Passivo"))
					{
						JOptionPane.showMessageDialog(null, "Un utente passivo non può avere pubblicazioni", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						String querypubb = "Select * from pubblicazione where ID_Utente="+idutente+";";
						ViewPubblicazioni vwpubb = new ViewPubblicazioni();
						vwpubb.setQuery(querypubb);
					}
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Devi selezionare prima una riga", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		btnElencopubb.setBounds(460, 7, 177, 23);
		frameUtenze.getContentPane().add(btnElencopubb);
	}
	
	private void CreateTable()
	{
		DefaultTableModel dtm= new DefaultTableModel()
		{
		    @Override
		    public boolean isCellEditable(int row, int column) 
		    {
		       return false;
		    }
		};
		dtm.setColumnIdentifiers(columnname);
		
		ArrayList<Utente> listautenti = cu.GetData(query);
		
		ListIterator<Utente> i= listautenti.listIterator();
		
		while(i.hasNext()) 
		{
			Utente obj= i.next();
			String tipo= "Passivo";
			
			if (obj.getTipo() == 1)
			{
				tipo = "Attivo";
			}
			Object[] row= {obj.getID(),obj.getNome(), obj.getCognome(), tipo};
			dtm.addRow(row);
		}
		
		table = new JTable(dtm);
		scrollPane.add(table);
		scrollPane.add(table.getTableHeader());
		
		table.removeColumn(table.getColumn("ID"));
		
		scrollPane.setViewportView(table);
		
		frameUtenze.getContentPane().add(scrollPane);
		
		scrollPane.revalidate();
		scrollPane.repaint();
		
	}
}
