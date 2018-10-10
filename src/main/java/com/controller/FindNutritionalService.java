package com.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.google.common.collect.Iterables;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
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
		MongoCollection<Document> collection = mongo.db.getCollection("master");
		ModelMapper Mapper = new ModelMapper();
		
		BasicDBObject query = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("thname", java.util.regex.Pattern.compile(masterDto.getThname())));
		obj.add(new BasicDBObject("enname", java.util.regex.Pattern.compile(masterDto.getThname())));
		query.put("$or", obj);
		
		
		MasterDto[] value = null;
		
		try {
			FindIterable<Document> data = collection.find(query);
			int size = Iterables.size(data);
			value = new MasterDto[size];
			int key = 0;
			for (Document document : data) {
				value[key++] = Mapper.map(document, MasterDto.class);
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
