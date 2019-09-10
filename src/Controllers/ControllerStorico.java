package Controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.Storico;
import database.Connect;

public class ControllerStorico implements FrontController{

	@Override
	public boolean SetData(Object[] T) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public  ArrayList<Storico> GetData(String Query) {
		ArrayList<Storico> listastorico = new ArrayList();
		
		Connect conn= new Connect();	
		Connection connessione=conn.getConnection();
		
	   try 
	   {
		
		 Statement stmt = connessione.createStatement();
		 ResultSet rs = stmt.executeQuery(Query);
		 
	   while (rs.next ())
	   {	  
			 //recupero valori dal db e creo istanza storico
		       int idutente = rs.getInt("ID_Utente");
		       long ISBN = rs.getLong ("ISBN");
		       String operazione = rs.getString ("Operazione");
		       
		       //creazione storico
			   Storico striga=new Storico(ISBN,idutente, operazione);
			   listastorico.add(striga);				   
	   }
	   rs.close();
	   stmt.close ();
	   conn.closeConnection();
		
	   }
	   catch(SQLException e)
	   {
		   System.err.println ("Error message: " + e.getMessage ());
	       System.err.println ("Error number: " + e.getErrorCode ());
	   }
		
		return  listastorico;
	}
	

}
