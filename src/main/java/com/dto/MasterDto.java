package com.dto;

import org.bson.types.ObjectId;

public class MasterDto {
	String id;
	String _id;
	String thname;
	String enname;
	String type;
	public CompositionsDto composition;
	public MineralsDto mineral;
	public VitaminsDto vitamin;
	public ObjectId getId() {
		return new ObjectId(id);
	}
	public void setId(String id) {
		this.id = id;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
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
