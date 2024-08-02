package com.example.demo.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface EvaluateService {
	public Boolean evaluate(long id, JsonNode requestBody);
}