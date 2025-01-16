package calculator;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Calculator {
    private final Map<String, BigInteger> variables;
    private CalculatorState state;

    public Calculator() {
        this.variables = new HashMap<>();
        this.state = CalculatorState.ON;
    }

    public CalculatorState getState() {
        return this.state;
    }

    public void execute(String input) {
        if (!input.isEmpty()) {
            try {
                if (input.startsWith("/")) {
                    Command command = new Command(input);
                    if (command.execute()) {
                        this.state = CalculatorState.OFF;
                    }
                } else if (input.contains("=")) {
                    DeclarationParser parser = new DeclarationParser(input);
                    Declaration declaration = new Declaration(parser.getDeclarationList(), this.variables);
                    declaration.execute();
                } else if (input.matches("\\d+[a-zA-Z]+[\\da-zA-Z]*|[a-zA-Z]+\\d+[\\da-zA-Z]*")) {
                    throw new IllegalArgumentException("Invalid identifier");
                } else {
                    ExpressionParser parser = new ExpressionParser(input);
                    Expression expression = new Expression(parser.getExpressionList(), this.variables);
                    expression.execute();
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
