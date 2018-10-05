package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



import org.bson.Document;
import org.modelmapper.ModelMapper;

import com.connect.Connect;
import com.dto.MasterDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;

@Path("/nutritional")
public class FindNutritionalService {
	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response search (MasterDto masterDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("solution");
		ModelMapper Mapper = new ModelMapper();
		
		
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	} 
}
