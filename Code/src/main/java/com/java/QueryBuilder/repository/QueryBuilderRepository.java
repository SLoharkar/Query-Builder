package com.java.QueryBuilder.repository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.java.QueryBuilder.database.DatabaseOperation;

@Repository
public class QueryBuilderRepository {

	@Autowired
	DatabaseOperation databaseOperation;

	public void deleteData() {
		databaseOperation.deleteData();
	}

	public void addData() {
		databaseOperation.addData();
	}

	public JSONObject executeQuery(JSONObject selectJsonObject, JSONObject whereJsonObject, int skip, int limit) {
		JSONObject queryResult = databaseOperation.executeQuery(selectJsonObject, whereJsonObject, skip, limit);

		return queryResult;

	}

}
