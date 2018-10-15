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

import com.dto.MasterDto;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@Path("/master")
public class Master {

	@Path("/insert")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(MasterDto vegetablesDto) {
		
		Connect mongo = new Connect();
		MongoCollection<Document> collection = mongo.db.getCollection("master");
		
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		ModelMapper Mapper = new ModelMapper();
		
		MasterDao vegetablesDao = Mapper.map(vegetablesDto ,MasterDao.class );
		
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
	@POST
	@Path("/findOne")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findOne(MasterDto masterDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("master");
		ModelMapper Mapper = new ModelMapper();
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", masterDto.getId());
		
		MasterDto value = new MasterDto();
		
		try {
			FindIterable<Document> data = collection.find(searchQuery);
			value = Mapper.map(data.first(), MasterDto.class);
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}finally {
			message.add("data", gson.toJsonTree(value));
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
		MongoCollection<Document> collection = mongo.db.getCollection("master");
		ModelMapper Mapper = new ModelMapper();
		
		MasterDto[] value = null;
		
		try {
			FindIterable<Document> data = collection.find();
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
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(MasterDto masterDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("master");
		ModelMapper Mapper = new ModelMapper();
		MasterDao masterDao = Mapper.map(masterDto, MasterDao.class);
		
		
//		MasterDao masterDao = new MasterDao();
//		masterDao.setThname(masterDto.getThname());
//		masterDao.setEnname(masterDto.getEnname());
//		masterDao.setType(masterDto.getType());
//		masterDao.composition.setEnergy(masterDto.composition.getEnergy());
//		masterDao.composition.setWater(masterDto.composition.getWater());
//		masterDao.composition.setProtein(masterDto.composition.getProtein());
//		masterDao.composition.setFat(masterDto.composition.getFat());
//		masterDao.composition.setCarbohydrate(masterDto.composition.getCarbohydrate());
//		masterDao.composition.setFiber(masterDto.composition.getFiber());
//		masterDao.composition.setAsh(masterDto.composition.getAsh());
//		masterDao.mineral.setCalsium(masterDto.mineral.getCalsium());
//		masterDao.mineral.setIron(masterDto.mineral.getIron());
//		masterDao.mineral.setPhosphorus(masterDto.mineral.getPhosphorus());
//		masterDao.vitamin.setRetinol(masterDto.vitamin.getRetinol());
//		masterDao.vitamin.setBetacarotene(masterDto.vitamin.getBetacarotene());
//		masterDao.vitamin.setVitaminA(masterDto.vitamin.getVitaminA());
//		masterDao.vitamin.setVitaminE(masterDto.vitamin.getVitaminE());
//		masterDao.vitamin.setThiamin(masterDto.vitamin.getThiamin());
//		masterDao.vitamin.setRiboflavin(masterDto.vitamin.getRiboflavin());
//		masterDao.vitamin.setNiacin(masterDto.vitamin.getNiacin());
//		masterDao.vitamin.setVitaminC(masterDto.vitamin.getVitaminC());

		String json = gson.toJson(masterDao);
		Document document = Document.parse(json);
		
		BasicDBObject setQuery = new BasicDBObject();
        setQuery.put("$set", document);
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", masterDto.getId());
		
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
	public Response delete(MasterDto masterDto) {
		Connect mongo = new Connect();
		JsonObject message = new JsonObject();
		Gson gson = new Gson();
		MongoCollection<Document> collection = mongo.db.getCollection("master");
		
		try {
			collection.deleteOne(Filters.eq("_id", masterDto.getId())); 
			message.addProperty("message", true);
		}catch (Exception e) {
			message.addProperty("message", false);
		}
		
		return Response.ok(gson.toJson(message), MediaType.APPLICATION_JSON).build();
	}
	

}
