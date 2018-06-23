package com.kishonnishanth.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.kishonnishant.cartcontrollers.User;

public class SQLDatabaseInstance {
	private Properties prop = null;
	private InputStream input = null;
	private String url = null;
	private String userName = null;
	private String password = null;
	private Connection conn = null;
	private Statement statement = null;
	

	/**
	 * @return the prop
	 */
	public Properties getProp() {
		return prop;
	}

	/**
	 * @param prop
	 *            the prop to set
	 */
	private void setProp(Properties prop) {
		this.prop = prop;
	}

	/**
	 * @return the input
	 */
	public InputStream getInput() {
		return input;
	}

	/**
	 * @param input
	 *            the input to set
	 */
	private void setInput(InputStream input) {
		this.input = input;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	private void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	private void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	private void setPassword(String password) {
		this.password = password;
	}

	public SQLDatabaseInstance() {
		prop = new Properties();
		try {
			input = new FileInputStream("../CartApp/Config/config.properties");
		} catch (FileNotFoundException FNFE) {
			// TODO Auto-generated catch block
			FNFE.printStackTrace();
		}

		try {
			// load a properties file
			prop.load(input);

			// get the property value and print it out
			// System.out.println(prop.getProperty("database"));
			// System.out.println(prop.getProperty("dbuser"));
			// System.out.println(prop.getProperty("dbpassword"));
			url = prop.getProperty("dbURL");
			userName = prop.getProperty("dbUserName");
			password = prop.getProperty("dbPassword");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//DriverManager.getConnection(this.getUrl(), this.getUserName(), this.getPassword());
			conn = DriverManager.getConnection(this.getUrl(), this.getUserName(), this.getPassword());
		} catch (Exception e) {
			System.out.println(e);
		}

		return conn;
	}
	private Statement getStatement() {
	  try {
		statement = this.getConnection().createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return statement;
	}
	
	public boolean closeAllConnections() {
		return true;
	}
	
	public boolean checkUserInDB(String UNname,String pass){
		if(statement == null) {
			statement = this.getStatement();
		}
		String query = "SELECT * FROM CARTINFO WHERE username = "+UNname+" and password = "+pass;
		try {
			return this.statement.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addUserToDB(User user){
		if(statement == null) {
			statement = this.getStatement();
		}
		String query = "INSERT INTO CARTINFO" + 
				"VALUES("+user.getUserName()+","+user.getEmail()+","+user.getPassword()+","+user.getPhoneNumber()+");";
		try {
			statement.executeUpdate(query);
			this.conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
