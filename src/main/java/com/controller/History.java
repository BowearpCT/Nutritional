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
import com.dao.HistoryDao;
import com.dao.MasterDao;
import com.dto.FindHistoryDto;
import com.dto.HistoryDto;
import com.dto.MasterDto;
import com.dto.ReturnHistoryDto;
import com.dto.TypeDto;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Path("/history")
public class History {
	@Path("/record")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response record(HistoryDto historyDto) {
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("history");
		MongoCollection<Document> foodcollection = mongo.db.getCollection("master");
		
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		
		BasicDBObject query = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("thname", java.util.regex.Pattern.compile(historyDto.getFood())));
		obj.add(new BasicDBObject("enname", java.util.regex.Pattern.compile(historyDto.getFood())));
		query.put("$or", obj);
		
		MasterDto foodvalue = new MasterDto();
		
		
		try {
			FindIterable<Document> data = foodcollection.find(query);
			foodvalue = Mapper.map(data.first(), MasterDto.class);
			historyDto.setFood(foodvalue.get_id());
			String json = gson.toJson(historyDto);
			Document document = Document.parse(json);
			collection.insertOne(document);
			message.addProperty("message", "Recorded");
		}
		catch(Exception e){
			message.addProperty("message", false);
		}
		finally {
			message.add("data", gson.toJsonTree(historyDto));
		}
		
		return Response.ok(gson.toJson(message)).build();
	}
	@Path("/findAll")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAll(FindHistoryDto findhistoryDto) {
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("history");
		MongoCollection<Document> foodcollection = mongo.db.getCollection("master");		
		MongoCollection<Document> typecollection = mongo.db.getCollection("type");
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		
		
//		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
//		obj.add(new BasicDBObject("$gte",findhistoryDto.getStartDate()));
//		obj.add(new BasicDBObject("$lte",findhistoryDto.getToDate()));
//		BasicDBObject datequery = new BasicDBObject();
//		
//		List<BasicDBObject> totalobj = new ArrayList<BasicDBObject>();
//		totalobj.add(new BasicDBObject("date",obj));
//		totalobj.add(new BasicDBObject("username",findhistoryDto.getUsername()));
//		datequery.put("date", obj);
//		BasicDBObject query = new BasicDBObject();
//		query.put("$and", totalobj);
		
//		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
//		obj.add(new BasicDBObject("date",findhistoryDto.getStartDate()));
//		obj.add(new BasicDBObject("username",findhistoryDto.getUsername()));
//		BasicDBObject setquery = new BasicDBObject();
//		setquery.put("$and", obj);
		
		BasicDBObject setquery = new BasicDBObject();
		setquery.put("username",findhistoryDto.getUsername());
		
		
		HistoryDto[] value = null;
		MasterDto[] listfood = null;
		ReturnHistoryDto[] result = null;
		TypeDto[] type = null;
		
		try {
			FindIterable<Document> data = collection.find(setquery);
			FindIterable<Document> fooddata = foodcollection.find();
			FindIterable<Document> typedata = typecollection.find();
			int foodsize = Iterables.size(fooddata);
			int typesize = Iterables.size(typedata);
			int size = Iterables.size(data);
			value = new HistoryDto[size];
			type = new TypeDto[typesize];
			listfood = new MasterDto[foodsize];
			int key=0;
			for (Document document : data ) {
				value[key++] = Mapper.map(document, HistoryDto.class);
			}
			key = 0;
			for (Document document : typedata) {
				type[key++] = Mapper.map(document, TypeDto.class);
			}
			key = 0;
			for (Document document : fooddata) {
				listfood[key++] = Mapper.map(document, MasterDto.class);
			}
			for (int i=0;i<listfood.length;i++) {
				for (int j=0;j<type.length;j++) {
					if(listfood[i].getType().equals(type[j].getId())) {
						listfood[i].setType(type[j].getName());
					}
				}
			}
			result = new ReturnHistoryDto[value.length];

			for (int i=0;i<value.length;i++) {
				for (int j=0;j<listfood.length;j++) {
					if(value[i].getFood().equals(listfood[j].get_id())) {
						result[i] = new ReturnHistoryDto();
						result[i].setUsername(value[i].getUsername());
						result[i].setDate(value[i].getDate());
						result[i].setPotion(value[i].getPotion());
						result[i].setFood(Mapper.map(listfood[j], MasterDto.class));
						result[i].setVolumn(value[i].getVolumn());	
						break;
					}
				}
			}
			message.addProperty("message", true);
		}
		catch(Exception e){
			message.addProperty("message", false);
		}
		finally {
//			message.add("result", gson.toJsonTree(result));
			message.add("data", gson.toJsonTree(result));
//			message.add("food", gson.toJsonTree(listfood));
		}
		return Response.ok(gson.toJson(message),MediaType.APPLICATION_JSON).build();
	}
	@Path("/find")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response find (FindHistoryDto findhistoryDto) {
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("history");
		MongoCollection<Document> foodcollection = mongo.db.getCollection("master");		
		MongoCollection<Document> typecollection = mongo.db.getCollection("type");
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("username",findhistoryDto.getUsername()));
		obj.add(new BasicDBObject("date",findhistoryDto.getStartDate()));
		BasicDBObject setquery = new BasicDBObject();
		setquery.put("$and",obj);
		
		HistoryDto[] value = null;
		MasterDto[] listfood = null;
		ReturnHistoryDto[] result = null;
		TypeDto[] type = null;
		
		try {
			FindIterable<Document> data = collection.find(setquery);
			FindIterable<Document> fooddata = foodcollection.find();
			FindIterable<Document> typedata = typecollection.find();
			int foodsize = Iterables.size(fooddata);
			int typesize = Iterables.size(typedata);
			int size = Iterables.size(data);
			value = new HistoryDto[size];
			type = new TypeDto[typesize];
			listfood = new MasterDto[foodsize];
			int key=0;
			for (Document document : data ) {
				value[key++] = Mapper.map(document, HistoryDto.class);
			}
			key = 0;
			for (Document document : typedata) {
				type[key++] = Mapper.map(document, TypeDto.class);
			}
			key = 0;
			for (Document document : fooddata) {
				listfood[key++] = Mapper.map(document, MasterDto.class);
			}
			for (int i=0;i<listfood.length;i++) {
				for (int j=0;j<type.length;j++) {
					if(listfood[i].getType().equals(type[j].getId())) {
						listfood[i].setType(type[j].getName());
					}
				}
			}
			result = new ReturnHistoryDto[value.length+1];
			int sizeresult = result.length;
			for (int i=0;i<value.length;i++) {
				for (int j=0;j<listfood.length;j++) {
					if(value[i].getFood().equals(listfood[j].get_id())) {
						result[i] = new ReturnHistoryDto();
						result[i].setUsername(value[i].getUsername());
						result[i].setDate(value[i].getDate());
						result[i].setPotion(value[i].getPotion());
						result[i].setFood(Mapper.map(listfood[j], MasterDto.class));
						result[i].setVolumn(value[i].getVolumn());	
						break;
					}
				}
			}
			message.addProperty("message", true);
		}
		catch(Exception e){
			message.addProperty("message", false);
		}
		finally {
//			message.add("result", gson.toJsonTree(result));
			message.add("data", gson.toJsonTree(result));
//			message.add("food", gson.toJsonTree(listfood));
		}
		return Response.ok(gson.toJson(message),MediaType.APPLICATION_JSON).build();
	}
	
}
