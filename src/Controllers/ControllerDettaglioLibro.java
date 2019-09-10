package Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class ControllerDettaglioLibro implements FrontController{

	@Override
	public boolean SetData(Object[] T) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public  ArrayList<String> GetData(String Query) {
		
		ArrayList<String> libro=new ArrayList();
		
		Connect conn= new Connect();	
		Connection connessione=conn.getConnection();
		
	   PreparedStatement s;
	   try 
	   {
		   
	   s = connessione.prepareStatement (Query);

	   ResultSet rs = s.executeQuery();
	   
	   
	   while (rs.next ())
	   {

		   libro.add(rs.getString ("Titolo"));
		   libro.add(rs.getString ("Editore"));
		   libro.add(rs.getString ("Descrizione"));
		   libro.add(rs.getString ("Lingua"));
		   libro.add(rs.getString ("Indice"));
		   libro.add(rs.getString ("Keywords"));
		   libro.add(rs.getString ("Autori"));
		   libro.add(rs.getString ("DataPubblicazione"));
		   libro.add(rs.getString ("NumeroPagine"));
		   libro.add(rs.getString ("Dowload"));
	       
	       //Date datapubblicazione = rs.getDate("DataPubblicazione");
	      // int numeropagine = rs.getInt("NumeroPagine");
	      // boolean download = rs.getBoolean("Dowload");
	       
	       
	       //libro=new Pubblicazione(ISBN,titolo, editore,descrizione, lingua, numeropagine, datapubblicazione,indice,keywords,download,autori);   

	   }
	   rs.close();
	   s.close ();
	   conn.closeConnection();
	   
		
	   }
	   catch(SQLException e)
	   {
		   System.err.println ("Error message: " + e.getMessage ());
	       System.err.println ("Error number: " + e.getErrorCode ());
	   }
		
		return libro;
	}

}
