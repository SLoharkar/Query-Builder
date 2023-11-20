package com.java.QueryBuilder.database;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Component
public class DatabaseOperation {

	@Autowired
	DatabaseConnnection databaseConnection;

	@Value("${spring.data.mongodb.database}")
	private String featuresDatabase;

	private MongoClient mongoClient;

	private MongoDatabase mongoDatabase;

	private MongoCollection<Document> mongoCollection;

	public void deleteData() {
		mongoClient = databaseConnection.getConnection();
		mongoDatabase = mongoClient.getDatabase(featuresDatabase);
		mongoCollection = mongoDatabase.getCollection("default");

		mongoCollection.deleteMany(Filters.empty());
	}

	public void addData() {
		mongoClient = databaseConnection.getConnection();
		mongoDatabase = mongoClient.getDatabase(featuresDatabase);
		mongoCollection = mongoDatabase.getCollection("default");

		for (int i = 1; i <= 10; i++) {
			double random = Math.random() * 1000 + 1000;
			int number = (int) random;
			String sal = String.valueOf(number);
			String id = String.valueOf(i);

			Document document = new Document();
			document.put("_id", id);
			document.put("name", "Sam" + i);
			document.put("age", "3" + i);
			document.put("salary", sal);
			document.put("occupation", "SDE" + i);

			mongoCollection.insertOne(document);

		}

	}

	public JSONObject executeQuery(JSONObject selectJsonObject, JSONObject whereJsonObject, int skip, int limit) {

		mongoClient = databaseConnection.getConnection();

		mongoDatabase = mongoClient.getDatabase(featuresDatabase);

		mongoCollection = mongoDatabase.getCollection("default");

		// Convert JSON to Document
		Document fetchDataDoc = Document.parse(selectJsonObject.toString());

		// Disable _id From Client
		fetchDataDoc.put("_id", 0);

		// Convert JSON to Document
		Document conditionDoc = Document.parse(whereJsonObject.toString());

		List<Document> docResult = mongoCollection.find(conditionDoc).projection(fetchDataDoc).skip(skip).limit(limit)
				.into(new ArrayList<>());

		JSONObject queryResult = new JSONObject();

		queryResult.put("Data", docResult);

		return queryResult;
	}

}
