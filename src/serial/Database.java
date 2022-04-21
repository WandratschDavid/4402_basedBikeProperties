package serial;

import model.Bike;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Database dient der Persistierung von Objekten in einer Datenbank.
 */
public class Database
{
	private String driver;
	private static String url;
	private static String username;
	private static String password;
	private static Database instance;
	private static Connection connection;

	/**
	 * Konstruktor.
	 */
	private Database()
	{
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
	}

	public static Database getInstance()
	{
		if (instance == null)
		{
			instance = new Database();
		}
		return instance;
	}

	/**
	 * Aufruf der Funktion open();
	 */
	public static Connection getConnection() throws SQLException
	{
		if (connection == null)
		{
			open();
		}
		return connection;
	}

	/**
	 * Herstellen der Verbindung zur Datenbank.
	 */
	private static void open() throws SQLException
	{
		connection = DriverManager.getConnection(url, username, password);
	}

	/**
	 * Schließen der Verbindung zur Datenbank.
	 */
	public void close() throws SQLException
	{
		if (!connection.isClosed())
		{
			connection.close();
		}
	}

	/**
	 * Speichert ein Objekt in der Datenbank. Existiert bereits ein gleiches, so wird das existierende zuerst entfernt.
	 * @param bike zu speicherndes Objekt
	 */
	public void save(Bike bike)
	{
		// In der Liste aller Objekte updaten oder inserten
		removeBike(bike);
		addBike(bike);
	}

	/**
	 * Added eines neuen Bikes.
	 * @param bike
	 */
	private void addBike(Bike bike)
	{
		try
		{
			Statement statement = connection.createStatement();
			String query = String.format("INSERT INTO Bike VALUES ('%s', '%s', '%s')", bike.getRahmennr(),
			                             bike.getMarkeType(), bike.getText());
			statement.execute(query);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Removen eines Bikes
	 * @param bike
	 */
	private void removeBike(Bike bike)
	{
		try
		{
			Statement statement = connection.createStatement();
			String query = String.format("DELETE FROM Bike WHERE rahmennr = '%s'", bike.getRahmennr());
			statement.execute(query);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Bike selectBikeByRahmennr(String rahmennr)
	{
		Bike found = getBikeFromQuery(rahmennr);
		return found;
	}

	/**
	 * Suche nach einem Bike in der Query
	 * @param rahmennr
	 * @return
	 */
	public Bike getBikeFromQuery(String rahmennr)
	{
		try
		{
			Statement statement = connection.createStatement();
			String query = String.format("SELECT * FROM Bike WHERE rahmennr = '%s'", rahmennr);
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next())
			{
				Bike bike = new Bike(resultSet.getString("rahmennr"));
				bike.setMarkeType(resultSet.getString("markeType"));
				bike.setText(resultSet.getString("text"));
				return bike;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}