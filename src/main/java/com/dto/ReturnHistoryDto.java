package com.dto;

public class ReturnHistoryDto {
	String username;
	String date;
	String potion; 
	MasterDto food;
	double volumn;
	
	public String getUsername() {
		return username;
	}
	public String getDate() {
		return date;
	}
	public String getPotion() {
		return potion;
	}
	public double getVolumn() {
		return volumn;
	}
	public MasterDto getFood() {
		return food;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setPotion(String potion) {
		this.potion = potion;
	}
	public void setVolumn(double volumn) {
		this.volumn = volumn;
	}
	public void setFood(MasterDto food) {
		this.food = food;
	}
	
}
