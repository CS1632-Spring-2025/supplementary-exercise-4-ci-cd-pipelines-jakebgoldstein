package edu.pitt.cs;

public class CatImpl implements Cat 
{
	private int id;
    private String name;
    private boolean isRented;

	public CatImpl(int id, String name) 
	{
		this.id = id;
		this.name = name;
		this.isRented = false;
	}

	public void rentCat() 
	{
		isRented = true;
	}

	public void returnCat() 
	{
		isRented = false;
	}

	public void renameCat(String inputName) 
	{
		name = inputName;
	}

	public String getName() 
	{
		return name;
	}

	public int getId() 
	{
		return id;
	}

	public boolean getRented() 
	{
		if(isRented)
		{
			return true;
		}
		return false;
	}

	public String toString() 
	{
		return "ID " + getId() + ". " + getName();
	}

}