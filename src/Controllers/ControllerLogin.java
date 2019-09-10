package Controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import Models.Utente;
import database.Connect;


public class ControllerLogin{
	private String email;
	private char[] password;
	private static Utente user;
	
	public ControllerLogin(String email, char[] password) {
		this.email = email;
		this.password = password;
	}
	
	public int Login ()
	{
		String pswfinal="";
		
		int stato=0;
		boolean passwordcorretta=false;
		
		for(int i=0;i<password.length;i++)
		{
			pswfinal+=password[i];
		}
		
		
		Connect conn= new Connect();	
		Connection connessione=conn.getConnection();
		
		try 
		{
		
	   PreparedStatement s;
	 
	   s = connessione.prepareStatement ("select * from utenti where Email= ?");
	   s.setString (1, email);
	   ResultSet rs = s.executeQuery();
	   
	   while (rs.next ())
	   {
		   String dbpassword = rs.getString ("Password");
		   
		   passwordcorretta = pswfinal.equals(dbpassword);
		   
		   if(passwordcorretta)
		   {
			 //recupero valori dal db e creo istanza utente
		       int idutente = rs.getInt("ID");
		       String nomeutente = rs.getString ("Nome");
		       String cognomeutente = rs.getString ("Cognome");
		       Date datanascita = rs.getDate("DataNascita");
		       int tipo = rs.getInt ("Tipo");
		       
		       //creazione utente
		       user=new Utente(idutente,nomeutente, cognomeutente,email, datanascita, pswfinal, tipo);
		       stato=1;
		   }
		   else
		   {
			   stato=-1;
		   }

	   }
	   rs.close();
	   s.close ();
	   conn.closeConnection();
	   }
	   catch(Exception e)
	   {
		   if(e instanceof SQLException)
		   {
			   System.err.println ("Error number: " + ((SQLException) e).getErrorCode ());
		   }
		   System.err.println ("Error message: " + e.getMessage ());
		   
		   stato=-2;
	       
	   }

	   
		return stato;
	}

	public static Utente getUser() {
		return user;
	}

}
