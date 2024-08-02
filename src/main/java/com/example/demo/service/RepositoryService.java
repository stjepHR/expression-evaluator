package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.Expression;
import com.example.demo.repository.ExpressionRepository;

@Service
public class RepositoryService {

    private final ExpressionRepository expressionRepository;

    public RepositoryService(ExpressionRepository expressionRepository) {
        this.expressionRepository = expressionRepository;
    }
	
	public Expression create(Expression expression) {
		return expressionRepository.save(expression);
	}
	
	public Expression fetchExpression(long id) {
		return expressionRepository.findById(id).get();
	}
    
}
