package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.modelmapper.ModelMapper;

import com.connect.Connect;
import com.dao.UserDao;
import com.dto.UserDto;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Path("user")
public class User {
	
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(UserDto userDto) {
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("user");
		
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		
		ModelMapper Mapper = new ModelMapper();
		UserDao userDao = Mapper.map(userDto, UserDao.class);
		String json = gson.toJson(userDao);
		Document document = Document.parse(json);
		
		try {
			collection.insertOne(document);
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
		MongoCollection<Document> collection = mongo.db.getCollection("user");
		ModelMapper Mapper = new ModelMapper();
		
		UserDto[] value = null;
		
		try {
			FindIterable<Document> data = collection.find();
			int size = Iterables.size(data);
			value = new UserDto[size];
			int key = 0;
			for (Document document : data) {
				value[key++] = Mapper.map(document, UserDto.class);
			}
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}finally {
			message.add("data", gson.toJsonTree(value));
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(UserDto userDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("user");
		ModelMapper Mapper = new ModelMapper();
		UserDao scoreDao = Mapper.map(userDto, UserDao.class);
		
		String json = gson.toJson(scoreDao);
		Document document = Document.parse(json);
		
		BasicDBObject setQuery = new BasicDBObject();
        setQuery.put("$set", document);
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", userDto.getId());
		
		try {
			collection.updateOne(searchQuery, setQuery);
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(UserDto userDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("user");
		
		try {
			collection.deleteOne(Filters.eq("_id", userDto.getId())); 
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/findOne")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findOne(UserDto userDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("user");
		ModelMapper Mapper = new ModelMapper();
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", userDto.getId());
		
		UserDto value = new UserDto();
		
		try {
			FindIterable<Document> data = collection.find(searchQuery);
			value = Mapper.map(data.first(), UserDto.class);
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}finally {
			message.add("data", gson.toJsonTree(value));
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response search(UserDto userDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("user");
		ModelMapper Mapper = new ModelMapper();
		
		// find when water = 'value' and seed = 'value'
		BasicDBObject query = new BasicDBObject();
			
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("name", userDto.getName()));
		obj.add(new BasicDBObject("lastname", userDto.getLastname()));
		query.put("$and", obj);
				
		UserDto[] value = null;
		
		try {
			FindIterable<Document> data = collection.find(query);
			int size = Iterables.size(data);
			value = new UserDto[size];
			int key = 0;
			for (Document document : data) {
				value[key++] = Mapper.map(document, UserDto.class);
			}
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}finally {
			message.add("data", gson.toJsonTree(value));
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
}
