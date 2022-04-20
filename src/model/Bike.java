package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import serial.Catalog;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Bike implements Externalizable
{
	private StringProperty rahmennr = new SimpleStringProperty();
	private StringProperty markeType = new SimpleStringProperty();
	private StringProperty farbe = new SimpleStringProperty();

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
		setFarbe(bike.getFarbe());
	}

	public static Bike select(String rahmennr)
	{
		Bike bike = Catalog.getInstance().selectBikeByRahmennr(rahmennr);

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

	public String getFarbe()
	{
		return farbe.get();
	}

	public StringProperty farbeProperty()
	{
		return farbe;
	}

	public void setFarbe(String farbe)
	{
		this.farbe.set(farbe);
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

		if (farbe.get() == null)
		{
			farbe.set("");
		}
	}

	public void save() throws BikeExecption
	{
		fillAndKill();
		Catalog.getInstance().save(this);
	}

	@Override
	public String toString()
	{
		return "Bike{" +
				"rahmennr='" + rahmennr + '\'' +
				", markeType='" + markeType + '\'' +
				", farbe='" + farbe + '\'' +
				'}';
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

	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeObject(getRahmennr());
		out.writeObject(getMarkeType());
		out.writeObject(getFarbe());
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		setRahmennr((String) in.readObject());
		setMarkeType((String) in.readObject());
		setFarbe((String) in.readObject());
	}
}