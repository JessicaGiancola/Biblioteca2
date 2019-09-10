package Views;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Controllers.ControllerPubblicazioni;
import Controllers.FrontController;
import Models.Pubblicazione;


import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.awt.event.ActionEvent;

public class ViewPubblicazioni {

	private JFrame frame;
	private JTable table;
	private String [] columnname;
	private JTextField textField = new JTextField();
	private JCheckBox chckbxQuery6 = new JCheckBox("Ordina per titolo");
	private JCheckBox chckbxQuery16 = new JCheckBox("Download disponibile");
	private JCheckBox chckbxQuery2 = new JCheckBox("Ultime 10 pubblicazioni");
	private JCheckBox chckbxQuery3 = new JCheckBox("Aggiornate ultimi 30 gg");
	private JCheckBox chckbxQuery17 = new JCheckBox("Includi ultima ristampa");
	private JButton btnDettaglioPubblicazione = new JButton("Dettaglio pubblicazione");
	private JButton btnElencoRecensioni = new JButton("Elenco Recensioni");
	private JButton btnMiPiace = new JButton("Mi piace");
	private JButton btnQuery18=new JButton("Ricerca pubblicazioni con stesso autore");
	private JScrollPane scrollPane = new JScrollPane();
	private FrontController cpubb= new ControllerPubblicazioni();
	private String query;


	public ViewPubblicazioni() {
		initialize();
		frame.setVisible(true);
	}
	
	private void querybase()
	{
		columnname = new String [] {"ISBN","Titolo","Editore","Data Pubblicazione","Autori","Numero like","Download"};
		query = "SELECT pubblicazione.ISBN, Titolo, Editore, DataPubblicazione,Autori,Dowload,NumeroLike FROM pubblicazione LEFT JOIN (SELECT ISBN,count('ID_Utente') as NumeroLike FROM mipiace GROUP BY ISBN) as piace ON pubblicazione.ISBN=piace.ISBN";
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 956, 496);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		querybase();
		chckbxQuery6.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{//ordina per titolo

				CreateTable();
	
			}
		});
		chckbxQuery6.setBounds(6, 7, 126, 23);
		frame.getContentPane().add(chckbxQuery6);
		chckbxQuery16.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(chckbxQuery16.isSelected())
				{// download disponibile
					query = "SELECT pubblicazione.ISBN, Titolo, Editore, DataPubblicazione,Autori,Dowload,NumeroLike FROM pubblicazione LEFT JOIN (SELECT ISBN,count('ID_Utente') as NumeroLike FROM mipiace GROUP BY ISBN) as piace ON pubblicazione.ISBN=piace.ISBN where Dowload=1";
				}
				else
				{
					querybase();
				}
				CreateTable();
				
			}
		});
		

		chckbxQuery16.setBounds(175, 7, 184, 23);
		frame.getContentPane().add(chckbxQuery16);
		chckbxQuery2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(chckbxQuery2.isSelected())
				{
					query = "SELECT pubblicazione.ISBN, Titolo, Editore, DataPubblicazione,Autori,Dowload,NumeroLike FROM pubblicazione LEFT JOIN (SELECT ISBN,count('ID_Utente') as NumeroLike FROM mipiace GROUP BY ISBN) as piace ON pubblicazione.ISBN=piace.ISBN order by DataPubblicazione desc limit 0,10";
				}
				else
				{
					querybase();
				}
				CreateTable();
			}
		});
		

		chckbxQuery2.setBounds(361, 7, 200, 23);
		frame.getContentPane().add(chckbxQuery2);
		chckbxQuery3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(chckbxQuery3.isSelected())
				{
					query = "SELECT DISTINCT Titolo,pubblicazione.*,storico.DataOra,DATEDIFF(CURRENT_DATE,storico.DataOra)AS 'differenzadata' FROM pubblicazione INNER JOIN storico ON storico.ISBN=pubblicazione.ISBN WHERE storico.operazione='Modifica' AND 'differenzadata'<=30";
					columnname = new String [] {"ISBN","Titolo","Editore","Data Pubblicazione","Autori","Download"};
				}
				else
				{
					columnname = new String [] {"ISBN","Titolo","Editore","Data Pubblicazione","Autori","Numero like","Download"};
					querybase();
				}
				CreateTable();
			}
		});
		

		chckbxQuery3.setBounds(563, 7, 200, 23);
		frame.getContentPane().add(chckbxQuery3);
		chckbxQuery17.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(chckbxQuery17.isSelected())
				{
					columnname = new String [] {"ISBN","Titolo","Editore","Data Pubblicazione","Autori","Ultima Ristampa","Download"};
					query = "SELECT MAX(ristampe.Data) as maxdata,pubblicazione.* FROM pubblicazione INNER JOIN ristampe ON pubblicazione.ISBN=ristampe.ISBN GROUP BY ristampe.ISBN";
					
				}
				else
				{
					querybase();
				}
				
				CreateTable();
			}
		});
		

		chckbxQuery17.setBounds(765, 7, 165, 23);
		frame.getContentPane().add(chckbxQuery17);
		btnDettaglioPubblicazione.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int row = table.getSelectedRow();
				
				if (row>=0)
				{
					long ISBN = (long) table.getModel().getValueAt(row, 0);
					ViewDettaglioLibro vwdl = new ViewDettaglioLibro(ISBN);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Devi selezionare prima una riga", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		

		btnDettaglioPubblicazione.setBounds(10, 48, 165, 23);
		frame.getContentPane().add(btnDettaglioPubblicazione);
		btnElencoRecensioni.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int row = table.getSelectedRow();
				
				if (row>=0)
				{
					long ISBN = (long) table.getModel().getValueAt(row, 0);
					ViewRecensioni vwrec = new ViewRecensioni();
					vwrec.setQuery("Select * from recensioni where ISBN="+ISBN+" and Pubblicata=1");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Devi selezionare prima una riga", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		

		btnElencoRecensioni.setBounds(194, 48, 157, 23);
		frame.getContentPane().add(btnElencoRecensioni);
		btnQuery18.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{// ricerca pubb con gli stessi autori
				
				int row = table.getSelectedRow();
				
				if (row>=0)
				{
					columnname = new String[] {"ISBN","Titolo","Editore","Data Pubblicazione","Autori","Download"};
					
					String autori = (String) table.getModel().getValueAt(row, 4);
					
					String[] listaautori= autori.split(",");

					
				    query="Select * from pubblicazione where Autori LIKE '%"+listaautori[0]+"%'";
				   
				   for(int i=1;i<listaautori.length;i++)
				   {
					   query+=" OR Autori LIKE '%"+listaautori[i]+"%' ";
				   }
				   
				   CreateTable();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Devi selezionare prima una riga", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		
		btnQuery18.setBounds(484, 48, 271, 23);
		frame.getContentPane().add(btnQuery18);
		btnMiPiace.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int row = table.getSelectedRow();
				
				if (row>=0)
				{
					Object mipiace [] = new Object [] {table.getModel().getValueAt(row, 0)};
					
					boolean insertmipiace = cpubb.SetData(mipiace);
					
					if(insertmipiace)
					{
						DefaultTableModel model = (DefaultTableModel)table.getModel();
						int nummipiace= (int) model.getValueAt(row, 5);
						nummipiace++;
						model.setValueAt(nummipiace, row, 5);
					}
					else
					{
						 JOptionPane.showMessageDialog(null, "Hai già inserito il tuo mi piace", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Devi selezionare prima una riga", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		

		btnMiPiace.setBounds(361, 48, 113, 23);
		frame.getContentPane().add(btnMiPiace);
		

		scrollPane.setBounds(6, 92, 924, 354);
		CreateTable();
		textField.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				query = "SELECT * FROM pubblicazione WHERE ISBN LIKE '%"+textField.getText()+"%' OR Titolo LIKE '%"+textField.getText()+"%' OR Keywords LIKE '%"+textField.getText()+"%' OR Autori LIKE '%"+textField.getText()+"%'";
				CreateTable();
			}
		});
		
		
		textField.setToolTipText("Cerca");
		textField.setBounds(765, 49, 150, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
	}
	
	private void CreateTable()
	{
		
		ArrayList<Pubblicazione> listap = cpubb.GetData(query);
		
		if(chckbxQuery6.isSelected())
		{
			Collections.sort(listap);
		}
		
		DefaultTableModel dtm= new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		dtm.setColumnIdentifiers(columnname);
		
		ListIterator<Pubblicazione> i= listap.listIterator();
		
		while(i.hasNext()) {
			Pubblicazione obj= i.next();
			
			String download="Non disponibile";
			
			if(obj.isDownload())
			{
				download="Disponibile";
			}
			
			Object[] row= {obj.getISBN(),obj.getTitolo(),obj.getEditore(),obj.getDatapubblicazione(),obj.getAutori(),download};
			
			if(obj.getNumerolike()>=0)
			{
				 row= new Object [] {obj.getISBN(),obj.getTitolo(),obj.getEditore(),obj.getDatapubblicazione(),obj.getAutori(),obj.getNumerolike(),download};
			}
		
			if(obj.getUltimaristampa()!=null)
			{
				 row=  new Object [] {obj.getISBN(),obj.getTitolo(),obj.getEditore(),obj.getDatapubblicazione(),obj.getAutori(),obj.getUltimaristampa(),download};
			}
			
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

	public void setQuery(String query) {
		this.query = query;
		columnname = new String [] {"ISBN","Titolo","Editore","Data Pubblicazione","Autori","Download"};
		CreateTable();
	}

}
