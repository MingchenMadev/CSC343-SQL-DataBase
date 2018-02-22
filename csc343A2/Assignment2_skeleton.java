import java.sql.*;
public class Assignment2{
    
  // A connection to the database  
  Connection connection;
  
  // Statement to run queries
  Statement sql;
  
  // Prepared Statement
  PreparedStatement ps;
  
  // Resultset for the query
  ResultSet rs;
  
  //CONSTRUCTOR
  Assignment2(){
    try{
      Class.forName("org postgresql Driver");
    } catch (ClassNotFoundException ce){
       ce.printStackTrace();
    }
  }

  public void setPath() throws SQLException{
    try{
      sql = connection.createStatement();
      sql.executeUpdate("SET search_path TO A2");
    }catch(SQLException se){
      se.printStackTrace();
    }
  }
  
  //Using the input parameters, establish a connection to be used for this session. Returns true if connection is sucessful
  public boolean connectDB(String URL, String username, String password) {
    try{
      Connection connection = DriverManager.getConnection(URL, username, password)
      if(connection.isValid()){
        return true;
      }
      return false;
    }catch(SQLException se){
      se.printStackTrace();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  
  //Closes the connection. Returns true if closure was sucessful
  public boolean disconnectDB(){
    try{
      connection.close();
      if(connection.isClosed()){
       return true;
      }
      return false;
    }catch(SQLException se){
      se.printStackTrace();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
    
  public boolean insertCountry (int cid, String name, int height, int population) {
    setPath();
    try{
      sql = connection.createStatement();
      String query = "INSERT INTO country VALUES(" + cid + ",'" + name \
        + "'," + height + "," + population + ");";
      sql.executeUpdate(query));
      ps = connection.PreparedStatement();
      if(ps.executeUpdate()){
        return true;
      }
      return false;
    }catch(SQLException se){
      se.printStackTrace();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  
  public int getCountriesNextToOceanCount(int oid) {
    setPath();
    try{
      sql = connection.createStatement();
      String query = "SELECT COUNT(*) AS rowCount FROM oceanAccess WHERE oid =" + "'" + oid + "';";
      rs = sql.executeQuery(query);
      rs.next();
      int count = r.getInt("rowCount");
      rs.close();
      return count;
    }
    catch(SQLException se){
      se.printStackTrace();
      return -1; 
    }catch(Exception e){
      e.printStackTrace();
      return -1;
    }
  }
   
  public String getOceanInfo(int oid) {
    setPath();
    try{
    sql = connection.createStatement();
      String query = "SELECT * FROM ocean WHERE oid =" + "'" + oid + "';";
      rs = sql.executeQuery(query);
      if (rs.next()){
        return "oid:" + rs.getString("oid") + "oname:" + rs.getString("oname") + "depth:" +  rs.getString("depth");
      }else{
        return "";
      }
    }catch(SQLException se){
      se.printStackTrace();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public boolean chgHDI(int cid, int year, float newHDI){
    setPath();
    try{
      sql = connection.createStatement();
      String query = "UPDATE hdi SET hdi_score = " + newHDI + "WHERE cid = " + cid + " and year = " + year + ";"
      sql.executeUpdate(query);
      ps = connection.PreparedStatement();
      if(ps.executeUpdate()){
        return true;
      }
      return false;
    }catch(SQLException se){
      se.printStackTrace();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public boolean deleteNeighbour(int c1id, int c2id){
    setPath();
    sql = connection.createStatement();
    String query1 = "DELETE FROM neighbour WHERE country = " + c1id + "and neighbor = " + c2id + ";";
    sql.executeUpdate(query1);
    String query2 = "DELETE FROM neighbour WHERE country = " + c2id + "and neighbor = " + c1id + ";";
    sql.executeUpdate(query2);
    ps = connection.PreparedStatement();
    if(ps.executeUpdate()){
      return true;
    }
    return false;       
  }
  
  public String listCountryLanguages(int cid){
    setPath();
    try{
        sql = connection.createStatement(); 
      String query2 = "SELECT lid, lnmae, (population*lpercentage) AS lpopulation FROM language NATURAL JOIN country WHERE cid = " + cid + " ORDER BY lpopulation;";
      rs = sql.executeQuery(query);
      s = "";
      if(!rs.next()){
        return "";
      }else{
        rs.next();
        s += ("lid:" + rs.getString("lid") + "lname:" + rs.getString("lid") + "population:" + rs.getFloat("lpopulation");
        while(rs.next()){
          s += ("#lid:" + rs.getString("lid") + "lname:" + rs.getString("lid") + "population:" + rs.getFloat("lpopulation");
        }
      }
      return s;
    }catch(SQLException se){
      se.printStackTrace();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  
  public boolean updateHeight(int cid, int decrH){
    setPath();
    try{
      sql = connection.createStatement();
      String query1 = "SELECT height FROM country WHERE cid = " + Integer.toString(cid) + ";";
      rs = sql.executeQuery(query);
      rs.next();
      int he = rs.getInt("height");
      String query2 = "UPDATE country SET height = " + Integer.toString(he - decrH) + "WHERE cid = " + Integer.toString(cid) + ";";
      sql.executeUpdate(query2);
      ps = connection.PreparedStatement();
      if(ps.executeUpdate()){
        return true;
      }
      return false;
    }catch(SQLException se){
      se.printStackTrace();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
    
  public boolean updateDB(){
    setPath();
    try{
        sql = connection.createStatement();
        String query1 = "CREATE TABLE mostPopulousCountries (
                cid     INTEGER   PRIMARY KEY,
                cname     VARCHAR(20) NOT NULL);"
        sql.executeUpdate(query1);
        String query2 = "INSERT INTO country VALUES( SELECT cid, cname FROM country WHERE population > 10e8 ORDER BY cid);"
        sql.executeUpdate(query2);
        if(ps.executeUpdate()){
          return true;
        }
        return false; 
    }catch(SQLException se){
      se.printStackTrace();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
