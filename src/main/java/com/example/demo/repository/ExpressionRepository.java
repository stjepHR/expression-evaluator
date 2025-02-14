package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Expression;

@Repository
public interface ExpressionRepository extends CrudRepository<Expression, Long> {
	
}
