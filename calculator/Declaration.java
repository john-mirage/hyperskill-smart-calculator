package calculator;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class Declaration {
    private final List<String> declarationList;
    private final Map<String, BigInteger> variables;

    public Declaration(List<String> declarationList, Map<String, BigInteger> variables) throws IllegalArgumentException {
        this.declarationList = declarationList;
        this.variables = variables;
    }

    public void execute() throws IllegalArgumentException {
        String identifier = this.declarationList.getFirst();
        String assignment = this.declarationList.getLast();
        if (Character.isLetter(assignment.charAt(0))) {
            if (this.variables.containsKey(assignment)) {
                this.variables.put(identifier, this.variables.get(assignment));
            } else {
                throw new IllegalArgumentException("Unknown variable");
            }
        } else {
            this.variables.put(identifier, new BigInteger(assignment));
        }
    }
}
