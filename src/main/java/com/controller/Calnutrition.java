package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.modelmapper.ModelMapper;

import com.connect.Connect;
import com.dto.CalnutritionDto;
import com.dto.MasterDto;
import com.dto.UserDto;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Path("/calnutritional")
public class Calnutrition {
	@POST
	@Path("/findOne")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findOne(CalnutritionDto MasterDto) {
		
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("master");
		ModelMapper Mapper = new ModelMapper();
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("type", 1);
		searchQuery.put("cereals",MasterDto.getCerealscal());
		
		MasterDto value = new MasterDto();
		FindIterable<Document> data = collection.find(searchQuery);
		value = Mapper.map(data.first(), MasterDto.class);
		
		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("type", 2);
		searchQuery1.put("plants",MasterDto.getPlantscal());
		
		MasterDto value1 = new MasterDto();
		FindIterable<Document> data1 = collection.find(searchQuery1);
		value1 = Mapper.map(data1.first(), MasterDto.class);
				
		BasicDBObject searchQuery2 = new BasicDBObject();
		searchQuery2.put("type", 3);
		searchQuery2.put("seedlings",MasterDto.getSeedlingscal());
		
		MasterDto value2 = new MasterDto();
		FindIterable<Document> data2 = collection.find(searchQuery2);
		value2 = Mapper.map(data2.first(), MasterDto.class);
		
		BasicDBObject searchQuery3 = new BasicDBObject();
		searchQuery3.put("type", 2);
		searchQuery3.put("vegetable",MasterDto.getVegetablecal());
		
		MasterDto value3 = new MasterDto();
		FindIterable<Document> data3 = collection.find(searchQuery3);
		value3 = Mapper.map(data3.first(), MasterDto.class);
		
		
		BasicDBObject searchQuery4 = new BasicDBObject();
		searchQuery4.put("type", 4);
		searchQuery4.put("fruit",MasterDto.getFruitcal());
		
		MasterDto value4 = new MasterDto();
		FindIterable<Document> data4 = collection.find(searchQuery4);
		value4 = Mapper.map(data4.first(), MasterDto.class);
		
		BasicDBObject searchQuery5 = new BasicDBObject();
		searchQuery5.put("type", 5);
		searchQuery5.put("meat",MasterDto.getMeatcal());
		
		MasterDto value5 = new MasterDto();
		FindIterable<Document> data5 = collection.find(searchQuery5);
		value5 = Mapper.map(data5.first(), MasterDto.class);
		
		
		BasicDBObject searchQuery6 = new BasicDBObject();
		searchQuery6.put("type", 6);
		searchQuery6.put("aquatic",MasterDto.getAquaticcal());
		
		MasterDto value6 = new MasterDto();
		FindIterable<Document> data6 = collection.find(searchQuery6);
		value6 = Mapper.map(data6.first(), MasterDto.class);
		
		BasicDBObject searchQuery7 = new BasicDBObject();
		searchQuery7.put("type", 7);
		searchQuery7.put("egg",MasterDto.getEggcal());
		
		MasterDto value7 = new MasterDto();
		FindIterable<Document> data7 = collection.find(searchQuery7);
		value7 = Mapper.map(data7.first(), MasterDto.class);
		
		BasicDBObject searchQuery8 = new BasicDBObject();
		searchQuery8.put("milk", 8);
		searchQuery8.put("cereals",MasterDto.getCerealscal());
		
		MasterDto value8 = new MasterDto();
		FindIterable<Document> data8 = collection.find(searchQuery8);
		value8 = Mapper.map(data8.first(), MasterDto.class);
		
		BasicDBObject searchQuery9 = new BasicDBObject();
		searchQuery9.put("type", 9);
		searchQuery9.put("seasoningmachine",MasterDto.getSeasoningmachinecal());
		
		MasterDto value9 = new MasterDto();
		FindIterable<Document> data9 = collection.find(searchQuery9);
		value9 = Mapper.map(data9.first(), MasterDto.class);
		
		
		try {
			FindIterable<Document> data11 = collection.find(searchQuery);
			value = Mapper.map(data11.first(), MasterDto.class);
			
			FindIterable<Document> data12 = collection.find(searchQuery1);
			value = Mapper.map(data12.first(), MasterDto.class);
			
			FindIterable<Document> data13 = collection.find(searchQuery2);
			value = Mapper.map(data1.first(), MasterDto.class);
			
			FindIterable<Document> data14 = collection.find(searchQuery3);
			value = Mapper.map(data14.first(), MasterDto.class);
			
			FindIterable<Document> data15 = collection.find(searchQuery4);
			value = Mapper.map(data15.first(), MasterDto.class);
			
			FindIterable<Document> data16 = collection.find(searchQuery5);
			value = Mapper.map(data16.first(), MasterDto.class);
			
			FindIterable<Document> data17 = collection.find(searchQuery6);
			value = Mapper.map(data17.first(), MasterDto.class);
			
			FindIterable<Document> data18 = collection.find(searchQuery7);
			value = Mapper.map(data18.first(), MasterDto.class);
			
			FindIterable<Document> data19 = collection.find(searchQuery8);
			value = Mapper.map(data19.first(), MasterDto.class);
			
			FindIterable<Document> data20 = collection.find(searchQuery9);
			value = Mapper.map(data20.first(), MasterDto.class);
			
			
			
			
			
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}finally {
			message.add("data", gson.toJsonTree(value));
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
		
		
	}
		
}
