package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Expression;
import com.example.demo.model.ExpressionResult;
import com.example.demo.service.ExpressionService;

@RestController
@RequestMapping("v1")
public class ExpressionController {

	@Autowired
	private ExpressionService service;
	
	@PostMapping("/expression")
	@ResponseStatus(HttpStatus.CREATED)
	public ExpressionResult create(@RequestBody Expression expression) {
		Expression storedExpression = service.create(expression); 
		return new ExpressionResult(storedExpression.getId());
	}

}