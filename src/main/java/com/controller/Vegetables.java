package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.modelmapper.ModelMapper;

import com.connect.Connect;
import com.dao.VegetablesDao;
import com.dto.VegetablesDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;

@Path("/master")
public class Vegetables {

	@Path("/insert")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(VegetablesDto vegetablesDto) {
		
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("master");
		
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		
		VegetablesDao vegetablesDao = Mapper.map(vegetablesDto ,VegetablesDao.class );
		
		String json = gson.toJson(vegetablesDto);
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

}
