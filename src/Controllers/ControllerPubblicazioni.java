package Controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import Models.Pubblicazione;
import database.Connect;

public class ControllerPubblicazioni implements FrontController{

	@Override
	public boolean SetData(Object[] mipiace) 
	{
		boolean insert = false;
		long ISBN = (long) mipiace[0];
		int idutente= Controllers.ControllerLogin.getUser().getID();
				
		Connect conn= new Connect();	
		Connection connessione=conn.getConnection();
		
	   PreparedStatement s;
	   try 
	   {
		   
	   
		   s = connessione.prepareStatement ("Select * from mipiace where ID_Utente= ? and ISBN=?");
		   s.setInt (1, idutente);
		   s.setLong(2, ISBN);
		   ResultSet rs = s.executeQuery();
		   
		   boolean trovato=false;
		   
		   while(rs.next())
			   
		   {
			   trovato=true;
		   }
		   
		   if(!trovato)
		   {
			   s = connessione.prepareStatement ("insert into mipiace (ID_Utente,ISBN) values (?,?)");
			   s.setInt (1, idutente);
			   s.setLong(2, ISBN);
			   int res = s.executeUpdate();
			   if (res>0)
			   {
					insert=true;
			   }
	
		   }
	   }
	   catch(SQLException e)
	   {
		   System.err.println ("Error message: " + e.getMessage ());
	       System.err.println ("Error number: " + e.getErrorCode ());
	   }
	   
	   return insert;
	   }


	@Override
	public  ArrayList<Pubblicazione> GetData(String Query) {
		ArrayList<Pubblicazione> catalogo= new ArrayList();
		
		Connect conn= new Connect();	
		Connection connessione=conn.getConnection();
		
	  
	   try 
	   {
		
		 Statement stmt = connessione.createStatement();
		 ResultSet rs = stmt.executeQuery(Query);
		 
		 ResultSetMetaData rsMetaData = rs.getMetaData();
		 int numberOfColumns = rsMetaData.getColumnCount();
		 
		 boolean numoflike = false;
		 boolean maxdata = false;

		 // get the column names; column indexes start from 1
		 for (int i = 1; i < numberOfColumns + 1; i++) 
		 {
		     String columnName = rsMetaData.getColumnName(i);
		     // Get the name of the column's table name
		     if ("maxdata".equals(columnName)) 
		     {
		         maxdata= true;
		     }
		     
		     if ("NumeroLike".equals(columnName)) 
		     {
		    	 numoflike=true;
		     }
		     
		 }
		 
		 
	   while (rs.next ())
	   {
		  
			 //recupero valori dal db e creo istanza pubblicazione
	       	   long ISBN=rs.getLong("ISBN");
		       String titolo = rs.getString ("Titolo");
		       String editore = rs.getString ("Editore");
		       Date datapubblicazione=rs.getDate("DataPubblicazione");
		       String autori = rs.getString ("Autori");
		       boolean download=rs.getBoolean("Dowload");
		       
		       Date ultimaristampa = null;
		       if(maxdata)
		       {
		    	   ultimaristampa = rs.getDate("maxdata");
		       }
		       
		       int numerolike=-1;
		       if(numoflike)
		       {
		    	   numerolike = rs.getInt("NumeroLike");
		       }
	
		       //creazione pubblicazione
		       Pubblicazione libro=new Pubblicazione(ISBN,titolo,editore,datapubblicazione,download,autori,numerolike,ultimaristampa);
			   catalogo.add(libro);				   
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
	   
	   return catalogo;
	}

}
