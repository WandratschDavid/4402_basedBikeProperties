package main;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseSetup
{
	public static void main(String[] args)
	{
		String driver;
		String url;
		String username;
		String password;

		try (FileInputStream in = new FileInputStream("dbconnect.properties"))
		{
			Properties prop = new Properties();
			prop.load(in);

			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}

		try (Connection connection = DriverManager.getConnection(url, username, password))
		{
			Statement statement = connection.createStatement();
			statement.execute("DROP TABLE Bike IF EXISTS");
			statement.execute(
					"CREATE TABLE Bike(rahmennr VARCHAR(25) NOT NULL PRIMARY KEY, markeType VARCHAR(25) NOT NULL, text VARCHAR(25))");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}