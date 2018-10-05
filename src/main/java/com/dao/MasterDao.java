package com.dao;

public class MasterDao {
	String thname;
	String enname;
	String type;
	CompositionsDao composition;
	MineralsDao mineral;
	VitaminsDao vitamin;
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
	public CompositionsDao getComposition() {
		return composition;
	}
	public void setComposition(CompositionsDao composition) {
		this.composition = composition;
	}
	public MineralsDao getMineral() {
		return mineral;
	}
	public void setMineral(MineralsDao mineral) {
		this.mineral = mineral;
	}
	public VitaminsDao getVitamin() {
		return vitamin;
	}
	public void setVitamin(VitaminsDao vitamin) {
		this.vitamin = vitamin;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
