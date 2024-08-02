package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.EvaluateResult;
import com.example.demo.service.EvaluateService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("v1")
public class EvaluateController {

	@Autowired
	private EvaluateService service;
	
	@PostMapping("/evaluate/{id}")
	public EvaluateResult evaluate(@PathVariable String id, @RequestBody JsonNode requestBody) {
		Boolean result = service.evaluate(Long.valueOf(id), requestBody);
		return new EvaluateResult(result);
	}

}