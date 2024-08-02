package com.example.demo.service;

import com.example.demo.model.Expression;

public interface ExpressionService {
	public Expression create(Expression expression);
	public Expression fetchExpression(long id);
}