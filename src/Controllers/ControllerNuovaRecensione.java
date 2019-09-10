package Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Controllers.ControllerLogin;
import database.Connect;

public class ControllerNuovaRecensione implements FrontController{

	@Override
	public boolean SetData(Object[] nuovarec) {
				
		boolean inserimento = false;
		Connect conn= new Connect();	
		Connection connessione=conn.getConnection();
		PreparedStatement s;
			   try 
			   {
				   
				   s = connessione.prepareStatement ("insert into recensioni (Testo,Pubblicata,ISBN,ID_Utente) values(?,0,?,?)");
				   s.setString (1, (String) nuovarec[0]);
				   s.setLong (2, (long) nuovarec[1]);
				   s.setInt (3, (int) nuovarec[2]);
				   
				   int rs = s.executeUpdate();
				
				   s.close ();
				   conn.closeConnection();
				   
				   if (rs>0)
				   {
					   inserimento= true;
				   }
				
			   }
			   catch(SQLException e)
			   {
				   System.err.println ("Error message: " + e.getMessage ());
			       System.err.println ("Error number: " + e.getErrorCode ());
			   }
		
			   
		return inserimento;
	}

	@Override
	public  ArrayList<Long> GetData(String Query) {
		
		ArrayList<Long> listaisbn = new ArrayList();
		
		Connect conn= new Connect();	
		Connection connessione=conn.getConnection();
		PreparedStatement s;
		   try 
		   {
			
			 s = connessione.prepareStatement ("SELECT DISTINCT pubblicazione.ISBN from pubblicazione LEFT JOIN recensioni on pubblicazione.ISBN=recensioni.ISBN WHERE  pubblicazione.ISBN NOt IN (SELECT ISBN FROM utenti INNER JOIN recensioni ON utenti.ID=recensioni.ID_Utente WHERE recensioni.ID_Utente=?);");
			 s.setInt(1, ControllerLogin.getUser().getID());
			 ResultSet rs = s.executeQuery();
			 
		   while (rs.next ())
		   {

			    long ISBN = rs.getLong("ISBN");
			    listaisbn.add(ISBN);
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
		
		return listaisbn;
	}

}
