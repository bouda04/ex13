package com.example.testsql.data;

import android.graphics.Color;

public class Item {
	private long id;
	private int number;
	private int color;
	
	public Item(int number, int color) {
		this(-1,number, color);
	}

	public Item(long id, int number, int color) {
		this.id =id;
		this.number = number;
		this.color = color;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long l) {
		this.id = l;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
}
