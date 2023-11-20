package com.java.QueryBuilder.models;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GetDocumentBean {

	@NotNull(message = "select not be null")
	Map<String, String> select;

	@NotNull(message = "where not be null")
	List<String> where;

	@NotNull(message = "pagination not be null")
	String pagination;

}
