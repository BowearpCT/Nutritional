package com.dto;

import org.bson.types.ObjectId;

public class TypeDto {
	String id;
	String name;
	String _id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ObjectId get_id() {
		return new ObjectId(_id);
	}
	public void set_id(String _id) {
		this._id = _id;
	}
}
