package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Vene;

public class Dao { 
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	
	private Connection yhdista(){
    	Connection con = null;    	        	
    	String JDBCAjuri = "org.mariadb.jdbc.Driver";
    	String url = "jdbc:mariadb://localhost:15001/a1702576";        	
    	try {
	         Class.forName(JDBCAjuri);
	         con = DriverManager.getConnection(url,"a1702576", "foJY8434h");	    
	     }catch (Exception e){	         
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public boolean lisaaVene(Vene vene){
		boolean paluuArvo=true;
		sql="INSERT INTO Veneet(nimi, merkkimalli, pituus, leveys, hinta) VALUES(?,?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, vene.getNimi());
			stmtPrep.setString(2, vene.getMerkkimalli());
			stmtPrep.setString(3, vene.getPituus());
			stmtPrep.setString(4, vene.getLeveys());
			stmtPrep.setString(5, vene.getHinta());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public ArrayList<Vene> listaaKaikki(){
		ArrayList<Vene> Veneet = new ArrayList<Vene>();
		sql = "SELECT * FROM Veneet";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					con.close();					
					while(rs.next()){
						Vene vene = new Vene();
						vene.setTunnus(rs.getInt(1));
						vene.setNimi(rs.getString(2));
						vene.setMerkkimalli(rs.getString(3));
						vene.setPituus(rs.getString(4));	
						vene.setLeveys(rs.getString(5));	
						vene.setHinta(rs.getString(6));	
						Veneet.add(vene);
					}					
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Veneet;
	}
	
	public ArrayList<Vene> listaaKaikki(String hakusana){
		ArrayList<Vene> Veneet = new ArrayList<Vene>();
		sql = "SELECT * FROM Veneet WHERE nimi LIKE ? or merkkimalli LIKE ? or pituus LIKE ? or leveys LIKE ? or hinta LIKE ?";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);   
				stmtPrep.setString(1, "%" + hakusana + "%");
				stmtPrep.setString(2, "%" + hakusana + "%");
				stmtPrep.setString(3, "%" + hakusana + "%");
				stmtPrep.setString(4, "%" + hakusana + "%");
				stmtPrep.setString(5, "%" + hakusana + "%");
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					con.close();					
					while(rs.next()){
						Vene vene = new Vene();
						vene.setTunnus(rs.getInt(1));
						vene.setNimi(rs.getString(2));
						vene.setMerkkimalli(rs.getString(3));
						vene.setPituus(rs.getString(4));	
						vene.setLeveys(rs.getString(5));
						vene.setHinta(rs.getString(6));
						Veneet.add(vene);
					}					
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Veneet;
	}
	
	public Vene loytyykoVene(int tunnus){
		Vene vene = null;
		sql = "SELECT * FROM Veneet WHERE tunnus=?";       
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setInt(1, tunnus);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa
        			rs.next();
        			vene = new Vene();        			
        			vene.setNimi(rs.getString(2));
        			vene.setMerkkimalli(rs.getString(3));
        			vene.setPituus(rs.getString(4));	
        			vene.setLeveys(rs.getString(5));	
        			vene.setHinta(rs.getString(6));
        			con.close();        			
				}			
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return vene;		
	}
	
	public boolean muutaVene(Vene vene){
		boolean paluuArvo=true;
		sql="UPDATE Veneet SET nimi=?, merkkimalli=?, pituus=?, leveys=?, hinta=? WHERE tunnus=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, vene.getNimi());
			stmtPrep.setString(2, vene.getMerkkimalli());
			stmtPrep.setString(3, vene.getPituus());
			stmtPrep.setString(4, vene.getLeveys());
			stmtPrep.setString(5, vene.getHinta());
			stmtPrep.setInt(6, vene.getTunnus());

			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public boolean poistaVene(int tunnus){
		boolean paluuArvo=true;
		sql="DELETE FROM Veneet WHERE tunnus=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setInt(1, tunnus);			
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
}