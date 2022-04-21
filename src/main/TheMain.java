package main;

import controllerview.BikeC;
import javafx.application.Application;
import javafx.stage.Stage;
import serial.Database;

import java.sql.SQLException;

public class TheMain extends Application
{
	@Override
	public void init() throws SQLException
	{
		Database.getInstance().getConnection();
	}

	@Override
	public void start(Stage primaryStage)
	{
		BikeC.show(primaryStage);
	}

	@Override
	public void stop() throws SQLException
	{
		Database.getInstance().close();
	}
}