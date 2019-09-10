package Controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.Recensione;
import database.Connect;

public class ControllerRecensioni implements FrontController{

	@Override
	public boolean SetData(Object[] approva) {
		
		Connect conn= new Connect();	
		Connection connessione=conn.getConnection();
		
		boolean approvazione = false;
		
	   PreparedStatement s;
	   try 
	   {

		   s = connessione.prepareStatement ("update recensioni set Pubblicata = ? where ID =  ?");
		   s.setInt (1,(int) approva[0]);
		   s.setInt(2,(int) approva[1]);
		   int rs = s.executeUpdate();
		   
		   s.close ();
		   conn.closeConnection();
		   
		   if (rs>0)
		   {
				
			   approvazione= true;
		   }
		   
	   }
	   catch(SQLException e)
	   {
		   System.err.println ("Error message: " + e.getMessage ());
	       System.err.println ("Error number: " + e.getErrorCode ());
	   }
		
		
		return approvazione;
	}

	@Override
	public  ArrayList<Recensione> GetData(String Query) {
		ArrayList<Recensione> listarecensioni= new ArrayList();
		
		Connect conn= new Connect();	
		Connection connessione=conn.getConnection();
		
	  
	   try 
	   {
		 Statement stmt = connessione.createStatement();
		 ResultSet rs = stmt.executeQuery(Query);
		 
	   while (rs.next ())
	   {
		  
			 //recupero valori dal db e creo istanza recensioni
		       int idrecensione = rs.getInt("ID");
		       int idutente = rs.getInt("ID_Utente");
		       String testo = rs.getString ("Testo");
		       long ISBN=rs.getLong("ISBN");
		       Date dataora=rs.getDate("DataOra");
		       boolean pubblicata=rs.getBoolean("Pubblicata");
		       
		       //creazione recensioni
		       Recensione recensione=new Recensione(idrecensione,testo, pubblicata, dataora,ISBN,idutente);
			   listarecensioni.add(recensione);				   
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
		
		
		
		return listarecensioni;
	}

}
