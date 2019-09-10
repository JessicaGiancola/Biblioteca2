package Views;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Controllers.ControllerDettaglioLibro;
import Controllers.FrontController;

public class ViewDettaglioLibro {

	private JFrame frame;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private FrontController cdl = new ControllerDettaglioLibro();
	private ArrayList<String> libro = new ArrayList();
	private long ISBN;


	public ViewDettaglioLibro(long ISBN) {
		this.ISBN=ISBN;
		String query= "select * from pubblicazione where ISBN="+ISBN+";";
		libro = cdl.GetData(query);
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 535, 329);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("ISBN: "+Long.toString(ISBN));
		lblNewLabel.setBounds(10, 11, 499, 14);
		frame.getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Titolo: "+libro.get(0));
		lblNewLabel_1.setBounds(10, 36, 499, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Editore: "+libro.get(1));
		lblNewLabel_2.setBounds(10, 61, 499, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Descrizione: "+libro.get(2));
		lblNewLabel_3.setBounds(10, 86, 499, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("Lingua: "+libro.get(3));
		lblNewLabel_4.setBounds(10, 111, 499, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Indice: "+libro.get(4));
		lblNewLabel_5.setBounds(10, 136, 499, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		label = new JLabel("Keywords: "+libro.get(5));
		label.setBounds(10, 261, 499, 14);
		frame.getContentPane().add(label);
		
		label_2 = new JLabel("Autori: "+libro.get(6));
		label_2.setBounds(10, 236, 499, 14);
		frame.getContentPane().add(label_2);
		
		label_3 = new JLabel("DataPubblicazione: "+libro.get(7));
		label_3.setBounds(10, 211, 499, 14);
		frame.getContentPane().add(label_3);
		
		label_4 = new JLabel("NumeroPagine: "+libro.get(8));
		label_4.setBounds(10, 186, 499, 14);
		frame.getContentPane().add(label_4);
		
		label_5 = new JLabel("Dowload: "+libro.get(9));
		label_5.setBounds(10, 161, 499, 14);
		frame.getContentPane().add(label_5);
	}

}
