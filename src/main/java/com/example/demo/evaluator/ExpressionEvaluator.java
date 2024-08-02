package com.example.demo.evaluator;

import com.fasterxml.jackson.databind.JsonNode;

public class ExpressionEvaluator {

	public static Boolean evaluate(JsonNode jsonNode, String expression) {
		return parseExpression(jsonNode, expression);
	}

	private static Boolean parseExpression(JsonNode jsonNode, String expression) {
    	
    	// Checking if parentheses should be removed
		if (expression.startsWith("(") && expression.endsWith(")")) {
			boolean shouldRemoveParentheses = true;
			int parenthesesLevel = 0;
			for (int i = 0; i < expression.length(); i++) {
				if (expression.charAt(i) == '(') parenthesesLevel++;
				if (expression.charAt(i) == ')') parenthesesLevel--;
				if (parenthesesLevel == 0 && i < (expression.length() - 1)) {
					shouldRemoveParentheses = false;
					break;
				}
			}
			if (shouldRemoveParentheses) {
				// Recursion to solve expression within the parentheses
				return parseExpression(jsonNode, expression.substring(1, expression.length() - 1).trim());
			}
		}

		int operatorPosition = getOperatorPosition(expression);
		if (operatorPosition == -1) {
			// No AND or OR operator in the expression
			return evaluateCondition(expression, jsonNode);
		}
		Integer operatorLength = 2;
		if (expression.substring(operatorPosition, operatorPosition + 3).equals("AND")) operatorLength = 3;

		// Get the operator
		String operator = expression.substring(operatorPosition, operatorPosition + operatorLength).trim();
		String left = expression.substring(0, operatorPosition).trim();
		String right = expression.substring(operatorPosition + operatorLength).trim();
		
		// Recursion to solve logical ANDs and ORs
		if (operator.equals("&&") || operator.equals("AND")) {
			return parseExpression(jsonNode, left) && parseExpression(jsonNode, right);
		}
		else if (operator.equals("||") || operator.equals("OR")) {
			return parseExpression(jsonNode, left) || parseExpression(jsonNode, right);
		}
		else {
			throw new IllegalArgumentException("Invalid operator: " + operator);
		}
		
    }

	/*
	 * Expressions can contain multiple nested parentheses.
	 * This method returns position of the highest precedence operator (parentheses level will be zero). 
	 */
    private static Integer getOperatorPosition(String expression) {
    	int parenthesesLevel = 0;
    	for (int i = 0; i < expression.length() - 2; i++) {
    		if (expression.charAt(i) == '(') {
    			parenthesesLevel++;
    		} else if (expression.charAt(i) == ')') {
    			parenthesesLevel--;
    		} else if (parenthesesLevel == 0) {
    			String tmpSubstring = expression.substring(i, i + 2); 
    			if (
    					tmpSubstring.equals("||") || 
    					tmpSubstring.equals("OR") ||
    					tmpSubstring.equals("&&") || 
    					expression.substring(i, i + 3).equals("AND")
    					) {
    				return i;
    			}
    		}
    	}
    	return -1;
    }

    private static Boolean evaluateCondition(String condition, JsonNode jsonNode) {
        // Split the condition into left and right side
    	String[] sides = condition.split("==|!=|<|>|<=|>=");
    	String jsonPath = sides[0].trim();
    	String operator = condition.substring(sides[0].length(), condition.length() - sides[1].length()).trim();
        
    	String value = null;
    	if (sides[1].trim().equals("null")) {
        	//
    	} else {
        	// Removing surrounding quotes
        	// ^\" represents a double quote at the start of the string
        	// \"$ represents a double quote at the end of the string
    		value = sides[1].trim().replaceAll("^\"|\"$", "");
    	}
        
    	String jsonPointer = jsonPath.replace('.', '/').replaceFirst("^", "/");
    	JsonNode node = jsonNode.at(jsonPointer);
        
    	if (operator.equals("==")) {
    		if (value == null) return node.isNull();
    		else return node.asText().equals(value);
    	}
    	else if (operator.equals("!=")) {
    		if (value == null) return !(node.isNull());
    		else return !node.asText().equals(value);
    	}
    	else if (operator.equals("<")) {
    		return node.asDouble() < Double.parseDouble(value);
    	}
    	else if (operator.equals("<=")) {
    		return node.asDouble() <= Double.parseDouble(value);
    	}
    	else if (operator.equals(">")) {
    		return node.asDouble() > Double.parseDouble(value);
    	}
    	else if (operator.equals(">=")) {
    		return node.asDouble() >= Double.parseDouble(value);
    	}
    	else return false;
    }
}