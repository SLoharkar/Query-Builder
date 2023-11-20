package com.java.QueryBuilder.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ExceptionConfigurationPojo {

	Date timestamp;
	int status;
	String error;
	String description;

}
