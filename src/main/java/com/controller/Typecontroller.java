package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.modelmapper.ModelMapper;

import com.connect.Connect;
import com.dao.TypeDao;
import com.dao.VegetablesDao;
import com.dto.TypeDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;

@Path("/type")
public class Typecontroller {
	@Path("/insert")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert (TypeDto typeDto) {
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("type");
		
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		
		TypeDao typeDao = Mapper.map(typeDto ,TypeDao.class );
		
		String json = gson.toJson(typeDto);
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
