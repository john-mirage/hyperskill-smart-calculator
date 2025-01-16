package calculator;

import java.math.BigInteger;
import java.util.*;

public class Expression {
    private final List<String> expressionList;
    private final Map<String, BigInteger> variables;

    public Expression(List<String> expressionList, Map<String, BigInteger> variables) throws IllegalArgumentException {
        this.expressionList = expressionList;
        this.variables = variables;
    }

    private int getOperatorPrecedence(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^" -> 3;
            default -> -1;
        };
    }

    private boolean isOperator(String operator) {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/") || operator.equals("^");
    }

    private List<String> infixToPostfix() {
        Stack<Character> operatorStack = new Stack<>();
        List<String> postfix = new ArrayList<>();
        for (String token : this.expressionList) {
            if (token.matches("-?\\d+|[a-zA-Z]+")) {
                postfix.add(token);
            } else if (token.equals("(")) {
                operatorStack.push('(');
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.add(operatorStack.pop().toString());
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();
                }
            } else if (this.isOperator(token)) {
                while (!operatorStack.isEmpty() && this.getOperatorPrecedence(token) <= this.getOperatorPrecedence(operatorStack.peek().toString())) {
                    postfix.add(operatorStack.pop().toString());
                }
                operatorStack.push(token.charAt(0));
            }
        }
        while (!operatorStack.isEmpty()) {
            postfix.add(operatorStack.pop().toString());
        }
        return postfix;
    }

    public void execute() {
        List<String> postfix = this.infixToPostfix();
        Stack<BigInteger> stack = new Stack<>();
        for (String token : postfix) {
            if (token.matches("-?\\d+")) {
                stack.push(new BigInteger(token));
            } else if (Character.isLetter(token.charAt(0))) {
                if (this.variables.containsKey(token)) {
                    stack.push(this.variables.get(token));
                } else {
                    throw new IllegalArgumentException("Unknown variable");
                }
            } else if (this.isOperator(token)) {
                BigInteger b = stack.pop();
                BigInteger a = stack.pop();
                switch (token.charAt(0)) {
                    case '+': stack.push(a.add(b)); break;
                    case '-': stack.push(a.subtract(b)); break;
                    case '*': stack.push(a.multiply(b)); break;
                    case '/': stack.push(a.divide(b)); break;
                }
            }
        }
        System.out.println(stack.pop());
    }
}
