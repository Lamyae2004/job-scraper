package Interface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class Based {


	public Connection connect() {
								try {
											Class.forName("com.mysql.cj.jdbc.Driver");
											//System.out.println("Driver Ok");
											String url ="jdbc:mysql://localhost:3306/data";
											String user="root";
											String password="";
											Connection con=DriverManager.getConnection(url,user,password);
											return con;
									}catch(Exception e) {
											e.printStackTrace();
											return null;
														}
		}
		

}
		
		
		
	
