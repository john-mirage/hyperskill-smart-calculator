package calculator;

import java.util.ArrayList;
import java.util.List;

public class DeclarationParser {
    private final String declaration;
    private final List<String> declarationList;
    private Character previousChar;
    private Character currentChar;
    private Character nextChar;
    private StringBuilder token;
    private boolean hasEqual;

    public DeclarationParser(String declaration) throws IllegalArgumentException {
        this.declaration = declaration;
        this.declarationList = new ArrayList<>();
        this.previousChar = null;
        this.currentChar = null;
        this.nextChar = null;
        this.token = new StringBuilder();
        this.hasEqual = false;
        this.parseDeclaration();
    }

    public List<String> getDeclarationList() {
        return this.declarationList;
    }

    private void handlePlus() throws IllegalArgumentException {
        if (this.previousChar == null) {
            throw new IllegalArgumentException("Invalid identifier");
        } else if (this.nextChar == null) {
            if (this.hasEqual) {
                throw new IllegalArgumentException("Invalid assignment");
            } else {
                throw new IllegalArgumentException("Invalid identifier");
            }
        } else if (Character.isDigit(this.previousChar)) {
            throw new IllegalArgumentException("Invalid assignment");
        } else if (Character.isLetter(this.previousChar)) {
            throw new IllegalArgumentException("Invalid identifier");
        }
    }

    private void handleMinus() throws IllegalArgumentException {
        if (this.previousChar == null) {
            throw new IllegalArgumentException("Invalid identifier");
        } else if (this.nextChar == null) {
            if (this.hasEqual) {
                throw new IllegalArgumentException("Invalid assignment");
            } else {
                throw new IllegalArgumentException("Invalid identifier");
            }
        } else if (this.previousChar == '=') {
            this.token.append(this.currentChar);
        } else if (Character.isDigit(this.previousChar)) {
            throw new IllegalArgumentException("Invalid assignment");
        } else if (Character.isLetter(this.previousChar)) {
            throw new IllegalArgumentException("Invalid identifier");
        }
    }

    private void handleEqual() throws IllegalArgumentException {
        if (this.nextChar == null) {
            if (this.hasEqual) {
                throw new IllegalArgumentException("Invalid assignment");
            } else {
                throw new IllegalArgumentException("Invalid identifier");
            }
        } else if (this.previousChar == null) {
            throw new IllegalArgumentException("Invalid identifier");
        } else if (Character.isDigit(this.previousChar) || Character.isLetter(this.previousChar)) {
            if (!this.hasEqual) {
                this.declarationList.add(this.token.toString());
                this.token = new StringBuilder();
                this.hasEqual = true;
            } else {
                throw new IllegalArgumentException("Invalid assignment");
            }
        }
    }

    private void handleDigit() throws IllegalArgumentException {
        if (this.previousChar == null) {
            throw new IllegalArgumentException("Invalid identifier");
        } else if (this.previousChar == '+') {
            this.token.append(this.currentChar);
        } else if (this.previousChar == '-') {
            this.token.append(this.currentChar);
            if (this.nextChar == null) {
                this.declarationList.add(this.token.toString());
                this.token = new StringBuilder();
            }
        } else if (Character.isDigit(this.previousChar)) {
            this.token.append(this.currentChar);
            if (this.nextChar == null) {
                this.declarationList.add(this.token.toString());
                this.token = new StringBuilder();
            }
        } else if (this.previousChar == '=') {
            this.token.append(this.currentChar);
            if (this.nextChar == null) {
                this.declarationList.add(this.token.toString());
                this.token = new StringBuilder();
            }
        } else if (Character.isLetter(this.previousChar)) {
            if (this.hasEqual) {
                throw new IllegalArgumentException("Invalid assignment");
            } else {
                throw new IllegalArgumentException("Invalid identifier");
            }
        }
    }

    private void handleLetter() throws IllegalArgumentException {
        if (this.previousChar == null) {
            this.token.append(this.currentChar);
        } else if (Character.isDigit(this.previousChar)) {
            if (this.hasEqual) {
                throw new IllegalArgumentException("Invalid assignment");
            } else {
                throw new IllegalArgumentException("Invalid identifier");
            }
        } else if (this.previousChar == '=') {
            this.token.append(this.currentChar);
            if (this.nextChar == null) {
                this.declarationList.add(this.token.toString());
                this.token = new StringBuilder();
            }
        } else if (Character.isLetter(this.previousChar)) {
            this.token.append(this.currentChar);
            if (this.nextChar == null) {
                this.declarationList.add(this.token.toString());
                this.token = new StringBuilder();
            }
        }
    }

    public void parseDeclaration() throws IllegalArgumentException {
        String spaceLessDeclaration = this.declaration.replaceAll("\\s+", "");
        char[] declarationCharArray = spaceLessDeclaration.toCharArray();
        for (int i = 0; i < declarationCharArray.length; i++) {
            this.currentChar = declarationCharArray[i];
            this.nextChar = i != declarationCharArray.length - 1 ? declarationCharArray[i + 1] : null;
            if (this.currentChar == '+') {
                this.handlePlus();
            } else if (this.currentChar == '-') {
                this.handleMinus();
            } else if (this.currentChar == '=') {
                this.handleEqual();
            } else if (Character.isDigit(this.currentChar)) {
                this.handleDigit();
            } else if (Character.isLetter(this.currentChar)) {
                this.handleLetter();
            } else {
                if (this.hasEqual) {
                    throw new IllegalArgumentException("Invalid assignment");
                } else {
                    throw new IllegalArgumentException("Invalid identifier");
                }
            }
            this.previousChar = this.currentChar;
        }
    }
}
