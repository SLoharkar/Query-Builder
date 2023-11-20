package com.java.QueryBuilder.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Component
public class DatabaseConnnection {

	@Autowired
	private Environment springEnvironment;

	private MongoClient mongoClient;

	public void initializeDatabaseProperties() {

		String url = springEnvironment.getProperty("spring.data.mongodb.uri");

		mongoClient = MongoClients.create(url);

	}

	public MongoClient getConnection() {
		if (mongoClient == null) {
			this.initializeDatabaseProperties();
		}
		return mongoClient;

	}

}
