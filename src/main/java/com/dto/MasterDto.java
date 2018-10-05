package com.dto;

public class MasterDto {
	String thname;
	String enname;
	String type;
	CompositionsDto composition;
	MineralsDto mineral;
	VitaminsDto vitamin;
	public String getThname() {
		return thname;
	}
	public void setThname(String thname) {
		this.thname = thname;
	}
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		this.enname = enname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public CompositionsDto getComposition() {
		return composition;
	}
	public void setComposition(CompositionsDto composition) {
		this.composition = composition;
	}
	public MineralsDto getMineral() {
		return mineral;
	}
	public void setMineral(MineralsDto mineral) {
		this.mineral = mineral;
	}
	public VitaminsDto getVitamin() {
		return vitamin;
	}
	public void setVitamin(VitaminsDto vitamin) {
		this.vitamin = vitamin;
	}
}
