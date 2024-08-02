package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Expression;

@Service
@Transactional
public class ExpressionServiceImplementation implements ExpressionService {

    @Autowired
    private RepositoryService repositoryService;

	@Override
	public Expression create(Expression expression) {
		return repositoryService.create(expression);
	}

	@Override
	public Expression fetchExpression(long id) {
		return repositoryService.fetchExpression(id);
	}
	
}
