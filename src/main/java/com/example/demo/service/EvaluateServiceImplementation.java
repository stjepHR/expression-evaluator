package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.evaluator.ExpressionEvaluator;
import com.example.demo.model.Expression;
import com.fasterxml.jackson.databind.JsonNode;

@Service
@Transactional
public class EvaluateServiceImplementation implements EvaluateService {

    @Autowired
    private RepositoryService repositoryService;
	
	@Override
	public Boolean evaluate(long id, JsonNode requestBody) {
		Expression expression = repositoryService.fetchExpression(id);
		//System.out.println(expression.toString());
        return ExpressionEvaluator.evaluate(requestBody, expression.getExpressionValue().trim());
	}
}
