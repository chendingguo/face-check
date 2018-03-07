package com.mtime.demo.model;

import java.util.List;

import lombok.Data;
@Data
public class ResultDataModel<T>{
	long total;
	List<T> rows;

	
}
