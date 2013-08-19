package database;

import java.sql.*;

/* @author mhaanran */
public class TietokantaYhteys {

    static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/projtyoaika";
    static final String kayttaja = "marko";
    static final String salasana = "marko";

    public TietokantaYhteys() {
        
    }
//    public static void main(String[] args) {
    public void luoTietokantaYhteys() {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement prep=null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            conn = DriverManager.getConnection(DB_URL, kayttaja, salasana);

            prep = conn.prepareStatement("SELECT * FROM KAYTTAJA WHERE KAYTTAJATUNNUS=? AND SALASANA=?");
            prep.setString(1, "ville");
            prep.setString(2, "");
            System.out.println("onnistui");
            
            ResultSet resultset = prep.executeQuery();
            if(resultset.next()) {
                System.out.println("Ville löytyi");
            }           
          
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try{
                if(conn!=null) {
                    conn.close();
                }
            }catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
