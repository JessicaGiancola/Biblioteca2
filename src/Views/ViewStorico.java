package Views;

import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controllers.ControllerStorico;
import Controllers.FrontController;
import Models.Storico;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewStorico {

	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane = new JScrollPane();
	private FrontController cs= new ControllerStorico();
	private String query;
	private String[] columnname= {"ISBN","Storico"};
	
	public ViewStorico() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnVisualizzaLogModifiche = new JButton("Visualizza log modifiche");
		btnVisualizzaLogModifiche.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int row = table.getSelectedRow();
				
				if(row>=0)
				{
					long ISBN = (long) table.getModel().getValueAt(row, 0);
					query="SELECT ISBN,Operazione,ID_Utente FROM storico WHERE Operazione='Modifica' and storico.ISBN="+ISBN;
					CreateTable();
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Devi selezionare prima una riga", "InfoBox: " , JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			}
		});
		btnVisualizzaLogModifiche.setBounds(10, 11, 189, 23);
		frame.getContentPane().add(btnVisualizzaLogModifiche);
		
		JButton btnVisualizzaTutto = new JButton("Visualizza tutto");
		btnVisualizzaTutto.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				visualizzatutto();
			}
		});
		btnVisualizzaTutto.setBounds(251, 11, 169, 23);
		frame.getContentPane().add(btnVisualizzaTutto);
		
		scrollPane.setBounds(10, 74, 764, 476);
		visualizzatutto();
		
		frame.getContentPane().add(scrollPane);
		
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
		
		ArrayList<Storico> listastorico = cs.GetData(query);
		
		ListIterator<Storico> i= listastorico.listIterator();
		
		while(i.hasNext()) 
		{
			Storico obj= i.next();
			
			Object[] row= {obj.getISBN(), obj.StoricoToString()};
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
	
	private void visualizzatutto()
	{
		query="select Operazione,ID_Utente,ISBN from storico";
		CreateTable();
	}

}
