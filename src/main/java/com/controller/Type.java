package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.modelmapper.ModelMapper;

import com.connect.Connect;
import com.dao.MasterDao;
import com.dao.TypeDao;
import com.dto.MasterDto;
import com.dto.TypeDto;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Path("/type")
public class Type {
	@Path("/insert")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(TypeDto typeDto) {
		
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("type");
		
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		
		TypeDao nametype = Mapper.map(typeDto ,TypeDao.class );
		
		String json = gson.toJson(nametype);
		Document document = Document.parse(json);
		
		try {
			collection.insertOne(document);
			message.addProperty("message", true);
		}
		catch(Exception e) {
			message.addProperty("message", false);
		}
		
		return Response.ok(gson.toJson(message),MediaType.APPLICATION_JSON).build();
	}
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(TypeDto typeDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("type");
		
		TypeDao typeDao = new TypeDao();
		typeDao.setName(typeDto.getName());
		
		String json = gson.toJson(typeDao);
		Document document = Document.parse(json);
		
		BasicDBObject setQuery = new BasicDBObject();
        setQuery.put("$set", document);
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", typeDto.getId());
		
		try {
			collection.updateOne(searchQuery, setQuery);
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/findAll")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAll() {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("type");
		ModelMapper Mapper = new ModelMapper();
		
		TypeDto[] value = null;
		
		try {
			FindIterable<Document> data = collection.find();
			int size = Iterables.size(data);
			value = new TypeDto[size];
			int key = 0;
			for (Document document : data) {
				value[key++] = Mapper.map(document, TypeDto.class);
			}
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}finally {
			message.add("data", gson.toJsonTree(value));
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(TypeDto typeDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("type");
		
		try {
			collection.deleteOne(Filters.eq("_id", typeDto.getId())); 
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	
}
