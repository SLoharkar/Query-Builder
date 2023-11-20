package com.java.QueryBuilder.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.java.QueryBuilder.models.GetDocumentBean;
import com.java.QueryBuilder.services.QueryBuilderService;

@RestController
public class QueryBuilderController {

	@Autowired
	QueryBuilderService queryBuilderService;

	@RequestMapping("/")
	public String Home() {
		return "Welcome to MongoDB Query Builder";
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/getDocument")
	public Map<String, Object> getDocuments(@Valid @RequestBody GetDocumentBean getDocumentBean) {

		return queryBuilderService.getDocument(getDocumentBean);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/add")
	public void addData() {
		queryBuilderService.addData();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete")
	public void deleteData() {
		queryBuilderService.deleteData();
	}

}
