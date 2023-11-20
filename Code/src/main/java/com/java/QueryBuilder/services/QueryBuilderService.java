package com.java.QueryBuilder.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.QueryBuilder.exceptions.DataNotFoundException;
import com.java.QueryBuilder.models.GetDocumentBean;
import com.java.QueryBuilder.repository.QueryBuilderRepository;

@Service
public class QueryBuilderService {

	@Autowired
	QueryBuilderRepository queryBuilderRepository;

	public Map<String, Object> getDocument(GetDocumentBean getDocumentBean) {

		// Select Operation
		JSONObject selectJsonObject = selectFunction(getDocumentBean.getSelect());

		// Where Operation
		JSONObject whereJsonObject = whereFunction(getDocumentBean.getWhere());

		// Pagination Start
		String index[] = getDocumentBean.getPagination().split(",");
		int skip = Integer.parseInt(index[0]);
		int limit = Integer.parseInt(index[1]);

		// Pass JSONObject Query to Repository
		JSONObject queryResult = queryBuilderRepository.executeQuery(selectJsonObject, whereJsonObject, skip, limit);

		// Convert Json Query Result to Map
		Map<String, Object> result = queryResult.toMap();

		return result;

	}

	public JSONObject selectFunction(Map<String, String> select) {

		JSONObject docSelectJSON = new JSONObject();

		for (Map.Entry<String, String> map : select.entrySet()) {

			String databaseFieldName = "$" + map.getKey();
			if (!map.getKey().equals("_id")) {
				String aliasFieldName = map.getValue();
				docSelectJSON.put(aliasFieldName, databaseFieldName);
			}

		}
		return docSelectJSON;

	}

	public JSONObject whereFunction(List<String> where) {

		String field;
		String value;

		JSONObject condition = new JSONObject();
		JSONObject conditionFilter = new JSONObject();

		for (String iterate : where) {

			String splitarr[] = iterate.split("\\^");

			if (splitarr.length >= 3) {

				for (int i = 0; i < splitarr.length; i++) {

					String temp = splitarr[i];

					switch (temp) {

						case "=":

							field = splitarr[i - 1];
							value = splitarr[i + 1];

							conditionFilter.put(field, value);
							break;
						case "==":

							field = splitarr[i - 1];
							value = splitarr[i + 1];

							condition.put("$eq", value);
							conditionFilter.put(field, condition);
							break;
						case "IN":

							field = splitarr[i - 1];
							ArrayList<String> arrlist = new ArrayList<>();

							int j = i + 1;
							while (j < splitarr.length) {
								value = splitarr[j];
								arrlist.add(value);
								j++;
							}
							condition.put("$in", arrlist);
							conditionFilter.put(field, condition);

							break;
						case "NOT IN":

							field = splitarr[i - 1];
							ArrayList<String> arrlist1 = new ArrayList<>();

							int k = i + 1;
							while (k < splitarr.length) {
								value = splitarr[k];
								arrlist1.add(value);
								k++;
							}
							condition.put("$nin", arrlist1);
							conditionFilter.put(field, condition);

							break;
						case "Between":
							String value2;
							field = splitarr[i - 1];
							value = splitarr[i + 1];
							value2 = splitarr[i + 3];

							condition.put("$gte", value);
							condition.put("$lte", value2);
							conditionFilter.put(field, condition);
							break;
						case "!=":

							field = splitarr[i - 1];
							value = splitarr[i + 1];

							condition.put("$ne", value);
							conditionFilter.put(field, condition);
							break;
						case ">":
							field = splitarr[i - 1];
							value = splitarr[i + 1];

							condition.put("$gt", value);
							conditionFilter.put(field, condition);
							break;
						case "<":
							field = splitarr[i - 1];
							value = splitarr[i + 1];

							condition.put("$lt", value);
							conditionFilter.put(field, condition);
							break;
						case "<=":
							field = splitarr[i - 1];
							value = splitarr[i + 1];

							condition.put("$lte", value);
							conditionFilter.put(field, condition);
							break;
						case ">=":
							field = splitarr[i - 1];
							value = splitarr[i + 1];

							condition.put("$gte", value);
							conditionFilter.put(field, condition);
							break;

					}
				}
			} else {
				throw new DataNotFoundException("Wrong Query");
			}

		}
		return conditionFilter;

	}

	// Delete All Data in Database
	public void deleteData() {
		queryBuilderRepository.deleteData();
	}

	// Add All Predefined Data in Database
	public void addData() {
		queryBuilderRepository.addData();

	}

}
