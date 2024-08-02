package com.example.demo.evaluator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
public class ExpressionEvaluatorTest {

	private ObjectNode prepareDataSample() {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode mainObjectNode = objectMapper.createObjectNode();
		ObjectNode customerNode = objectMapper.createObjectNode();
		ObjectNode addressNode = objectMapper.createObjectNode();
		
		customerNode.put("firstName", "MARK");
		customerNode.put("lastName", "DOE");
		customerNode.put("salary", 99);
		customerNode.put("type", "BUSINESS");
		
		addressNode.put("city", "Chicago");
		addressNode.put("zipCode", 1234);
		addressNode.put("street", "56th");
		addressNode.put("houseNumber", 2345);
		
		customerNode.set("address", addressNode);
		mainObjectNode.set("customer", customerNode);
		
		return mainObjectNode;
	}
	
	@Test
	void checkAssignmentFullExample() {
		String expression = "(customer.firstName == \"MARK\" AND customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")";
		assertTrue(ExpressionEvaluator.evaluate(prepareDataSample(), expression));
	}
	
	@Test
	void checkParenthesesHandling() {
		String expression = "(((customer.firstName == \"MARK\")) AND (((customer.salary < 100))))";
		assertTrue(ExpressionEvaluator.evaluate(prepareDataSample(), expression));
	}
	
	@Test
	void checkLogicalOr() {
		String expression = "customer.firstName == \"MARK\" OR customer.salary == 100";
		assertTrue(ExpressionEvaluator.evaluate(prepareDataSample(), expression));
	}
	
	@Test
	void checkLogicalAnd() {
		String expression = "customer.firstName == \"MARK\" AND customer.salary == 100";
		assertFalse(ExpressionEvaluator.evaluate(prepareDataSample(), expression));
	}
	
	@Test
	void checkNull() {
		String expression = "customer.firstName == null";
		ObjectNode objectNode = prepareDataSample();
		ObjectNode customerNode = (ObjectNode) objectNode.get("customer");

		// Actual null value
		customerNode.putNull("firstName");
		assertTrue(ExpressionEvaluator.evaluate(objectNode, expression));
		
		// String value "null"
		customerNode.put("firstName", "null");
		assertFalse(ExpressionEvaluator.evaluate(objectNode, expression));
		
	}
	
	@Test
	void checkNotExisting() {
		String expression = "customer.firstName == \"MARK\"";
		ObjectNode objectNode = prepareDataSample();

		objectNode.putNull("customer");
		assertFalse(ExpressionEvaluator.evaluate(objectNode, expression));
	}

}
