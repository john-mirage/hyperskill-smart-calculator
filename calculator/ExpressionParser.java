package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionParser {
    private final String expression;
    private final List<String> expressionList;
    private final Stack<Character> parenthesisStack;
    private Character previousChar;
    private Character currentChar;
    private Character nextChar;
    private StringBuilder token;

    public ExpressionParser(String expression) throws IllegalArgumentException {
        this.expression = expression;
        this.expressionList = new ArrayList<>();
        this.parenthesisStack = new Stack<>();
        this.previousChar = null;
        this.currentChar = null;
        this.nextChar = null;
        this.token = new StringBuilder();
        this.parseExpression();
    }

    public List<String> getExpressionList() {
        return this.expressionList;
    }

    private void handleAddition() throws IllegalArgumentException {
        if (this.nextChar == null) {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == null || this.previousChar == '^') {
            if (!Character.isDigit(this.nextChar)) {
                throw new IllegalArgumentException("Invalid expression");
            }
        } else if (this.previousChar == ')') {
            this.expressionList.add(this.currentChar.toString());
        } else if (Character.isDigit(this.previousChar) || Character.isLetter(this.previousChar)) {
            this.expressionList.add(this.token.toString());
            this.expressionList.add(this.currentChar.toString());
            this.token = new StringBuilder();
        }
    }

    private void handleSubtraction() throws IllegalArgumentException {
        if (this.nextChar == null) {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == null) {
            if (Character.isDigit(this.nextChar)) {
                this.token.append(this.currentChar);
            } else if (this.nextChar == '(') {
                this.expressionList.add(this.currentChar.toString());
            } else {
                throw new IllegalArgumentException("Invalid expression");
            }
        } else if (this.previousChar == '+') {
            this.expressionList.removeLast();
            this.expressionList.add(this.currentChar.toString());
        } else if (this.previousChar == '-') {
            this.expressionList.removeLast();
            this.expressionList.add("+");
            this.currentChar = '+';
        } else if (
            this.previousChar == '*' ||
            this.previousChar == '/' ||
            this.previousChar == '^'
        ) {
            if (Character.isDigit(this.nextChar)) {
                this.token.append("-");
            } else {
                throw new IllegalArgumentException("Invalid expression");
            }
        } else if (this.previousChar == '(' || this.previousChar == ')') {
            this.expressionList.add(this.currentChar.toString());
        } else if (Character.isDigit(this.previousChar) || Character.isLetter(this.previousChar)) {
            this.expressionList.add(this.token.toString());
            this.expressionList.add(this.currentChar.toString());
            this.token = new StringBuilder();
        }
    }

    private void handleMultiplication() throws IllegalArgumentException {
        if (
            this.nextChar == null ||
            this.previousChar == null ||
            this.previousChar == '+' ||
            this.previousChar == '-' ||
            this.previousChar == '*' ||
            this.previousChar == '/' ||
            this.previousChar == '^' ||
            this.previousChar == '('
        ) {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == ')') {
            this.expressionList.add(this.currentChar.toString());
        } else if (Character.isDigit(this.previousChar) || Character.isLetter(this.previousChar)) {
            this.expressionList.add(this.token.toString());
            this.expressionList.add(this.currentChar.toString());
            this.token = new StringBuilder();
        }
    }

    private void handleDivision() throws IllegalArgumentException {
        if (
            this.nextChar == null ||
            this.previousChar == null ||
            this.previousChar == '+' ||
            this.previousChar == '-' ||
            this.previousChar == '*' ||
            this.previousChar == '/' ||
            this.previousChar == '^' ||
            this.previousChar == '('
        ) {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == ')') {
            this.expressionList.add(this.currentChar.toString());
        } else if (Character.isDigit(this.previousChar) || Character.isLetter(this.previousChar)) {
            this.expressionList.add(this.token.toString());
            this.expressionList.add(this.currentChar.toString());
            this.token = new StringBuilder();
        }
    }

    private void handlePower() throws IllegalArgumentException {
        if (
            this.nextChar == null ||
            this.previousChar == null ||
            this.previousChar == '+' ||
            this.previousChar == '-' ||
            this.previousChar == '*' ||
            this.previousChar == '/' ||
            this.previousChar == '^' ||
            this.previousChar == '('
        ) {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == ')') {
            this.expressionList.add(this.currentChar.toString());
        } else if (Character.isDigit(this.previousChar) || Character.isLetter(this.previousChar)) {
            this.expressionList.add(this.token.toString());
            this.expressionList.add(this.currentChar.toString());
            this.token = new StringBuilder();
        }
    }

    private void handleLeftParenthesis() throws IllegalArgumentException {
        this.parenthesisStack.push(this.currentChar);
        if (this.nextChar == null) {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == null) {
            this.expressionList.add(this.currentChar.toString());
        } else if (this.previousChar == '+') {
            this.expressionList.add(this.currentChar.toString());
        } else if (this.previousChar == '-') {
            this.expressionList.add(this.currentChar.toString());
        } else if (this.previousChar == '*') {
            this.expressionList.add(this.currentChar.toString());
        } else if (this.previousChar == '/') {
            this.expressionList.add(this.currentChar.toString());
        } else if (this.previousChar == '^') {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == '(') {
            this.expressionList.add(this.currentChar.toString());
        } else if (this.previousChar == ')') {
            throw new IllegalArgumentException("Invalid expression");
        } else if (Character.isDigit(this.previousChar)) {
            throw new IllegalArgumentException("Invalid expression");
        } else if (Character.isLetter(this.previousChar)) {
            throw new IllegalArgumentException("Invalid expression");
        }
    }

    private void handleRightParenthesis() throws IllegalArgumentException {
        if (this.parenthesisStack.isEmpty() || this.parenthesisStack.pop() != '(') {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == null) {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == '+') {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == '-') {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == '*') {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == '/') {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == '^') {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == '(') {
            throw new IllegalArgumentException("Invalid expression");
        } else if (this.previousChar == ')') {
            this.expressionList.add(this.currentChar.toString());
        } else if (Character.isDigit(this.previousChar)) {
            this.expressionList.add(this.token.toString());
            this.expressionList.add(this.currentChar.toString());
            this.token = new StringBuilder();
        } else if (Character.isLetter(this.previousChar)) {
            this.expressionList.add(this.token.toString());
            this.expressionList.add(this.currentChar.toString());
            this.token = new StringBuilder();
        }
    }

    private void handleDigit() throws IllegalArgumentException {
        if (this.previousChar == null ||
            this.previousChar == '+' ||
            this.previousChar == '-' ||
            this.previousChar == '*' ||
            this.previousChar == '/' ||
            this.previousChar == '^' ||
            this.previousChar == '('
        ) {
            this.token.append(this.currentChar);
            if (this.nextChar == null) {
                this.expressionList.add(this.token.toString());
                this.token = new StringBuilder();
            }
        } else if (this.previousChar == ')') {
            throw new IllegalArgumentException("Invalid expression");
        } else if (Character.isDigit(this.previousChar)) {
            this.token.append(this.currentChar);
            if (this.nextChar == null) {
                this.expressionList.add(this.token.toString());
                this.token = new StringBuilder();
            }
        } else if (Character.isLetter(this.previousChar)) {
            throw new IllegalArgumentException("Invalid expression");
        }
    }

    private void handleLetter() throws IllegalArgumentException {
        if (this.previousChar == null ||
            this.previousChar == '+' ||
            this.previousChar == '-' ||
            this.previousChar == '*' ||
            this.previousChar == '/' ||
            this.previousChar == '^' ||
            this.previousChar == '('
        ) {
            this.token.append(this.currentChar);
            if (this.nextChar == null) {
                this.expressionList.add(this.token.toString());
                this.token = new StringBuilder();
            }
        } else if (this.previousChar == ')') {
            throw new IllegalArgumentException("Invalid expression");
        } else if (Character.isDigit(this.previousChar)) {
            throw new IllegalArgumentException("Invalid expression");
        } else if (Character.isLetter(this.previousChar)) {
            this.token.append(this.currentChar);
            if (this.nextChar == null) {
                this.expressionList.add(this.token.toString());
                this.token = new StringBuilder();
            }
        }
    }

    private void parseExpression() throws IllegalArgumentException {
        String spaceLessExpression = this.expression.replaceAll("\\s+", "");
        char[] expressionCharacters = spaceLessExpression.toCharArray();
        for (int i = 0; i < expressionCharacters.length; i++) {
            this.currentChar = expressionCharacters[i];
            this.nextChar = i != expressionCharacters.length - 1 ? expressionCharacters[i + 1] : null;
            if (this.currentChar == '+') {
                this.handleAddition();
            } else if (this.currentChar == '-') {
                this.handleSubtraction();
            } else if (this.currentChar == '*') {
                this.handleMultiplication();
            } else if (this.currentChar == '/') {
                this.handleDivision();
            } else if (this.currentChar == '^') {
                this.handlePower();
            } else if (this.currentChar == '(') {
                this.handleLeftParenthesis();
            } else if (this.currentChar == ')') {
                this.handleRightParenthesis();
            } else if (Character.isDigit(this.currentChar)) {
                this.handleDigit();
            } else if (Character.isLetter(this.currentChar)) {
                this.handleLetter();
            } else {
                throw new IllegalArgumentException("Invalid expression");
            }
            this.previousChar = this.currentChar;
        }
        if (!this.parenthesisStack.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression");
        }
    }
}
