package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import serial.Database;

public class Bike
{
	private StringProperty rahmennr = new SimpleStringProperty();
	private StringProperty markeType = new SimpleStringProperty();
	private StringProperty text = new SimpleStringProperty();

	public Bike(String rahmennr)
	{
		setRahmennr(rahmennr);
	}

	public Bike()
	{
		this("");
	}

	public Bike(Bike bike)
	{
		setRahmennr(bike.getRahmennr());
		setMarkeType(bike.getMarkeType());
		setText(bike.getText());
	}

	public static Bike select(String rahmennr)
	{
		Bike bike = Database.getInstance().selectBikeByRahmennr(rahmennr);

		if (bike != null)
		{
			// Bike vorhanden
			// Kopie des Objekts zurückgeben, um cancel() zu ermöglichen
			return new Bike(bike);
		}
		else
		{
			// Neues Bike
			// Leeres Bike mit Schlüssel zurückgeben
			return new Bike(rahmennr);
		}
	}

	// Generated Getters & Setters
	public String getRahmennr()
	{
		return rahmennr.get();
	}

	public StringProperty rahmennrProperty()
	{
		return rahmennr;
	}

	public void setRahmennr(String rahmennr)
	{
		this.rahmennr.set(rahmennr);
	}

	public String getMarkeType()
	{
		return markeType.get();
	}

	public StringProperty markeTypeProperty()
	{
		return markeType;
	}

	public void setMarkeType(String markeType)
	{
		this.markeType.set(markeType);
	}

	public String getText()
	{
		return text.get();
	}

	public StringProperty textProperty()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text.set(text);
	}


	private void fillAndKill() throws BikeExecption
	{
		if (rahmennr.get() == null)
		{
			rahmennr.set("");
		}
		if (rahmennr.get().length() < 5)
		{
			throw new BikeExecption("Rahmennummer muss zumindest 5 Stellen haben!");
		}

		if (markeType.get() == null)
		{
			markeType.set("");
		}
		if (markeType.get().length() < 3)
		{
			throw new BikeExecption("Marke und Type muss zumindest 3 Stellen haben!");
		}

		if (text.get() == null)
		{
			text.set("");
		}
	}

	public void save() throws BikeExecption
	{
		fillAndKill();
		Database.getInstance().save(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		Bike bike = (Bike) o;

		return getRahmennr().equals(bike.getRahmennr());
	}
}