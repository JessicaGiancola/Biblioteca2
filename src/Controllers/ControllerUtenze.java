package Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import Models.Utente;
import database.Connect;

public class ControllerUtenze implements FrontController{

	
	@Override
	public boolean SetData(Object[] filtro) {
		
		Connect conn= new Connect();	
		Connection connessione=conn.getConnection();
		
		boolean modifica = false;
		
	   PreparedStatement s;
	   try 
	   {

		   s = connessione.prepareStatement ("update utenti set Tipo = ? where ID =  ?");
		   s.setInt (1,(int) filtro[0]);
		   s.setInt(2,(int) filtro[1]);
		   int rs = s.executeUpdate();
		   
		   s.close ();
		   conn.closeConnection();
		   
		   if (rs>0)
		   {
				
				modifica= true;
		   }
		   
	   }
	   catch(SQLException e)
	   {
		   System.err.println ("Error message: " + e.getMessage ());
	       System.err.println ("Error number: " + e.getErrorCode ());
	   }
		
		
		return modifica;
	}

	@Override
	public ArrayList<Utente> GetData(String query) {
		ArrayList<Utente> listautenti = new ArrayList<Utente>();
		
		Connect conn= new Connect();	
		Connection connessione=conn.getConnection();
		
	   try 
	   {
		 Statement stmt = connessione.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 
	   while (rs.next ())
	   {
		  
			 //recupero valori dal db e creo istanza utente
		       int idutente = rs.getInt("ID");
		       String nomeutente = rs.getString ("Nome");
		       String cognomeutente = rs.getString ("Cognome");
		       int tipo = rs.getInt ("Tipo");
		       
		       //creazione utente
			   Utente user=new Utente(idutente,nomeutente, cognomeutente, tipo);
			   listautenti.add(user);				   
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
		
		return listautenti;
	}


}
