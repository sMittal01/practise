package first.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbSource {

	private static DbSource dbInstance;

	private DbSource(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}  

	}

	public static DbSource getDbInstance(){
		if(dbInstance==null){
			dbInstance=new DbSource();
		}
		return dbInstance;
	}

	public void postObject(String sql) throws SQLException{
		Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/world","root","root");  
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.execute(sql);
		con.close();
	}
	
	public void putObject(String sql) throws SQLException{
		Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/world","root","root");  
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.execute(sql);
		con.close();
	}
	
	public void deleteObject(String sql) throws SQLException{
		
		Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/world","root","root");  
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.execute(sql);
		con.close();
	}
	
	public <T> List<T> getObjects(String sql) throws SQLException{
		Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/world","root","root");  
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List<MyPojo> pojoList = new ArrayList<MyPojo>();
		if(null != rs){
			while(rs.next()){
				MyPojo pojo = new MyPojo();
				pojo.setId(rs.getInt(1));
				pojo.setName(rs.getString(2));
				pojo.setCountryCode(rs.getString(3));
				pojo.setDistrict(rs.getString(4));
				pojo.setPopulation(Integer.valueOf(rs.getString(5)));
				pojoList.add(pojo);
			}
		}
		rs.close();
		stmt.close();
		con.close();
		return (List<T>) pojoList;
	}
}
