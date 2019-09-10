package Views;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controllers.ControllerRecensioni;
import Controllers.FrontController;
import Models.Recensione;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ListIterator;
import java.awt.event.ActionEvent;

public class ViewRecensioni {

	private JFrame frame;
	private String [] columnname = {"ID","ID_Utente","Testo","ISBN","Data","Pubblicata"};
	private JTable table;
	private JScrollPane scrollPane = new JScrollPane();
	private String query;
	private FrontController crec = new ControllerRecensioni();

	public ViewRecensioni() {
		initialize();
		frame.setVisible(true);
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 712, 588);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnInserisciUnaNuova = new JButton("Inserisci una nuova recensione");
		btnInserisciUnaNuova.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				ViewNuovaRecensione vwnr = new ViewNuovaRecensione();
			}
		});
		btnInserisciUnaNuova.setBounds(10, 11, 222, 23);
		frame.getContentPane().add(btnInserisciUnaNuova);
		
		JButton btnApprovaEPubblica = new JButton("Approva e pubblica recensione");
		btnApprovaEPubblica.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				int row = table.getSelectedRow();
				
				if (row>=0)
				{
					String pubb = (String) table.getModel().getValueAt(row, 5);
					int idrecensione= (int) table.getModel().getValueAt(row,0);
				
					if (pubb.equals("Pubblicata"))
					{ 
						JOptionPane.showMessageDialog(null, "La recensione è già stata pubblicata", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE); 
					}
					else 
					{
						pubb="Pubblicata";
						Object[] approva = new Object[]{1,idrecensione};
							
					
					    boolean modifica = crec.SetData(approva);
					
						if(modifica)
						{
							DefaultTableModel model = (DefaultTableModel)table.getModel();
							model.setValueAt(pubb, row, 5);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Modifica fallita", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
						}
					
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Devi selezionare prima una riga", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnApprovaEPubblica.setBounds(253, 11, 237, 23);
		frame.getContentPane().add(btnApprovaEPubblica);
		
		JCheckBox chckbxFiltraPerNon = new JCheckBox("Filtra per non pubblicate");
		chckbxFiltraPerNon.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(chckbxFiltraPerNon.isSelected())
				{
					query = "select ID,ID_Utente,Testo,ISBN,DataOra, Pubblicata from recensioni where Pubblicata=0";
					CreateTable();
				}
				else
				{
					recensionibase();
					CreateTable();
				}
			}
		});
		chckbxFiltraPerNon.setBounds(496, 11, 194, 23);
		frame.getContentPane().add(chckbxFiltraPerNon);
		
		scrollPane.setBounds(10, 61, 680, 477);
		recensionibase();
		CreateTable();
		frame.getContentPane().add(scrollPane);
		
	}
	
	private void CreateTable()
	{
		ArrayList<Recensione> listar= crec.GetData(query);
		DefaultTableModel dtm= new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		dtm.setColumnIdentifiers(columnname);
		
		ListIterator<Recensione> i= listar.listIterator();
		
		while(i.hasNext()) 
		{
			Recensione obj= i.next();
			
			String pubb="Non pubblicata";
			if(obj.isPubblicata())
			{pubb="Pubblicata";}
			
			Object[] row= {obj.getID(),obj.getID_Utente(),obj.getTesto(),obj.getISBN(),obj.getDataOra(),pubb};
			dtm.addRow(row);
		}
		
		table = new JTable(dtm);
		scrollPane.add(table);
		scrollPane.add(table.getTableHeader());
		
		
		scrollPane.setViewportView(table);
		
		frame.getContentPane().add(scrollPane);
		
		scrollPane.revalidate();
		scrollPane.repaint();
		
		
	}
	
	private void recensionibase()
	{
		query = "select ID,ID_Utente,Testo,ISBN,DataOra, Pubblicata from recensioni";
	}


	public void setQuery(String query) {
		this.query = query;
		CreateTable();
	}



}
